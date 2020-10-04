package com.zea7ot.whorepresentsyou.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.zea7ot.whorepresentsyou.util.DatabaseConstant
import com.zea7ot.whorepresentsyou.util.JsonTitle

data class ResMembers(
    @SerializedName(JsonTitle.RESULTS)
    val members: ArrayList<PartyMember>
)

@Entity(tableName = DatabaseConstant.TableName.PARTY_MEMBER_TABLE)
data class PartyMember(
    @SerializedName(JsonTitle.NAME)
    @ColumnInfo(name = DatabaseConstant.ColumnName.NAME)
    val name: String,

    @SerializedName(JsonTitle.PARTY)
    @ColumnInfo(name = DatabaseConstant.ColumnName.PARTY)
    val party: String,

    @SerializedName(JsonTitle.STATE)
    @ColumnInfo(name = DatabaseConstant.ColumnName.STATE)
    val state: String,

    @SerializedName(JsonTitle.DISTRICT)
    @ColumnInfo(name = DatabaseConstant.ColumnName.DISTRICT)
    val district: String,

    @SerializedName(JsonTitle.PHONE)
    @PrimaryKey
    @ColumnInfo(name = DatabaseConstant.ColumnName.PHONE_NUMBER)
    val phoneNumber: String,

    @SerializedName(JsonTitle.OFFICE)
    @ColumnInfo(name = DatabaseConstant.ColumnName.OFFICE)
    val office: String,

    @SerializedName(JsonTitle.LINK)
    @ColumnInfo(name = DatabaseConstant.ColumnName.LINK)
    val link: String
)