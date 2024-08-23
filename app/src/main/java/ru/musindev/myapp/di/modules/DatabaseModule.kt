package ru.musindev.myapp.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.musindev.myapp.data.DatabaseHelper
import ru.musindev.myapp.data.MainRepository
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabaseHelper(context: Context) = DatabaseHelper(context)

    @Provides
    @Singleton
    fun provideRepository(databaseHelper: DatabaseHelper) = MainRepository(databaseHelper)
}