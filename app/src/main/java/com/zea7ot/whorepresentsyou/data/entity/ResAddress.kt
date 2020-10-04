package com.zea7ot.whorepresentsyou.data.entity

import com.google.gson.annotations.SerializedName
import com.zea7ot.whorepresentsyou.util.DefaultValue
import com.zea7ot.whorepresentsyou.util.JsonTitle
import com.zea7ot.whorepresentsyou.util.JsonValue
import timber.log.Timber

data class ResAddress(
    @SerializedName(JsonTitle.RESULTS)
    val results: ArrayList<Result>
) {
    fun getZipCode(): String =
        if (results.isEmpty()) DefaultValue.Invalid.POSTAL_CODE_STRING else results[0].getZipCode()
}

data class Result(
    @SerializedName(JsonTitle.ADDRESS_COMPONENTS)
    val addressComponents: ArrayList<AddressComponent>,

    @SerializedName(JsonTitle.FORMATTED_ADDRESS)
    val formattedAddress: String,

    @SerializedName(JsonTitle.PLACE_ID)
    val placeId: String,

    @SerializedName(JsonTitle.TYPES)
    val types: ArrayList<String>
) {
    fun getZipCode(): String {
        val res = addressComponents.filter { it.types.contains(JsonValue.POSTAL_CODE) }
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
