package com.orbitalsonic.crosspromotion.listeners

import android.view.View

object OnClickListeners {
    var defaultInterval: Int = 500
    var lastTimeClicked: Long = 0

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

//    fun isMultipleClicks():Boolean{
//        return if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
//            lastTimeClicked = SystemClock.elapsedRealtime()
//            true
//        }else{
//            lastTimeClicked = SystemClock.elapsedRealtime()
//            false
//        }
//
//    }
}