package com.mateusz.quoteapp2.data.database

import androidx.room.*
import com.mateusz.quoteapp2.data.model.Favourite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favourite: Favourite)

    @Delete
    suspend fun delete(favourite: Favourite)

    @Query("SELECT * FROM favourites WHERE id = :favouriteId")
    suspend fun getFavouriteById(favouriteId: Int): Favourite

    @Query("SELECT COUNT(*) FROM favourites")
    fun getFavouritesCount(): Int

    @Query("SELECT * FROM favourites")
    fun getAll(): Flow<List<Favourite>>

}