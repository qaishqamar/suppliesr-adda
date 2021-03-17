package com.example.suppliersadda.Activity

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suppliersadda.Adaptor.AdapterSuggestionSearch
//import com.example.suppliersadda.Adaptor.AdapterSuggestionSearch
import com.example.suppliersadda.R
import com.example.suppliersadda.utils.ViewAnimation

class SearchActivity : AppCompatActivity() {
    private var et_search: EditText? = null
    private var bt_clear: ImageButton? = null
    private  var bt_back:ImageButton? = null

    private var progress_bar: ProgressBar? = null
    private var lyt_no_result: LinearLayout? = null

    private var recyclerSuggestion: RecyclerView? = null
   private var mAdapterSuggestion: AdapterSuggestionSearch? = null
    private var lyt_suggestion: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    //   initToolbar()
        initComponent()
    }

//        private fun initToolbar() {
//        Tools.setSystemBarColor(this)
//    }

    fun initComponent()
    {
        progress_bar = findViewById<View>(R.id.progress_bar) as ProgressBar
        lyt_no_result = findViewById<View>(R.id.lyt_no_result) as LinearLayout
        lyt_suggestion = findViewById<View>(R.id.lyt_suggestion) as LinearLayout
        et_search = findViewById<View>(R.id.et_search) as EditText
        bt_clear = findViewById<View>(R.id.bt_clear) as ImageButton
        bt_back = findViewById<View>(R.id.bt_back) as ImageButton
        bt_clear!!.setVisibility(View.GONE)

//   for connecting adaptors
                recyclerSuggestion = findViewById<View>(R.id.recyclerSuggestion) as RecyclerView
        recyclerSuggestion!!.setLayoutManager(LinearLayoutManager(this))
        recyclerSuggestion!!.setHasFixedSize(true)

//                set data and list adapter suggestion
        mAdapterSuggestion = AdapterSuggestionSearch(this)
        recyclerSuggestion!!.setAdapter(mAdapterSuggestion)
        showSuggestionSearch()

        mAdapterSuggestion!!.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("click event"," sugee serch" )
               val itemTitle=mAdapterSuggestion!!.items.get(p2)
                et_search!!.setText(itemTitle)
                ViewAnimation.collapse(lyt_suggestion)
                hideKeyboard()
               searchAction()
            }
        })
        // to clear text
        bt_clear!!.setOnClickListener {
            et_search!!.setText("")
        }
        bt_back!!.setOnClickListener {
            finish()
        }

        // for search click
                et_search!!.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
               searchAction()
                return@OnEditorActionListener true
            }
            false
        })

                et_search!!.setOnTouchListener(OnTouchListener { view, motionEvent ->
            showSuggestionSearch()
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            false
        })
        // for wathing text changes in edit text

        et_search!!.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim { it <= ' ' }.length == 0) {
                bt_clear!!.visibility = View.GONE
            } else {
                bt_clear!!.visibility = View.VISIBLE
            }

        }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }
        private fun showSuggestionSearch() {
        mAdapterSuggestion?.refreshItems()
        ViewAnimation.expand(lyt_suggestion)
    }



//
    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
//
    private fun searchAction() {
        progress_bar!!.visibility = View.VISIBLE
        ViewAnimation.collapse(lyt_suggestion)
        lyt_no_result!!.visibility = View.GONE
        val query = et_search!!.text.toString().trim { it <= ' ' }
    Log.d("SearchAction","searching")
        if (query != "") {
            Handler().postDelayed({
                progress_bar!!.visibility = View.GONE
                lyt_no_result!!.visibility = View.VISIBLE
            }, 2000)
            Log.d("SearchAction","searching")
            mAdapterSuggestion!!.addSearchHistory(query)
        } else {
            Toast.makeText(this, "Please fill search input", Toast.LENGTH_SHORT).show()
        }
    }
}
