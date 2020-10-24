package com.example.suppliersadda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var homeFragment:HomeFragment
    lateinit var shellFragment: ShellFragment
    lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // as default fragment
        homeFragment= HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout,homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()


        button_login.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        val  bottomNavigation=findViewById<BottomNavigationView>(R.id.bottom_nav_layout)
        bottomNavigation.setOnNavigationItemSelectedListener {item->
                  when(item.itemId){
                     R.id.homeid->{
                         homeFragment= HomeFragment()
                         supportFragmentManager
                             .beginTransaction()
                             .replace(R.id.frame_layout,homeFragment)
                             .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                             .commit()

                     }
                      R.id.shelid->{
                          shellFragment= ShellFragment()
                          supportFragmentManager
                              .beginTransaction()
                              .replace(R.id.frame_layout,shellFragment)
                              .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                              .commit()

                      }
                      R.id.profileid->{
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
}