package com.gshockv.rws.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface WordDao {
    @Query("SELECT * FROM words_table")
    fun getAllWords() : LiveData<List<Word>>

    @Insert
    fun insert(word: Word)

    @Query("DELETE FROM words_table")
    fun deleteAll()
}
