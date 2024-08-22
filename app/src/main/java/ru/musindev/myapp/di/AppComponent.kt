package ru.musindev.myapp.di

import dagger.Component
import ru.musindev.myapp.di.modules.DatabaseModule
import ru.musindev.myapp.di.modules.DomainModule
import ru.musindev.myapp.di.modules.RemoteModule
import ru.musindev.myapp.viewmodel.HomeFragmentViewModel
import ru.musindev.myapp.viewmodel.SettingsFragmentViewModel
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    //метод для того, чтобы появилась возможность внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    //метод для того, чтобы появилась возможность внедрять зависимости в SettingsFragmentViewModel
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}