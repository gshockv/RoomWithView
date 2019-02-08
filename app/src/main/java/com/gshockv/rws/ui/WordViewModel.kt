package com.gshockv.rws.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.gshockv.rws.data.Word
import com.gshockv.rws.data.WordRepository
import com.gshockv.rws.data.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WordViewModel(app: Application) : AndroidViewModel(app) {

    private val repository : WordRepository
    val allWords : LiveData<List<Word>>

    private var parentJob = Job()
    private val coroutineContext : CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val wordDao = WordRoomDatabase.getDatabase(app, scope).wordDao()
        repository = WordRepository(wordDao)
        allWords = repository.allWords
    }

    fun insert(word: Word) = scope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}