package com.zea7ot.whorepresentsyou.data.entity

import com.google.gson.annotations.SerializedName
import com.zea7ot.whorepresentsyou.util.DefaultValue
import com.zea7ot.whorepresentsyou.util.JsonTitle
import com.zea7ot.whorepresentsyou.util.JsonValue

data class ResAddress(
    @SerializedName(JsonTitle.RESULTS)
    val results: Results
) {
    fun getZipCode(): String = results.getZipCode()
}

data class Results(
    @SerializedName(JsonTitle.ADDRESS_COMPONENTS)
    val addressComponent: ArrayList<AddressComponent>,

    @SerializedName(JsonTitle.FORMATTED_ADDRESS)
    val formattedAddress: String,

    @SerializedName(JsonTitle.PLACE_ID)
    val placeId: String,

    @SerializedName(JsonTitle.TYPES)
    val types: ArrayList<String>
) {
    fun getZipCode(): String {
        val res = addressComponent.filter { it.types.contains(JsonValue.POSTAL_CODE) }
        if (res.isEmpty()) return DefaultValue.Invalid.POSTAL_CODE_STRING
        return res[0].shortName
    }
}

data class AddressComponent(
    @SerializedName(JsonTitle.LONG_NAME)
    val longName: String,

    @SerializedName(JsonTitle.SHORT_NAME)
    val shortName: String,

    @SerializedName(JsonTitle.TYPES)
    val types: ArrayList<String>
)
