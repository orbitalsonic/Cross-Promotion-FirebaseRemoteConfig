package com.orbitalsonic.crosspromotion.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orbitalsonic.crosspromotion.viewmodel.PromotionViewModel
import org.koin.android.ext.android.inject

open class BaseActivity: AppCompatActivity() {

    val promotionViewModel: PromotionViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}