package com.example.quoteapp.domain

import javax.inject.Inject
import com.example.quoteapp.Result
import com.example.quoteapp.data.database.Quote
import com.example.quoteapp.data.repository.QuoteRepository

class AddQuoteUseCase @Inject constructor(private val repository: QuoteRepository) {
    suspend operator fun invoke(quote: Quote): Result<Unit> = repository.addQuote(quote)
}