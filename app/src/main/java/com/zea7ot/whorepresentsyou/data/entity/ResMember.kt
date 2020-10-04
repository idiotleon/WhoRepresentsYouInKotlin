package com.zea7ot.whorepresentsyou.data.entity

import com.google.gson.annotations.SerializedName
import com.zea7ot.whorepresentsyou.util.JsonTitle

data class ResMembers(
    @SerializedName(JsonTitle.RESULTS)
    val members: ArrayList<ResMember>
)

data class ResMember(
    @SerializedName(JsonTitle.NAME)
    val name: String,
    @SerializedName(JsonTitle.PARTY)
    val party: String,
    @SerializedName(JsonTitle.STATE)
    val state: String,
    @SerializedName(JsonTitle.DISTRICT)
    val district: String,
    @SerializedName(JsonTitle.PHONE)
    val phone: String,
    @SerializedName(JsonTitle.OFFICE)
    val office: String,
    @SerializedName(JsonTitle.LINK)
    val link: String
)