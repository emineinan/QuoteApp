package com.example.quoteapp.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.quoteapp.Result
import com.example.quoteapp.data.database.Quote
import com.example.quoteapp.data.repository.QuoteRepository

class GetQuotesUseCase @Inject constructor(private val repository: QuoteRepository) {
    operator fun invoke(): Flow<Result<List<Quote>>> = repository.getAllQuotes()
}
