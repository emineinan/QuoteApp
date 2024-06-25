package com.example.quoteapp.ui.quote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteapp.data.database.Quote
import com.example.quoteapp.domain.AddQuoteUseCase
import com.example.quoteapp.domain.DeleteQuoteUseCase
import com.example.quoteapp.domain.GetQuotesUseCase
import com.example.quoteapp.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getQuotesUseCase: GetQuotesUseCase,
    private val addQuoteUseCase: AddQuoteUseCase,
    private val deleteQuoteUseCase: DeleteQuoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<QuoteUiState>(QuoteUiState.Loading)
    val uiState: StateFlow<QuoteUiState> = _uiState

    init {
        getQuotes()
    }

    private fun getQuotes() {
        viewModelScope.launch {
            getQuotesUseCase().collect { result ->
                _uiState.value = when (result) {
                    is Result.Success -> QuoteUiState.Success(result.data)
                    is Result.Loading -> QuoteUiState.Loading
                    is Result.Error -> QuoteUiState.Error(
                        result.exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun addQuote(quote: Quote) {
        viewModelScope.launch {
            when (val result = addQuoteUseCase(quote)) {
                is Result.Success -> getQuotes()
                is Result.Loading -> QuoteUiState.Loading
                is Result.Error -> _uiState.value =
                    QuoteUiState.Error(result.exception.message ?: "Failed to add quote")
            }
        }
    }

    fun deleteQuote(quote: Quote) {
        viewModelScope.launch {
            when (val result = deleteQuoteUseCase(quote)) {
                is Result.Success -> getQuotes()
                is Result.Loading -> QuoteUiState.Loading
                is Result.Error -> _uiState.value =
                    QuoteUiState.Error(result.exception.message ?: "Failed to delete quote")
            }
        }
    }
}