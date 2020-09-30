package com.zea7ot.whorepresentsyou.ui.members

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.zea7ot.whorepresentsyou.data.repository.MemberRepository

class MembersViewModel @ViewModelInject constructor(
    private val memberRepository: MemberRepository
) : ViewModel() {
    val members = memberRepository.getMembers("71270")
}