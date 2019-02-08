package com.gshockv.rws.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "words_table")
class Word(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String
)
