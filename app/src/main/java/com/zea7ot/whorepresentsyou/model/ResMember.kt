package com.zea7ot.whorepresentsyou.model

import com.google.gson.annotations.SerializedName
import com.zea7ot.whorepresentsyou.util.JsonTitle

data class ResMembers(
    val members: List<ResMember>
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