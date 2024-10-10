package ru.musindev.myapp.di.modules

import android.content.Context
import ru.musindev.myapp.data.MainRepository
import ru.musindev.remote_module.TmdbApi
import ru.musindev.myapp.domain.Interactor
import dagger.Module
import dagger.Provides
import ru.musindev.myapp.data.PreferenceProvider
import javax.inject.Singleton

@Module
//Передаем контекст для SharedPreferences через конструктор
class DomainModule(val context: Context) {
    //Нам нужно контекст как-то провайдить, поэтому создаем такой метод
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    //Создаем экземпляр SharedPreferences
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(
        repository: MainRepository,
        tmdbApi: TmdbApi,
        preferenceProvider: PreferenceProvider) = Interactor(
        repo = repository,
        retrofitService = tmdbApi,
        preferences = preferenceProvider)
}