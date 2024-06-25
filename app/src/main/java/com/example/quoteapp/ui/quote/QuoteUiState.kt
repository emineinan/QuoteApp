package com.example.quoteapp.ui.quote

import com.example.quoteapp.data.database.Quote

sealed class QuoteUiState {
    data class Success(val quotes: List<Quote>) : QuoteUiState()
    data object Loading : QuoteUiState()
    data class Error(val message: String) : QuoteUiState()
}
