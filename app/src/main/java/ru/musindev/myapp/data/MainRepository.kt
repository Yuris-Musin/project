package ru.musindev.myapp.data

import io.reactivex.rxjava3.core.Observable

import ru.musindev.myapp.data.dao.FilmDao

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        //Запросы в БД должны быть в отдельном потоке
        filmDao.insertAll(films)
    }

    fun getAllFromDB(): Observable<List<Film>> = filmDao.getCachedFilms()

}