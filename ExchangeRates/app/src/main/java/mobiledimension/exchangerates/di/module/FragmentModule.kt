package mobiledimension.exchangerates.di.module

import dagger.Module
import dagger.Provides
import mobiledimension.exchangerates.di.PerFragment
import mobiledimension.exchangerates.ui.socialNetwork.SocialNetwork
import mobiledimension.exchangerates.ui.socialNetwork.SocialNetworkPresenter
import mobiledimension.exchangerates.ui.socialNetwork.SocialNetworkView

@Module
class FragmentModule {

    @Provides
    @PerFragment
    internal fun provideSocialNetworkPresenter(fragmentSocialNetworks: SocialNetwork<SocialNetworkView>): SocialNetworkPresenter<SocialNetworkView> {
        return fragmentSocialNetworks
    }

}
