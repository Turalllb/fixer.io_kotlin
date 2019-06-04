package mobiledimension.exchangerates.presenter.socialNetwork

import android.graphics.Bitmap
import android.os.Bundle

import com.github.gorbin.asne.core.SocialNetworkManager
import com.github.gorbin.asne.core.listener.OnPostingCompleteListener
import com.github.gorbin.asne.vk.VkSocialNetwork
import com.vk.sdk.VKScope

import javax.inject.Inject

import mobiledimension.exchangerates.presenter.base.BasePresenter
import mobiledimension.exchangerates.ui.socialNetwork.SocialNetworkView


class SocialNetwork<V : SocialNetworkView> @Inject
constructor() : BasePresenter<V>(), SocialNetworkPresenter<V> {

    override val vkKey = "6040457"
    override val vkScope = arrayOf(VKScope.FRIENDS, VKScope.WALL, VKScope.PHOTOS, VKScope.NOHTTPS, VKScope.STATUS)
    private val postingComplete = object : OnPostingCompleteListener {
        override fun onPostSuccessfully(socialNetworkID: Int) {
            try {
                view.showToast("Опубликовано")
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }

        }

        override fun onError(socialNetworkID: Int, requestID: String, errorMessage: String, data: Any) {
            view.showToast("Error while sending: $errorMessage")
        }
    }

    override fun vkShare(screenshot: Bitmap, socialNetworkManager: SocialNetworkManager) {
        var networkId = 0 //на случай если будут кнопки от других соц сетей
        networkId = VkSocialNetwork.ID
        val socialNetwork = socialNetworkManager.getSocialNetwork(networkId)
        if (!socialNetwork.isConnected) {
            if (networkId != 0) {
                socialNetwork.requestLogin()
                view.showToast("После авторизации нажмите повторно на публикацию")
            } else {
                view.showToast("Wrong networkId")
            }
        } else {
            val postParams = Bundle()
            postParams.putString(com.github.gorbin.asne.core.SocialNetwork.BUNDLE_LINK, "https://fixer.io")

            socialNetwork.requestPostPhotoMessageLink(screenshot, postParams, "ExchangeRates", postingComplete)
            view.showToast("Отправка публикации")
        }
    }
}
