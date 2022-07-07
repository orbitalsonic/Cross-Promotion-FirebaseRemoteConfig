package com.orbitalsonic.crosspromotion.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.orbitalsonic.crosspromotion.fragment.PromotionPlaceholderFragment

class PromotionPagerAdapter(private val totalCount: Int, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): PromotionPlaceholderFragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a IntroPlaceholderFragment (defined as a static inner class below).
        return PromotionPlaceholderFragment.newInstance(position + 1)
    }

    override fun getCount(): Int {
        // Show total pages.
        return totalCount
    }
}