package com.orbitalsonic.crosspromotion.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.orbitalsonic.crosspromotion.adapters.PromotionPagerAdapter
import com.orbitalsonic.crosspromotion.R
import com.orbitalsonic.crosspromotion.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mHandler = Handler(Looper.getMainLooper())
    private val slideRunner = Runnable { showPromotionSlide() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        addPromotion()
    }

    private fun addPromotion() {
        if (promotionViewModel.promotionList.isNotEmpty()) {
            binding.promotionContainer.visibility = View.VISIBLE
            if (promotionViewModel.promotionList.size == 1) {
                binding.promotionIndicatorView.visibility = View.GONE
            } else {
                binding.promotionIndicatorView.visibility = View.VISIBLE
            }


            val pagerAdapter = PromotionPagerAdapter(
                promotionViewModel.promotionList.size,
                supportFragmentManager
            )
            binding.promotionViewPager.adapter = pagerAdapter

            binding.promotionIndicatorView.setPageSize(binding.promotionViewPager.adapter!!.count)
            binding.promotionIndicatorView.setupWithViewPager(binding.promotionViewPager)
            binding.promotionViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    binding.promotionIndicatorView.onPageSelected(position)
//                    btnBeforeNavigate.isEnabled = position != 0
//                    if (position==viewPager.adapter!!.count-1){
//                        btnNextNavigate.setImageResource(R.drawable.ic_baseline_done_24)
//                    }else if (position==viewPager.adapter!!.count-2){
//                        btnNextNavigate.setImageResource(R.drawable.ic_baseline_navigate_next_24)
//                    }
                }

                override fun onPageScrollStateChanged(state: Int) {

                }

            })
        } else {
            binding.promotionContainer.visibility = View.GONE
        }

    }

    private fun showPromotionSlide() {
        if (promotionViewModel.promotionList.isNotEmpty()) {
            if (promotionViewModel.promotionList.size != 1) {

                if (binding.promotionViewPager.currentItem < promotionViewModel.promotionList.size - 1) {
                    binding.promotionViewPager.currentItem = binding.promotionViewPager.currentItem + 1
                } else {
                    binding.promotionViewPager.currentItem = 0
                }
                mHandler.removeCallbacks { slideRunner }
                mHandler.postDelayed(
                    slideRunner,
                    (5000)
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mHandler.removeCallbacks(slideRunner)
    }

    override fun onResume() {
        super.onResume()
        //Start Drawing
        mHandler.post(slideRunner)
    }
}