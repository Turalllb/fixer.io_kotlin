package mobiledimension.exchangerates.di

import dagger.Component
import mobiledimension.exchangerates.di.module.ActivityModule
import mobiledimension.exchangerates.ui.MainMenu.MainMenu

@PerActivity
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(mainMenu: MainMenu)

}
