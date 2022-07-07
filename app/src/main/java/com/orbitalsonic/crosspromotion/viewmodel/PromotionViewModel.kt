package com.orbitalsonic.crosspromotion.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.orbitalsonic.crosspromotion.datamodel.PromotionModel
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class PromotionViewModel(application: Application) : AndroidViewModel(application) {
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("CrossPromotionTag", "$exception")
    }

    var promotionList: ArrayList<PromotionModel> = ArrayList()

    fun setPromotionListList(jsonData:String?) {
        if (promotionList.isEmpty()) {
            GlobalScope.launch(Dispatchers.Main + handler) {
                async(Dispatchers.IO + handler) {
                    try {
                        jsonData?.let {
                            val jsonarry = JSONArray(it)
                            for (i in 0 until jsonarry.length()) {
                                val jsonobject: JSONObject = jsonarry.getJSONObject(i)

                                val mName = jsonobject.getString("title")
                                val mShortDescription = jsonobject.getString("short_desc")
                                val mPackage = jsonobject.getString("package")
                                val mIcon = jsonobject.getString("icon")
                                val mFeature = jsonobject.getString("feature")

                                promotionList.add(
                                    PromotionModel(
                                        title = mName,
                                        short_desc = mShortDescription,
                                        mPackage = mPackage,
                                        icon = mIcon,
                                        feature = mFeature
                                    )
                                )
                            }
                        }


                    } catch (e: JSONException) {

                    }

                }.await()

            }
        }

    }

    fun cleanList() {
        promotionList.clear()
        promotionList = ArrayList()
    }


}