package com.zea7ot.whorepresentsyou.ui.partyMembers

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.zea7ot.whorepresentsyou.data.repository.PartyMemberRepository
import com.zea7ot.whorepresentsyou.data.entity.PartyMember
import com.zea7ot.whorepresentsyou.data.entity.ResMembers
import com.zea7ot.whorepresentsyou.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class PartyMembersViewModel @ViewModelInject constructor(
    application: Application,
    private val partyMemberRepository: PartyMemberRepository
) : AndroidViewModel(application) {

    // val allLocalPartyMembers: LiveData<List<PartyMember>>
    val fetchedPartyMember = MutableLiveData<Resource<ResMembers>>()

//    init {
//        val partyMemberDao =
//            PartyMemberRoomDatabase.getDatabase(application, viewModelScope).partyMemberDao()
//        partyMemberRepository = PartyMemberRepository(partyMemberRemoteDataSource, partyMemberDao)
//        allLocalPartyMembers = partyMemberRepository.allLocalPartyMembers
//    }

    fun insert(partyMember: PartyMember) = viewModelScope.launch(Dispatchers.IO) {
        Timber.d("inserted: ${partyMember.name}")
        partyMemberRepository.insert(partyMember)
    }

    fun getPartyMembersRemotelyAsync(zipCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val members = partyMemberRepository.getPartyMembersRemotelyAsync(zipCode)
            fetchedPartyMember.postValue(members)
        }
    }
}