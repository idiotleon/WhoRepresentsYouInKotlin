package com.zea7ot.whorepresentsyou.data.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zea7ot.whorepresentsyou.util.DatabaseConstant

@Dao
interface PartyMemberDao {

    @Query("SELECT * from ${DatabaseConstant.TableName.PARTY_MEMBER_TABLE} ORDER BY ${DatabaseConstant.ColumnName.NAME} ASC")
    fun getAlaphabetizedPartyMembers(): LiveData<List<PartyMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(partyMember: PartyMember)
}