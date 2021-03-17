package com.example.suppliersadda.Activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suppliersadda.Adaptor.LocationAdaptor
import com.example.suppliersadda.Adaptor.VehicleAdapter


import com.example.suppliersadda.Models.DealersDataModel
import com.example.suppliersadda.Models.SharePrefUsersData
import com.example.suppliersadda.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.homTopToolbar
import kotlinx.android.synthetic.main.activity_main.locationRecyclerView
import kotlinx.android.synthetic.main.activity_main.vehicleRecyclerView
import kotlinx.android.synthetic.main.dealers_row_unit.view.*

class MainActivity : AppCompatActivity() {


    lateinit var toggle: ActionBarDrawerToggle

    //variables for header user details
    lateinit var current_userName: TextView
    lateinit var current_userEmail: TextView
    lateinit var current_userPhoneNo: TextView
    lateinit var current_userPic: ImageView
    lateinit var nav_View:NavigationView
    //variable for home fragments data past in main activity
    val addImageArr= arrayOf<Int>(
        R.drawable.ic_action_home,
        //R.drawable.caraddpic,


        R.drawable.irhastamp

    )
    lateinit var carouselView: CarouselView
    lateinit var delearRecyclerView: RecyclerView
    lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // for past data from fragment


        delearRecyclerView = findViewById<RecyclerView>(R.id.dealerRecyclerviewHome)
        val adapter = LocationAdaptor(this, Registration.localitiesArray)
        locationRecyclerView.adapter = adapter

        val vehicleAdapter =
            VehicleAdapter(this, Registration.vehicleArray, Registration.vehiclePicArray)



        vehicleRecyclerView.adapter = vehicleAdapter

        // for slider view
        carouselView = findViewById(R.id.carouselView);

        Log.d("slider","array counted")
        carouselView.setImageListener{position, imageView ->
            imageView.setImageResource(addImageArr[position])
            Log.d("slider","resourse seted")
        }
        carouselView.pageCount=addImageArr.size
        carouselView.setImageClickListener {
            Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show()
            Log.d("slider","click event ")
        }


        homTopToolbar.setNavigationOnClickListener {
            Toast.makeText(this,"icon clicked",Toast.LENGTH_SHORT).show()
        }
        homTopToolbar.inflateMenu(R.menu.topbar)
        homTopToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.cloud_menu_topbar -> {
                    Toast.makeText(this,"tips is underdevolopment",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.search_menu_topbar-> {
                    Log.d("Search","calling search activity")
                    Toast.makeText(this,"search is underdevolopment",Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,SearchActivity::class.java)
                    startActivity(intent)
                    Log.d("Search","calling search activity1")
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }

     // for loading data from firebase
        fetchUser()

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
                    startActivity(intent)
                }

                R.id.nav_profile -> {
                    val intent = Intent(this,ProfileActivity::class.java)
                    startActivity(intent)
                }

                R.id.nav_sell -> {
                    val intent = Intent(this,SellActivity::class.java)
                    startActivity(intent)
                }

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
//        homeFragment= HomeFragment()
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.frame_layout,homeFragment)
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            .commit()


//        button_login.setOnClickListener {
//            val intent=Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//        val  bottomNavigation=findViewById<BottomNavigationView>(R.id.bottom_nav_layout)
//        bottomNavigation.setOnNavigationItemSelectedListener {item->
//                  when(item.itemId){
//                     R.id.homeid ->{
//                         homeFragment= HomeFragment()
//                         supportFragmentManager
//                             .beginTransaction()
//                             .replace(R.id.frame_layout,homeFragment)
//                             .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                             .commit()
//
//                     }
//                      R.id.shelid ->{
//                          shellFragment= ShellFragment()
//                          supportFragmentManager
//                              .beginTransaction()
//                              .replace(R.id.frame_layout,shellFragment)
//                              .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                              .commit()
//
//                      }
//                      R.id.profileid ->{
//                          profileFragment= ProfileFragment()
//                          supportFragmentManager
//                              .beginTransaction()
//                              .replace(R.id.frame_layout,profileFragment)
//                              .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                              .commit()
//
//                      }
//                  }
//            true
//        }
    }
    fun fetchUser(){
        val ref= FirebaseDatabase.getInstance().getReference("/DealersData")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                p0.children.forEach {
                    Log.d("newmessege", it.toString())
                    val dealer = it.getValue(DealersDataModel::class.java)
                    if (dealer != null) {
                        adapter.add(UserItem(dealer, this@MainActivity))
                    }
                }
                //adapter.setOnItemClickListener{item,view->
                //    val intent=Intent(th)
                // }
                delearRecyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)

                delearRecyclerView.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

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


    //Handling double back pressed
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this,"Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}

class UserItem(val dealer: DealersDataModel, val context: Context?): Item<GroupieViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.dealers_row_unit
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.dealerNameUnit.text=dealer.userName
        viewHolder.itemView.localityDealerUnit.text=dealer.locality
        Log.d("DealerFetch", "locality")
        viewHolder.itemView.cityTvdealerunit.text=dealer.city
        viewHolder.itemView.pinCodeDelerUnit.text=dealer.pin
        viewHolder.itemView.sulierTypeDealerUnit.text=dealer.transMaterial

        // onItem click events
        viewHolder.itemView.setOnClickListener {
            val intent=Intent(context, DealersProfileActivity::class.java)
            context!!.startActivity(intent)
            Toast.makeText(context, "item clicked", Toast.LENGTH_SHORT).show()
            true
        }

        if (viewHolder.itemView.profile_image!=null&&viewHolder.itemView.dvehiclepicDealerUnit!=null&&dealer.userImage!=null) {
            Log.d("DealerFetch", "image ${dealer.userImage}")
            Picasso.get().load(dealer.userImage).into(viewHolder.itemView.profile_image)
            Log.d("DealerFetch", "image ${dealer.imageFirst}")
            Picasso.get().load(dealer.imageFirst).into(viewHolder.itemView.dvehiclepicDealerUnit)
        }
    }
}
