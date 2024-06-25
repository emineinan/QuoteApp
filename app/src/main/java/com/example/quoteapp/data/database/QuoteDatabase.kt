package com.example.quoteapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}