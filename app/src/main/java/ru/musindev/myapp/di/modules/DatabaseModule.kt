package ru.musindev.myapp.di.modules

import dagger.Module
import dagger.Provides
import ru.musindev.myapp.data.MainRepository
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRepository() = MainRepository()
}