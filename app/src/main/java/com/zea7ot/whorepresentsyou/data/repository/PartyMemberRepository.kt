package com.zea7ot.whorepresentsyou.data.repository

import androidx.lifecycle.LiveData
import com.zea7ot.whorepresentsyou.data.entity.PartyMember
import com.zea7ot.whorepresentsyou.data.entity.PartyMemberDao
import com.zea7ot.whorepresentsyou.data.remote.partyMember.PartyMemberRemoteDataSource
import javax.inject.Inject

class PartyMemberRepository @Inject constructor(
    private val partyMemberRemoteDataSource: PartyMemberRemoteDataSource,
    private val partyMemberDao: PartyMemberDao
) {
    val allLocalPartyMembers: LiveData<List<PartyMember>> =
        partyMemberDao.getAlaphabetizedPartyMembers()

    suspend fun insert(partyMember: PartyMember) {
        partyMemberDao.insert(partyMember)
    }

    suspend fun getPartyMembersRemotelyAsync(zipCode: String) =
        partyMemberRemoteDataSource.getPartyMembersRemotelyAsync(zipCode)
}