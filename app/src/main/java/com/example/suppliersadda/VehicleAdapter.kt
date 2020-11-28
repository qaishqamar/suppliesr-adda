package com.example.suppliersadda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.vehicle_filter_unit.view.*

class VehicleAdapter(val context: Context, private val vehicles: Array<String>,val vehiclePics:Array<Int>): RecyclerView.Adapter<VehicleAdapter.MyVehicleViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): MyVehicleViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.vehicle_filter_unit,parent,false)
        return MyVehicleViewHolder(view)
    }

    override fun getItemCount(): Int {

        return vehicles.size
    }
    override fun onBindViewHolder(holder: MyVehicleViewHolder, position: Int) {
        val vehicle=vehicles[position]
        holder.SetData(vehicle,position)

    }




    inner class MyVehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var currentVehicle: String? = null
        var curretPossition: Int = 0

        init {
            itemView.setOnClickListener {
                //val intent=Intent(this,NewsWebsite::class.java)
                // startActivity(intent)


            }
//            itemView.cv1mask_tips.setOnClickListener {
//                val massage: String = "My hobby is :"+currentHobby!!.tittle
//                val intent= Intent()
//                intent.action= Intent.ACTION_SEND
//                intent.putExtra(Intent.EXTRA_TEXT,massage)
//                intent.type="text/plain"
//                context.startActivity(Intent.createChooser(intent,"share to"))
//            }
        }

        fun SetData(vehicle : String?, poss: Int) {
            itemView.vehicleImageUnit.setImageResource(R.drawable.vehicle_pic_icon)
            itemView.vehicleNameUnitTv.text = vehicle
            this.currentVehicle = vehicle
            this.curretPossition = poss

        }
    }



}