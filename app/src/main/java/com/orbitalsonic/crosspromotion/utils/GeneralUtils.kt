package com.orbitalsonic.crosspromotion.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.orbitalsonic.crosspromotion.R


object GeneralUtils {

    fun Activity?.openPlayStoreApp(packageName: String) {
        this?.let {
            try {
                it.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
                )

            } catch (e: ActivityNotFoundException) {
                it.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                    )
                )

            }
        }

    }


    fun Activity?.shareApp() {
        this?.let {
            try {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, it.getString(R.string.app_name))
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "https://play.google.com/store/apps/details?id=${it.packageName}"
                )
                sendIntent.type = "text/plain"
                it.startActivity(sendIntent)
            } catch (e: Exception) {
            }

        }

    }

}