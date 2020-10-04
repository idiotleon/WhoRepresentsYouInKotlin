package com.zea7ot.whorepresentsyou.di

import android.content.Context
import com.zea7ot.whorepresentsyou.data.PartyMemberRoomDatabase
import com.zea7ot.whorepresentsyou.data.entity.PartyMemberDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun providePartyMemberDao(partyMemberRoomDatabase: PartyMemberRoomDatabase): PartyMemberDao {
        return partyMemberRoomDatabase.partyMemberDao()
    }

    @Provides
    @Singleton
    fun providePartyMemberRoomDatabase(
        @ApplicationContext context: Context
    ): PartyMemberRoomDatabase {
        return PartyMemberRoomDatabase.getDatabase(context)
    }
}