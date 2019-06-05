package mobiledimension.exchangerates.ui.socialNetwork

import android.graphics.Bitmap
import com.github.gorbin.asne.core.SocialNetworkManager
import mobiledimension.exchangerates.ui.base.MvpPresenter

interface SocialNetworkPresenter<V : SocialNetworkView> : MvpPresenter<V> {

    val vkKey: String

    val vkScope: Array<String>

    fun vkShare(screenshot: Bitmap, socialNetworkManager: SocialNetworkManager)


}
