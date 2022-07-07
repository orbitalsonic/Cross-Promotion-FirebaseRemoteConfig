package com.orbitalsonic.crosspromotion

import android.app.Application
import com.orbitalsonic.crosspromotion.viewmodel.PagerViewModel
import com.orbitalsonic.crosspromotion.viewmodel.PromotionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication:Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@MainApplication)
            modules(listOf(viewModelModule))
        }
    }

    private val viewModelModule = module{
        single { PromotionViewModel(this@MainApplication) }
        single { PagerViewModel() }
    }

}