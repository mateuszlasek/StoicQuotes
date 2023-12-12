package com.mateusz.quoteapp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mateusz.quoteapp2.data.model.Favourite
import com.mateusz.quoteapp2.data.model.Quote

@Database(entities = [Quote::class, Favourite::class], version=2, exportSchema = false)
abstract class QuotesDatabase: RoomDatabase(){
    abstract fun quoteDao(): QuoteDao
    abstract fun favouriteDao(): FavouriteDao

    companion object{
        @Volatile
        private var INSTANCE: QuotesDatabase? = null

        fun getDatabase(context: Context): QuotesDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuotesDatabase::class.java,
                    "quotes_database"
                ).createFromAsset("database/stoic_quotes.db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}