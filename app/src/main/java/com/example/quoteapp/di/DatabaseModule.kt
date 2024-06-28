package com.example.quoteapp.di

import android.content.Context
import androidx.room.Room
import com.example.quoteapp.R
import com.example.quoteapp.data.database.QuoteDao
import com.example.quoteapp.data.database.QuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): QuoteDatabase {
        return Room.databaseBuilder(
            context,
            QuoteDatabase::class.java,
            name = context.getString(R.string.app_database)
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideQuoteDao(database: QuoteDatabase): QuoteDao {
        return database.quoteDao()
    }
}