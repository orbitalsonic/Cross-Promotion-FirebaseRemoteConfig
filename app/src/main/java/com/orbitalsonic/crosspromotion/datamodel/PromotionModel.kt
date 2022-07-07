package com.orbitalsonic.crosspromotion.datamodel

import com.google.gson.annotations.SerializedName


data class PromotionModel(
    @SerializedName("title" ) var title: String,
    @SerializedName("short_desc" ) var short_desc: String,
    @SerializedName("package" ) var mPackage: String,
    @SerializedName("icon" ) var icon: String,
    @SerializedName("feature" ) var feature: String
)