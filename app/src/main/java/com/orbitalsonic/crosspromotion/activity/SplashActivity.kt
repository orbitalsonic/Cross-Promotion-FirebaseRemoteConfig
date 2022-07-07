package com.orbitalsonic.crosspromotion.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.orbitalsonic.crosspromotion.listeners.OnClickListeners.setSafeOnClickListener
import com.orbitalsonic.crosspromotion.R
import com.orbitalsonic.crosspromotion.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var remoteConfig: FirebaseRemoteConfig
    val cross_promotion = "cross_promotion"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        binding.btnStart.setSafeOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        initRemoteConfig()
    }

    private fun initRemoteConfig() {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 2
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        fetchRemoteValues()
    }

    private fun fetchRemoteValues() {
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            updateRemoteValues()
        }

    }

    private fun updateRemoteValues() {
        promotionViewModel.setPromotionListList(remoteConfig[cross_promotion].asString())
        binding.loadingProgress.visibility = View.INVISIBLE
        binding.tvLoading.text = "Data Successfully get"
    }
}