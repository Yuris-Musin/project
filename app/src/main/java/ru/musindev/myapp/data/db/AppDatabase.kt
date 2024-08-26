package ru.musindev.myapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.musindev.myapp.data.Film
import ru.musindev.myapp.data.dao.FilmDao

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}