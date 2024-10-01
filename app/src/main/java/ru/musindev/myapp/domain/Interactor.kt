package ru.musindev.myapp.domain


import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.musindev.myapp.API
import ru.musindev.myapp.data.Film
import ru.musindev.myapp.data.MainRepository
import ru.musindev.myapp.data.PreferenceProvider
import ru.musindev.myapp.data.TmdbApi
import ru.musindev.myapp.utils.Converter

class Interactor(
    private val repo: MainRepository,
    private val retrofitService: TmdbApi,
    private val preferences: PreferenceProvider) {

    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        // Показываем ProgressBar
        progressBarState.onNext(true)

        //Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResultsDto> {
            override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {

                val list = Converter.convertApiListToDtoList(response.body()?.tmdbFilms)
                //Кладем фильмы в бд
                //При успехе мы кладем фильмы в бд и отключаем progressBar
                Completable
                    .fromSingle<List<Film>> {
                        repo.putToDb(list)
                    }.subscribeOn(Schedulers.io())
                    .subscribe()
                progressBarState.onNext(false)

            }
            override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека
                progressBarState.onNext(false)
            }
        })
    }
    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }
    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()

}