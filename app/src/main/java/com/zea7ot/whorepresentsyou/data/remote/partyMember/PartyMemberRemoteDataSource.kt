package com.zea7ot.whorepresentsyou.data.remote.partyMember

import com.zea7ot.whorepresentsyou.data.remote.BaseDataSource
import javax.inject.Inject

class PartyMemberRemoteDataSource @Inject constructor(
    private val partyMemberService: PartyMemberService
) : BaseDataSource() {

    suspend fun getPartyMembersRemotelyAsync(zipCode: String) =
        getResult { partyMemberService.getPartyMembersRemotelyAsync(zipCode = zipCode) }
}