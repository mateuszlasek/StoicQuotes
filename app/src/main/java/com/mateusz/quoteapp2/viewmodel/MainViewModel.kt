package com.mateusz.quoteapp2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mateusz.quoteapp2.data.database.QuotesDatabase
import com.mateusz.quoteapp2.data.model.Favourite
import com.mateusz.quoteapp2.data.model.Quote
import com.mateusz.quoteapp2.repository.FavouriteRepository
import com.mateusz.quoteapp2.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: QuoteRepository
    private val repositoryFav: FavouriteRepository
    init {
        val quoteDao = QuotesDatabase.getDatabase(app).quoteDao()
        val favouriteDao = QuotesDatabase.getDatabase(app).favouriteDao()
        repository = QuoteRepository(quoteDao)
        repositoryFav = FavouriteRepository(favouriteDao)
    }

    fun getQuotes(): Flow<List<Quote>>  {
        return repository.getAll()
    }
    suspend fun getQuoteById(quoteId: Int): Quote {
        return repository.getQuoteById(quoteId)
    }
    suspend fun getAllIdsByAuthor(author: String): List<Int>{
        return repository.getAllIdsByAuthor(author)
    }
    suspend fun getRandomQuote(): Quote {
        return repository.getRandomQuote()
    }
    suspend fun getQuotesCount(): Int{
        return repository.getQuotesCount()
    }
    suspend fun addToQuotes(quote: Quote){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(quote)
        }
    }
    fun getFavorites(): Flow<List<Favourite>>{
        return repositoryFav.getAll()
    }
    suspend fun getFavouriteById(favouriteId: Int): Favourite {
        return repositoryFav.getFavouriteById(favouriteId)
    }
    suspend fun addToFavourites(quote: Quote){
        val favouriteQuote = Favourite(quote.quote, quote.author, quote.source, quote.id)
        viewModelScope.launch(Dispatchers.IO){
            repositoryFav.insert(favouriteQuote)
        }
    }
    suspend fun removeFromFavourites(quote: Quote){
        val favouriteQuote = Favourite(quote.quote, quote.author, quote.source, quote.id)
        repositoryFav.delete(favouriteQuote)
    }
    suspend fun getFavouritesCount(): Int{
        return repositoryFav.getFavouritesCount()
    }


}