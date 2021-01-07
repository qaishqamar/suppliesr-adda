package com.example.suppliersadda.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.suppliersadda.Fragments.HomeFragment
import com.example.suppliersadda.Fragments.ProfileFragment
import com.example.suppliersadda.Fragments.ShellFragment
import com.example.suppliersadda.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var homeFragment: HomeFragment
    lateinit var shellFragment: ShellFragment
    lateinit var profileFragment: ProfileFragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // for checking user is registered or not
        VerifyUser()

        // as default fragment
        homeFragment= HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout,homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()


        button_login.setOnClickListener {
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
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
}