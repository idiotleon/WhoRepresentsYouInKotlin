package com.zea7ot.whorepresentsyou.data.remote.partyMember

import com.zea7ot.whorepresentsyou.data.entity.ResMembers
import com.zea7ot.whorepresentsyou.util.ApiQuery
import com.zea7ot.whorepresentsyou.util.ApiUrl
import com.zea7ot.whorepresentsyou.util.DefaultValue
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PartyMemberService {
    @GET
    suspend fun getPartyMembersRemotelyAsync(
        @Url baseUrl: String = "${ApiUrl.Member.BASE_URL}/${ApiUrl.Member.GET_MEMBERS}",
        @Query(ApiQuery.ZIP) zipCode: String,
        @Query(ApiQuery.OUTPUT) output: String = DefaultValue.ApiQuery.OUTPUT
    ): Response<ResMembers>
}