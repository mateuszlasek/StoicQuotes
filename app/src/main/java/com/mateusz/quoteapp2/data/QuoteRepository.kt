package com.mateusz.quoteapp2.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class QuoteRepository(private val quoteDao: QuoteDao) {

    suspend fun insert(quote: Quote) = withContext(Dispatchers.IO) {
        quoteDao.insert(quote)
    }

    suspend fun getRandomQuote(): Quote = withContext(Dispatchers.IO) {
        quoteDao.getRandomQuote()
    }
    suspend fun getQuoteById(quoteId: Int): Quote = withContext(Dispatchers.IO){
        quoteDao.getQuoteById(quoteId)
    }

    suspend fun getAllIdsByAuthor(author: String): List<Int> = withContext(Dispatchers.IO){
        quoteDao.getAllIdsByAuthor(author)
    }
    suspend fun getQuotesCount():Int = withContext(Dispatchers.IO){
        quoteDao.getQuotesCount()
    }

    fun getAll(): Flow<List<Quote>> {
        return quoteDao.getAll()
    }

}