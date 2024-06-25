package com.example.quoteapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Query(
        """
        SELECT * FROM quotes
        ORDER BY quote ASC    
        """
    )
    fun getAll(): Flow<List<Quote>>

    @Insert
    suspend fun addQuote(quote: Quote)

    @Delete
    suspend fun deleteQuote(quote: Quote)
}