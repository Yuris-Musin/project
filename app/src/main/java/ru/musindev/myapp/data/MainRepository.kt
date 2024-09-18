package ru.musindev.myapp.data

import kotlinx.coroutines.flow.Flow
import ru.musindev.myapp.data.dao.FilmDao

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        //Запросы в БД должны быть в отдельном потоке
        filmDao.insertAll(films)
    }

    fun getAllFromDB(): Flow<List<Film>> = filmDao.getCachedFilms()
}