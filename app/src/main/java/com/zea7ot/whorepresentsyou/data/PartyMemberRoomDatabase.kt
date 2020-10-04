package com.zea7ot.whorepresentsyou.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zea7ot.whorepresentsyou.data.entity.PartyMember
import com.zea7ot.whorepresentsyou.data.entity.PartyMemberDao
import com.zea7ot.whorepresentsyou.util.DatabaseConstant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [PartyMember::class], version = 1, exportSchema = false)
abstract class PartyMemberRoomDatabase : RoomDatabase() {

    abstract fun partyMemberDao(): PartyMemberDao

    companion object {
        @Volatile
        private var INSTANCE: PartyMemberRoomDatabase? = null

        fun getDatabase(context: Context): PartyMemberRoomDatabase {
            val tempInstance = INSTANCE
            tempInstance?.let {
                return it
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PartyMemberRoomDatabase::class.java,
                    DatabaseConstant.TableName.PARTY_MEMBER_TABLE
                )
                    // .addCallback(PartyMemberDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class PartyMemberDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.partyMemberDao())
                }
            }
        }

        suspend fun populateDatabase(partyMemberDao: PartyMemberDao) {

        }
    }
}