package com.zea7ot.whorepresentsyou.data.repository

import com.zea7ot.whorepresentsyou.data.remote.member.MemberRemoteDataSource
import javax.inject.Inject

class MemberRepository @Inject constructor(
    private val remoteDataSource: MemberRemoteDataSource
) {
    suspend fun getMembers(zipCode: String) = remoteDataSource.getMembers(zipCode)
}