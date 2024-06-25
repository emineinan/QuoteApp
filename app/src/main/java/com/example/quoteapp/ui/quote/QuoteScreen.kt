package com.example.quoteapp.ui.quote

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quoteapp.R
import com.example.quoteapp.data.database.Quote

@Composable
fun QuoteScreen(viewModel: QuoteViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var text by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 64.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QuoteInputField(
                text = text,
                onTextChange = { newText -> text = newText }
            )
            QuoteList(uiState = uiState, viewModel = viewModel)
        }
        AddQuoteButton(
            onClick = {
                viewModel.addQuote(Quote(quote = text))
                text = "" // Clear the text field after adding
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun QuoteInputField(
    text: String,
    onTextChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = { newText -> onTextChange(newText) },
        label = { Text(stringResource(R.string.enter_quote), color = Color.Gray) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
    )
}

@Composable
fun QuoteList(uiState: QuoteUiState, viewModel: QuoteViewModel) {
    when (uiState) {
        is QuoteUiState.Success -> {
            val quotes = uiState.quotes
            QuoteList(quotes, viewModel)
        }

        QuoteUiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is QuoteUiState.Error -> {
            Text(
                text = stringResource(R.string.error, uiState.message),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun QuoteList(
    quotes: List<Quote>,
    viewModel: QuoteViewModel
) {
    LazyColumn {
        items(
            items = quotes,
            key = { quote ->
                quote.id
            }
        ) { quote ->
            QuoteItem(quote = quote, onDelete = {
                viewModel.deleteQuote(quote = quote)
            })
        }
    }
}

@Composable
fun AddQuoteButton(onClick: () -> Unit, modifier: Modifier) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.padding(vertical = 64.dp, horizontal = 16.dp),
    ) {
        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_quote))
    }
}

@Composable
fun QuoteItem(quote: Quote, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = quote.quote,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(all = 8.dp)
            )
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = Color.Red
                )
            }
        }
    }
}