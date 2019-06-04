package mobiledimension.exchangerates.di.module;

import dagger.Module;
import dagger.Provides;
import mobiledimension.exchangerates.di.PerFragment;
import mobiledimension.exchangerates.presenter.socialNetwork.SocialNetwork;
import mobiledimension.exchangerates.presenter.socialNetwork.SocialNetworkPresenter;
import mobiledimension.exchangerates.ui.socialNetwork.SocialNetworkView;

@Module
public class FragmentModule {

    @Provides
    @PerFragment
    SocialNetworkPresenter<SocialNetworkView> provideSocialNetworkPresenter(SocialNetwork<SocialNetworkView> fragmentSocialNetworks) {
        return fragmentSocialNetworks;
    }

}
