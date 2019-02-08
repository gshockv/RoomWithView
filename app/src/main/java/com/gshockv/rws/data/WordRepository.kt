package com.gshockv.rws.data

import android.support.annotation.WorkerThread

class WordRepository(private val wordDao: WordDao) {
    val allWords = wordDao.getAllWords()

    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}
