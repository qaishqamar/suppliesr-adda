package com.example.suppliersadda.Adaptor

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.suppliersadda.R
import com.google.gson.Gson
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


class AdapterSuggestionSearch(val context: Context) : RecyclerView.Adapter<AdapterSuggestionSearch.ViewHolder>()
    {

        val SEARCH_HISTORY_KEY = "_SEARCH_HISTORY_KEY"
        val MAX_HISTORY_ITEMS = 5

        var items: List<String> = ArrayList()
        lateinit var onItemClickListener: AdapterView.OnItemClickListener
        var prefs: SharedPreferences? = null




        @JvmName("setOnItemClickListener1")
        fun setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener) {
            this.onItemClickListener = onItemClickListener
        }

       init {
            prefs = context.getSharedPreferences("PREF_RECENT_SEARCH", Context.MODE_PRIVATE)
            items = getSearchHistory()!!
            Collections.reverse(items)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v: View =
                LayoutInflater.from(parent.context).inflate(
                    R.layout.search_item_suggestion,
                    parent,
                    false
                )
            return ViewHolder(v)
        }

       override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val p = items[position]
            holder.title.text = p
           Log.d("click event", "after suges click")
            holder.lyt_parent.setOnClickListener { v ->
                onItemClickListener.onItemClick(
                    null,v ,
                    position,
                    holder.itemId
                )
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount(): Int {
            return items.size
        }

       open interface OnItemClickListener {
            fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
        }

        fun refreshItems() {
            Log.d("adaptorSearch", "refreshItems")
            items = getSearchHistory()!!
            Collections.reverse(items)
            notifyDataSetChanged()
        }

      inner class SearchObject(items: ArrayList<String>) :
            Serializable {
            var items: ArrayList<String> = ArrayList()

            init {
                this.items = items
            }
        }

        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

            var title: TextView
            var lyt_parent: LinearLayout

            init {
                title = v.findViewById<View>(R.id.title) as TextView
                lyt_parent = v.findViewById<View>(R.id.lyt_parent_suggest) as LinearLayout
            }
        }

        /**
         * To save last state request
         */
        fun addSearchHistory(s: String) {
            Log.d("adaptorSearch", "addSearchHistory")
            val searchObject = SearchObject(getSearchHistory()!!)
            if (searchObject.items.contains(s)) searchObject.items.remove(s)
            searchObject.items.add(s)
            if (searchObject.items.size > MAX_HISTORY_ITEMS) searchObject.items.removeAt(0)
            val json: String = Gson().toJson(searchObject, SearchObject::class.java)
            prefs!!.edit().putString(SEARCH_HISTORY_KEY, json).apply()
        }

        open fun getSearchHistory(): ArrayList<String>? {
            Log.d("adaptorSearch", "getSearchHistory")

                val json = prefs!!.getString(SEARCH_HISTORY_KEY, "")
                if (json == "") return ArrayList()
                val searchObject: SearchObject = Gson().fromJson(json, SearchObject::class.java)
                return searchObject.items


        }

}
