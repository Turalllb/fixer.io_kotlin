package mobiledimension.exchangerates.presenter.SocialNetwork

import android.graphics.Bitmap

import com.github.gorbin.asne.core.SocialNetworkManager

import mobiledimension.exchangerates.presenter.base.MvpPresenter
import mobiledimension.exchangerates.ui.SocialNetwork.SocialNetworkView

interface SocialNetworkPresenter<V : SocialNetworkView> : MvpPresenter<V> {

    val vkKey: String

    val vkScope: Array<String>

    fun vkShare(screenshot: Bitmap, socialNetworkManager: SocialNetworkManager)


}
