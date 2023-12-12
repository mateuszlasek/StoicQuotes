package com.mateusz.quoteapp2.data.database

import androidx.room.*
import com.mateusz.quoteapp2.data.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quote: Quote)

    @Query("SELECT * FROM quotes ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuote(): Quote

    @Query("SELECT * FROM quotes WHERE id = :quoteId")
    suspend fun getQuoteById(quoteId: Int): Quote

    @Query("SELECT id FROM quotes WHERE author = :author")
    suspend fun getAllIdsByAuthor(author: String): List<Int>

    @Query("SELECT COUNT(*) FROM quotes")
    suspend fun getQuotesCount(): Int

    @Query("SELECT * FROM quotes")
    fun getAll(): Flow<List<Quote>>

}