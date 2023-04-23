package com.mateusz.quoteapp2.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FavouriteRepository(private val favouriteDao: FavouriteDao) {

    fun getAll(): Flow<List<Favourite>>  {
        return favouriteDao.getAll()
    }

    suspend fun insert(favourite: Favourite) = withContext(Dispatchers.IO) {
        favouriteDao.insert(favourite)
    }
    suspend fun getFavouriteById(favouriteId: Int): Favourite = withContext(Dispatchers.IO){
        favouriteDao.getFavouriteById(favouriteId)
    }
    suspend fun getFavouritesCount():Int = withContext(Dispatchers.IO){
        favouriteDao.getFavouritesCount()
    }

   suspend fun delete(favourite: Favourite) = withContext(Dispatchers.IO) {
       favouriteDao.delete(favourite)
    }

}