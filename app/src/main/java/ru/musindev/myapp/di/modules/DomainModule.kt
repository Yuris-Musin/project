package ru.musindev.myapp.di.modules

import ru.musindev.myapp.data.MainRepository
import ru.musindev.myapp.data.TmdbApi
import ru.musindev.myapp.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) = Interactor(repo = repository, retrofitService = tmdbApi)
}