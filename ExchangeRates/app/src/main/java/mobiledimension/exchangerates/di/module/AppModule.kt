package mobiledimension.exchangerates.di.module

import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import mobiledimension.exchangerates.ui.mainMenu.MainMenuPresenter
import mobiledimension.exchangerates.ui.mainMenu.MainPresenter
import mobiledimension.exchangerates.ui.mainMenu.MainView

@Module
class AppModule(private val context: Context) {

    @Provides
    internal fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    internal fun provideMainPresenter(presenter: MainMenuPresenter<MainView>): MainPresenter<MainView> {
        return presenter
    }


}
