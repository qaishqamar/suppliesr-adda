package com.example.suppliersadda.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suppliersadda.*
import com.example.suppliersadda.Activity.DealersProfileActivity
import com.example.suppliersadda.Activity.Registration
import com.example.suppliersadda.Adaptor.LocationAdaptor
import com.example.suppliersadda.Adaptor.VehicleAdapter
import com.example.suppliersadda.Models.DealersDataModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
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
    lateinit var delearRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
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
    }

   fun fetchUser(){
        val ref= FirebaseDatabase.getInstance().getReference("/DealersData")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val adapter= GroupAdapter<GroupieViewHolder>()
                p0.children.forEach{
                    Log.d("newmessege",it.toString())
                    val dealer = it.getValue(DealersDataModel::class.java)
                    if (dealer!=null) {
                        adapter.add(UserItem(dealer))
                    }
                }
                //adapter.setOnItemClickListener{item,view->
                //    val intent=Intent(th)
                // }
                 delearRecyclerView.layoutManager=GridLayoutManager(context,2)

                delearRecyclerView.adapter=adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }
}
class UserItem(val dealer: DealersDataModel): Item<GroupieViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.dealers_row_unit
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.dealerNameUnit.text=dealer.userName
        viewHolder.itemView.localityDealerUnit.text=dealer.locality
        Log.d("DealerFetch","locality")
        viewHolder.itemView.cityTvdealerunit.text=dealer.city
        viewHolder.itemView.pinCodeDelerUnit.text=dealer.pin
        viewHolder.itemView.sulierTypeDealerUnit.text=dealer.transMaterial

        // onItem click events
           viewHolder.itemView.dealerNameUnit.setOnClickListener {
//               val intent=Intent(this,DealersProfileActivity::class.java)
//               startActivity(intent)

           }

        if (viewHolder.itemView.profile_image!=null&&viewHolder.itemView.dvehiclepicDealerUnit!=null&&dealer.userImage!=null) {
            Log.d("DealerFetch","image ${dealer.userImage}")
            Picasso.get().load(dealer.userImage).into(viewHolder.itemView.profile_image)
            Log.d("DealerFetch","image ${dealer.imageFirst}")
            Picasso.get().load(dealer.imageFirst).into(viewHolder.itemView.dvehiclepicDealerUnit)
        }
    }
}


