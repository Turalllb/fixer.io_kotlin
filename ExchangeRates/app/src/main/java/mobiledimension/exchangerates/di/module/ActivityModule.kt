package mobiledimension.exchangerates.di.module

import dagger.Module
import dagger.Provides
import mobiledimension.exchangerates.utils.NetworkChangeReceiver

@Module
class ActivityModule {

    @Provides
    internal fun provideNetworkChangeReceiver(): NetworkChangeReceiver {
        return NetworkChangeReceiver()
    }

}
