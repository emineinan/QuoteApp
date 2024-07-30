package com.example.quoteapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quoteapp.data.database.Quote
import com.example.quoteapp.data.database.QuoteDao
import com.example.quoteapp.data.database.QuoteDatabase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuoteDaoTest {
    private lateinit var quoteDao: QuoteDao
    private lateinit var quoteDatabase: QuoteDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        quoteDatabase = Room.inMemoryDatabaseBuilder(
            context, QuoteDatabase::class.java
        ).build()
        quoteDao = quoteDatabase.quoteDao()
    }

    @After
    fun closeDb() {
        quoteDatabase.close()
    }

    @Test
    fun quoteDao_insert_and_retrieve_quotes() = runTest {
        val quote1 = Quote(
            id = 1,
            quote = "The only limit to our realization of tomorrow is our doubts of today."
        )
        val quote2 = Quote(
            id = 2,
            quote = "Do not wait to strike till the iron is hot; but make it hot by striking."
        )

        quoteDao.addQuote(quote1)
        quoteDao.addQuote(quote2)

        val quotes = quoteDao.getAll().first()

        assertTrue(quotes.contains(quote1))
        assertTrue(quotes.contains(quote2))
        assertEquals(2, quotes.size)
    }

    @Test
    fun quoteDao_delete_quote() = runTest {
        val quote = Quote(
            id = 1,
            quote = "The only limit to our realization of tomorrow is our doubts of today."
        )

        quoteDao.addQuote(quote)
        quoteDao.deleteQuote(quote)

        val quotes = quoteDao.getAll().first()

        assertFalse(quotes.contains(quote))
    }

    @Test
    fun quoteDao_insert_delete_and_retrieve_quotes() = runTest {
        val quote1 = Quote(
            id = 1,
            quote = "Life is 10% what happens to us and 90% how we react to it."
        )
        val quote2 = Quote(
            id = 2,
            quote = "Do not wait to strike till the iron is hot; but make it hot by striking."
        )

        quoteDao.addQuote(quote1)
        quoteDao.addQuote(quote2)
        quoteDao.deleteQuote(quote2)

        val quotes = quoteDao.getAll().first()

        assertTrue(quotes.contains(quote1))
        assertFalse(quotes.contains(quote2))
        assertEquals(1, quotes.size)
    }
}