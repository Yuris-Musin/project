package ru.musindev.myapp.domain

import ru.musindev.myapp.data.MainRepository

class Interactor(val repo: MainRepository) {
    fun getFilmsDB(): List<Film> = repo.filmsDataBase
}