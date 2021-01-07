package com.example.suppliersadda.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suppliersadda.R
import kotlinx.android.synthetic.main.location_unit.view.*

class LocationAdaptor(val context: Context, private val locations: Array<String>)  :
    RecyclerView.Adapter<LocationAdaptor.MyViewHolder>(){

    override fun onCreateViewHolder(parent:ViewGroup, viewtype: Int): MyViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.location_unit,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, possition: Int) {
        val location=locations[possition]
        holder.SetData(location,possition)

    }
    companion object{
        val USER_KEY = "USER_KEY"
    }
    inner class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        var currentLocation: String? = null
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

        fun SetData(location : String?, poss: Int) {

            itemView.locatoinTextUnit.text = location
            this.currentLocation = location
            this.curretPossition = poss

        }
    }

}