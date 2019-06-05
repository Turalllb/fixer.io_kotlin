package mobiledimension.exchangerates.di

import android.content.Context

import javax.inject.Singleton

import dagger.Component
import mobiledimension.exchangerates.data.network.ApiFixer
import mobiledimension.exchangerates.di.module.AppModule
import mobiledimension.exchangerates.di.module.DbModule
import mobiledimension.exchangerates.di.module.PathModule
import mobiledimension.exchangerates.ui.mainMenu.MainMenuPresenter
import mobiledimension.exchangerates.ui.mainMenu.MainPresenter
import mobiledimension.exchangerates.ui.mainMenu.MainView

@Singleton
@Component(modules = [AppModule::class, DbModule::class, ApiFixer::class, PathModule::class])
interface AppComponent {

    fun context(): Context

    fun mainPresenter(): MainPresenter<MainView>

    fun mainMenuPresenter(): MainMenuPresenter<MainView>

}
