package com.goddoro.udc.di.module

import android.content.Context
import androidx.room.Room
import com.goddoro.udc.di.AppScope
import dagger.Module
import dagger.Provides


/**
 * created By DORO 2020/08/15
 */

@Module
class AppModule {

//    @Provides
//    @AppScope
//    fun provideRoomDatabase(context : Context) : AppDatabase {
//        return Room.databaseBuilder(context,AppDatabase::class.java,AppDatabase.DATABASE_NAME)
//            .build()
//    }
//
//    @Provides
//    @AppScope
//    fun provideSearchDao(database : AppDatabase) : SearchDao {
//        return database.searchDao()
//    }
}