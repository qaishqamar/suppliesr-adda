package com.example.suppliersadda.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suppliersadda.Activity.DealersProfileActivity
import com.example.suppliersadda.Activity.Registration
import com.example.suppliersadda.Adaptor.LocationAdaptor
import com.example.suppliersadda.Adaptor.VehicleAdapter
import com.example.suppliersadda.Models.DealersDataModel
import com.example.suppliersadda.R
import com.example.suppliersadda.R.id
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.dealers_row_unit.view.*
import kotlinx.android.synthetic.main.fragment_home.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {
     val addImageArr= arrayOf<Int>(
         R.drawable.ic_action_home,
       //R.drawable.caraddpic,


         R.drawable.irhastamp

     )
     lateinit var carouselView:CarouselView
    lateinit var delearRecyclerView: RecyclerView
    lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        Log.d("topbar","click event ")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

      //  inflate tobbar menu

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
         fetchUser()
        delearRecyclerView = view!!.findViewById<RecyclerView>(R.id.dealerRecyclerviewHome)
        val adapter = LocationAdaptor(activity!!, Registration.localitiesArray)
        locationRecyclerView.adapter = adapter

        val vehicleAdapter =
            VehicleAdapter(activity!!, Registration.vehicleArray, Registration.vehiclePicArray)



        vehicleRecyclerView.adapter = vehicleAdapter

        // for slider view
         carouselView = view!!.findViewById(R.id.carouselView);

          Log.d("slider","array counted")
        carouselView.setImageListener{position, imageView ->
            imageView.setImageResource(addImageArr[position])
            Log.d("slider","resourse seted")
        }
        carouselView.pageCount=addImageArr.size
        carouselView.setImageClickListener {
            Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show()
            Log.d("slider","click event ")
        }


        homTopToolbar.setNavigationOnClickListener {
            Toast.makeText(context,"icon clicked",Toast.LENGTH_SHORT).show()
        }
        homTopToolbar.inflateMenu(R.menu.topbar)
       homTopToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.cloud_menu_topbar -> {
                    Toast.makeText(context,"tips is underdevolopment",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.search_menu_topbar-> {
                    Toast.makeText(context,"search is underdevolopment",Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }


    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
//        inflater!!.inflate(R.menu.topbar,menu)
//        Log.d("topbar","click event ")
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val itemView=item.itemId
//        Log.d("topbar","click event ")
//        when(itemView){
//           R.id.search_menu_topbar->{
//               Toast.makeText(context,"search is underdevolopment",Toast.LENGTH_SHORT).show()
//
//            }
//            R.id.cloud_menu_topbar->{
//                Toast.makeText(context,"tips is underdevolopment",Toast.LENGTH_SHORT).show()
//
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//



    fun fetchUser(){
        val ref= FirebaseDatabase.getInstance().getReference("/DealersData")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                p0.children.forEach {
                    Log.d("newmessege", it.toString())
                    val dealer = it.getValue(DealersDataModel::class.java)
                    if (dealer != null) {
                        adapter.add(UserItem(dealer, context))
                    }
                }
                //adapter.setOnItemClickListener{item,view->
                //    val intent=Intent(th)
                // }
                delearRecyclerView.layoutManager = GridLayoutManager(context, 2)

                delearRecyclerView.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

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


