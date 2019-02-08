package com.gshockv.rws.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ Word::class ], version = 1)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao() : WordDao

    companion object {
        @Volatile
        private var INSTANCE : WordRoomDatabase? = null

        fun getDatabase(context: Context, coroutineScope: CoroutineScope) : WordRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "words_database"
                ).addCallback(WordDatabaseCallback(coroutineScope)).build()

                INSTANCE = instance
                return instance
            }
        }
    }

    private class WordDatabaseCallback(private val coroutineScope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {
                coroutineScope.launch(Dispatchers.IO) {
                    populateDatabase(it.wordDao())
                }
            }
        }

        private fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()

            var word = Word("Room")
            wordDao.insert(word)
            word = Word("Database")
            wordDao.insert(word)
        }
    }
}
