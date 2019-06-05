package mobiledimension.exchangerates.di.module

import dagger.Module
import dagger.Provides
import mobiledimension.exchangerates.ui.mainMenu.NetworkChangeReceiver

@Module
class ActivityModule {

    @Provides
    internal fun provideNetworkChangeReceiver(): NetworkChangeReceiver {
        return NetworkChangeReceiver()
    }

}
