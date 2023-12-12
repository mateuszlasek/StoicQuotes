package com.mateusz.quoteapp2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="quotes")
class Quote(
    val quote: String,
    val author: String,
    val source: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int
)