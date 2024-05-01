package com.example.newcekirge.di

import android.content.Context
import androidx.room.Room
import com.example.newcekirge.Utilies.Constants
import com.example.newcekirge.repository.Repository
import com.example.newcekirge.room.UniDao
import com.example.newcekirge.room.UniDatabase
import com.example.newcekirge.service.UniversityService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofitService(): UniversityService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_UNI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UniversityService::class.java)
    }



    @Provides
    @Singleton
    fun provideRepository(
        uniDao: UniDao,universityService: UniversityService
    ): Repository {
        return Repository(uniDao,universityService)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): UniDatabase {
        return Room.databaseBuilder(
            appContext,
            UniDatabase::class.java,"university_database",
        ).build()
    }

    @Provides
    fun provideUniDao(database: UniDatabase): UniDao {
        return database.uniDao()
    }
}

