package com.dfavilav.zararickmorty.di

import android.content.Context
import androidx.room.Room
import com.dfavilav.zararickmorty.data.local.RickAndMortyDatabase
import com.dfavilav.zararickmorty.data.repository.LocalDataSourceImpl
import com.dfavilav.zararickmorty.domain.repository.LocalDataSource
import com.dfavilav.zararickmorty.util.Constants.RAM_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RickAndMortyDatabase {
        return Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,
            RAM_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        database: RickAndMortyDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(
            database
        )
    }

}