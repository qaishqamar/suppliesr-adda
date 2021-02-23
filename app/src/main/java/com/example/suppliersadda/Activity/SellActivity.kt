package com.example.suppliersadda.Activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.suppliersadda.R
import com.example.suppliersadda.utils.ViewAnimation

class SellActivity : AppCompatActivity() {

    private val MAX_STEP = 6
    private var current_step = 1
    private var status: TextView? = null

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
        (findViewById<View>(R.id.lyt_back) as LinearLayout).setOnClickListener {
            backStep(current_step)
            bottomProgressDots(current_step)
        }
        (findViewById<View>(R.id.lyt_next) as LinearLayout).setOnClickListener {
            nextStep(current_step)
            bottomProgressDots(current_step)
        }
        val str_progress = String.format(
            getString(R.string.step_of),
            current_step,MAX_STEP
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
        if (dots.size > 0) {
            dots[current_index]!!.setImageResource(R.drawable.shape_circle)
            dots[current_index]!!
                .setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
        }
    }
}