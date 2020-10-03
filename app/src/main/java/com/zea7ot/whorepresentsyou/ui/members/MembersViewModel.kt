package com.zea7ot.whorepresentsyou.ui.members

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zea7ot.whorepresentsyou.data.entity.ResMembers
import com.zea7ot.whorepresentsyou.data.repository.MemberRepository
import com.zea7ot.whorepresentsyou.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MembersViewModel @ViewModelInject constructor(
    private val memberRepository: MemberRepository
) : ViewModel() {
    // private val allMembers = MutableLiveData<Resource<ResMember>>()
    val allMembers = MutableLiveData<Resource<ResMembers>>()

    fun getAllMembers(zipCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            allMembers.postValue(memberRepository.getMembers(zipCode))
        }
    }
}