package com.zea7ot.whorepresentsyou.data.repository

import com.zea7ot.whorepresentsyou.data.remote.MemberRemoteDataSource
import com.zea7ot.whorepresentsyou.util.performGetOperation
import javax.inject.Inject

class MemberRepository @Inject constructor(
    private val remoteDataSource: MemberRemoteDataSource
) {
    fun getMembers(zipCode: String) = performGetOperation(
        networkCall = { remoteDataSource.getMembers(zipCode) }
    )
}