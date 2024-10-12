package ru.musindev.remote_module

interface RemoteProvider {
    fun provideRemote(): TmdbApi
}