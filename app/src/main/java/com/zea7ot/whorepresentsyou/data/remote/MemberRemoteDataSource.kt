package com.zea7ot.whorepresentsyou.data.remote

import javax.inject.Inject

class MemberRemoteDataSource @Inject constructor(
    private val memberService: MemberService
) : BaseDataSource() {

    suspend fun getMembers(zipCode: String) = getResult { memberService.getAllMembers(zipCode) }
}