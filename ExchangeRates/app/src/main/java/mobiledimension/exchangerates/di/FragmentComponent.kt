package mobiledimension.exchangerates.di

import dagger.Component
import mobiledimension.exchangerates.di.module.FragmentModule
import mobiledimension.exchangerates.ui.socialNetwork.FragmentSocialNetworks

@PerFragment
@Component(dependencies = [AppComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(fragmentSocialNetworks: FragmentSocialNetworks)

}
