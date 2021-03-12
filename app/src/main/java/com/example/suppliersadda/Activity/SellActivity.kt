package com.example.suppliersadda.Activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import com.example.suppliersadda.R
import com.example.suppliersadda.utils.ViewAnimation
import kotlinx.android.synthetic.main.activity_sell.*

class SellActivity : AppCompatActivity() {

    private val MAX_STEP = 5
    private var current_step = 1
    private var status: TextView? = null
    private var stubs:ViewStub? = null

    //Defining Expand variables
    private val nested_scroll_view: NestedScrollView? = null
    private var bt_toggle_text: ImageButton? = null
    private var bt_hide_text: Button? = null
    private var lyt_expand_text: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

        initToolbar()
        initComponent()
    }

    private fun initToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Sell your service")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
 //       Tools.setSystemBarColor(this)

    }

    private fun initComponent() {
        status = findViewById<View>(R.id.status) as TextView

        //Defining view sub
//        stub = findViewById<View>(R.id.layout_step) as ViewStub;
//        stub!!.layoutResource(R.layout.sell_step1);

//        stubs = findViewById(R.id.layout_step) as ViewStub
//        stubs!!.layoutResource = R.layout.sell_step1
//        stubs!!.inflate()


        // text section
        bt_toggle_text = findViewById<View>(R.id.bt_toggle_text) as ImageButton
        bt_hide_text = findViewById<View>(R.id.bt_hide_text) as Button
        lyt_expand_text = findViewById<View>(R.id.lyt_expand_text)
        lyt_expand_text!!.visibility = View.GONE
        bt_toggle_text!!.setOnClickListener(View.OnClickListener { toggleSectionText(bt_toggle_text!!) })
        bt_hide_text!!.setOnClickListener(View.OnClickListener { toggleSectionText(bt_toggle_text!!) })

        //Trying old way of including xml's
        var step1 = findViewById<View>(R.id.step1__)
        var step2 = findViewById<View>(R.id.step2__)
        var step3 = findViewById<View>(R.id.step3__)
        var step4 = findViewById<View>(R.id.step4__)
        var step5 = findViewById<View>(R.id.step5__)
        stepLoader(current_step)


        (findViewById<View>(R.id.lyt_back) as LinearLayout).setOnClickListener {
            backStep(current_step)
            stepLoader(current_step)
            bottomProgressDots(current_step)
        }
        (findViewById<View>(R.id.lyt_next) as LinearLayout).setOnClickListener {
            nextStep(current_step)
            stepLoader(current_step)
            bottomProgressDots(current_step)
        }
        val str_progress = String.format(
            getString(R.string.step_of),
            current_step, MAX_STEP
        )
        status!!.setText(str_progress)
        bottomProgressDots(current_step)
    }

    private fun nextStep(progress: Int) {
        var progress = progress
        if (progress < MAX_STEP) {
            progress++
            current_step = progress
            ViewAnimation.fadeOutIn(status)
        }
        val str_progress = String.format(
            getString(R.string.step_of),
            current_step, MAX_STEP
        )
        status!!.text = str_progress
    }

    private fun backStep(progress: Int) {
        var progress = progress
        if (progress > 1) {
            progress--
            current_step = progress
            ViewAnimation.fadeOutIn(status)
        }
        val str_progress = String.format(
            getString(R.string.step_of),
            current_step, MAX_STEP
        )
        status!!.text = str_progress
    }

    private fun bottomProgressDots(current_index: Int) {
        var current_index = current_index
        current_index--
        val dotsLayout = findViewById<View>(R.id.layoutDots) as LinearLayout
        val dots =
            arrayOfNulls<ImageView>(MAX_STEP)
        dotsLayout.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(this)
            val width_height = 15
            val params =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams(width_height, width_height))
            params.setMargins(10, 10, 10, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle)
            dots[i]!!.setColorFilter(resources.getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN)
            dotsLayout.addView(dots[i])
        }
        if (dots.size > 0)
        {
            dots[current_index]!!.setImageResource(R.drawable.shape_circle)
            dots[current_index]!!
                .setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
        }
    }

    private fun stepLoader(current_position: Int){
//        if (current_position == 1 && R.layout.sell_step_loading!=null){
//            stubs!!.layoutResource = R.layout.sell_step1
//            stubs!!.inflate()
//        }
//        else if (current_position == 2 && R.layout.sell_step_loading!=null){
//            stubs!!.layoutResource = R.layout.sell_step2
//            stubs!!.inflate()
//        }else{
//            stubs!!.layoutResource = R.layout.sell_step_loading
//            stubs!!.inflate()
//        }

        if(current_position == 1){
            step1__.visibility = View.VISIBLE
            step2__.visibility = View.INVISIBLE
            step3__.visibility = View.INVISIBLE
            step4__.visibility = View.INVISIBLE
            step5__.visibility = View.INVISIBLE
        }
        else if(current_position == 2){
            step1__.visibility = View.INVISIBLE
            step2__.visibility = View.VISIBLE
            step3__.visibility = View.INVISIBLE
            step4__.visibility = View.INVISIBLE
            step5__.visibility = View.INVISIBLE
        }
        else if(current_position == 3){
            step1__.visibility = View.INVISIBLE
            step2__.visibility = View.INVISIBLE
            step3__.visibility = View.VISIBLE
            step4__.visibility = View.INVISIBLE
            step5__.visibility = View.INVISIBLE
        }
        else if(current_position == 4){
            step1__.visibility = View.INVISIBLE
            step2__.visibility = View.INVISIBLE
            step3__.visibility = View.INVISIBLE
            step4__.visibility = View.VISIBLE
            step5__.visibility = View.INVISIBLE
        }
        else if(current_position == 5){
            step1__.visibility = View.INVISIBLE
            step2__.visibility = View.INVISIBLE
            step3__.visibility = View.INVISIBLE
            step4__.visibility = View.INVISIBLE
            step5__.visibility = View.VISIBLE
        }



    }

    private fun toggleSectionText(view: View) {
        val show: Boolean = toggleArrow(view)
        if (show) {
            ViewAnimation.expand(
                lyt_expand_text
            ) {
                if (nested_scroll_view != null) {
                    lyt_expand_text?.let { nestedScrollTo(nested_scroll_view, it) }
                }
            }
        } else {
            ViewAnimation.collapse(lyt_expand_text)
        }
    }

    fun nestedScrollTo(nested: NestedScrollView, targetView: View) {
        nested.post { nested.scrollTo(500, targetView.bottom) }
    }

    fun toggleArrow(view: View): Boolean {
        return if (view.rotation == 0f) {
            view.animate().setDuration(200).rotation(180f)
            true
        } else {
            view.animate().setDuration(200).rotation(0f)
            false
        }
    }

}