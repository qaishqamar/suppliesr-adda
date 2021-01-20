package com.example.suppliersadda.Activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.example.suppliersadda.Fragments.HomeFragment
import com.example.suppliersadda.Fragments.ProfileFragment
import com.example.suppliersadda.Fragments.ShellFragment
import com.example.suppliersadda.Models.SharePrefUsersData
import com.example.suppliersadda.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*

class MainActivity : AppCompatActivity() {
    lateinit var homeFragment: HomeFragment
    lateinit var shellFragment: ShellFragment
    lateinit var profileFragment: ProfileFragment

    lateinit var toggle: ActionBarDrawerToggle

    //variables for header user details
    lateinit var current_userName: TextView
    lateinit var current_userEmail: TextView
    lateinit var current_userPhoneNo: TextView
    lateinit var current_userPic: ImageView
    lateinit var nav_View:NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // for checking user is registered or not
        VerifyUser()

        //loading and defining user details for header navView
        nav_View = findViewById(R.id.nav_View)!!
        current_userName=nav_View.getHeaderView(0).findViewById<TextView>(R.id.current_userName)!!
        current_userEmail=nav_View.getHeaderView(0).findViewById<TextView>(R.id.current_userEmail)!!
        current_userPic=nav_View.getHeaderView(0).findViewById<ImageView>(R.id.current_userPic)!!
        loadCurrentUserData()

        //ActionBar drawer toggle
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav_View.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home ->{
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)}

                R.id.nav_profile -> Toast.makeText(applicationContext,
                    "clicked Profile", Toast.LENGTH_SHORT).show()

                R.id.navigation_settings -> Toast.makeText(applicationContext,
                    "clicked Settings", Toast.LENGTH_SHORT).show()

                R.id.nav_developer -> Toast.makeText(applicationContext,
                    "clicked Developer", Toast.LENGTH_SHORT).show()

                R.id.nav_HelpAndFeedback -> Toast.makeText(applicationContext,
                    "clicked Feedback", Toast.LENGTH_SHORT).show()

                R.id.nav_share -> Toast.makeText(applicationContext,
                    "clicked Share", Toast.LENGTH_SHORT).show()

                R.id.nav_rateUs -> Toast.makeText(applicationContext,
                    "clicked Rate us", Toast.LENGTH_SHORT).show()

                R.id.nav_about_app -> Toast.makeText(applicationContext,
                    "clicked About app", Toast.LENGTH_SHORT).show()

                R.id.nav_privacy_policy -> Toast.makeText(applicationContext,
                    "clicked Privacy policy", Toast.LENGTH_SHORT).show()
            }
            true
        }


        // as default fragment
        homeFragment= HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout,homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()


//        button_login.setOnClickListener {
//            val intent=Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
        val  bottomNavigation=findViewById<BottomNavigationView>(R.id.bottom_nav_layout)
        bottomNavigation.setOnNavigationItemSelectedListener {item->
                  when(item.itemId){
                     R.id.homeid ->{
                         homeFragment= HomeFragment()
                         supportFragmentManager
                             .beginTransaction()
                             .replace(R.id.frame_layout,homeFragment)
                             .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                             .commit()

                     }
                      R.id.shelid ->{
                          shellFragment= ShellFragment()
                          supportFragmentManager
                              .beginTransaction()
                              .replace(R.id.frame_layout,shellFragment)
                              .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                              .commit()

                      }
                      R.id.profileid ->{
                          profileFragment= ProfileFragment()
                          supportFragmentManager
                              .beginTransaction()
                              .replace(R.id.frame_layout,profileFragment)
                              .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                              .commit()

                      }
                  }
            true
        }
    }

    private fun loadCurrentUserData() {
        val sharePrefUsersData= SharePrefUsersData(this!!)
        val currentUserData=sharePrefUsersData.getUserDataPref()
        if (currentUserData!=null){
            Log.d("profile","${currentUserData!!.userNmae}")
            current_userName.text=currentUserData.userNmae
            current_userEmail.text=currentUserData.email
            if (currentUserData.image!=null&&current_userPic!=null){
                val uri= Uri.parse(currentUserData.image)
                current_userPic.setImageURI(uri)
            }

        }
    }

    fun VerifyUser(){
        val uid= FirebaseAuth.getInstance().uid
        if(uid==null)
        {
            val intent=Intent(this,
                LoginActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

