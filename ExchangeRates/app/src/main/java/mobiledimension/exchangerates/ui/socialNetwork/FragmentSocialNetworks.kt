package mobiledimension.exchangerates.ui.socialNetwork

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.github.gorbin.asne.core.SocialNetworkManager
import com.github.gorbin.asne.vk.VkSocialNetwork
import mobiledimension.exchangerates.MyApplication.Companion.appComponent
import mobiledimension.exchangerates.R
import mobiledimension.exchangerates.di.DaggerFragmentComponent
import javax.inject.Inject


/**
 * Created by Турал on 11.12.2017.
 */

class FragmentSocialNetworks : Fragment(), SocialNetworkView, SocialNetworkManager.OnInitializationCompleteListener {

    @Inject
    internal lateinit var socialNetworkPresenter: SocialNetworkPresenter<SocialNetworkView>
    private lateinit var socialNetworkManager: SocialNetworkManager
    private val socialNetworkTag = "SocialIntegrationMain.SOCIAL_NETWORK_TAG"
    private val screenshot: Bitmap
        get() {
            val rootView = activity!!.findViewById<View>(android.R.id.content).rootView
            rootView.isDrawingCacheEnabled = true
            return rootView.drawingCache
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment, null)

        val fragmentComponent = DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .build()
        fragmentComponent.inject(this)


        val socialFragment = fragmentManager?.findFragmentByTag(socialNetworkTag)
        if (socialFragment != null) {
            socialNetworkManager = socialFragment as SocialNetworkManager
            //if manager exist - get and setup login only for initialized SocialNetworks
            if (socialNetworkManager.initializedSocialNetworks.isNotEmpty()) {
                val socialNetworks = socialNetworkManager.initializedSocialNetworks
                for (socialNetwork in socialNetworks) {
                    //socialNetwork.setOnLoginCompleteListener(this);
                    initSocialNetwork(socialNetwork)
                }
            }
        } else {
            socialNetworkManager = SocialNetworkManager()

            //Init and add to manager VkSocialNetwork
            val vkNetwork = VkSocialNetwork(this, socialNetworkPresenter.vkKey, socialNetworkPresenter.vkScope)
            socialNetworkManager.addSocialNetwork(vkNetwork)

            //Initiate every network from socialNetworkManager
            fragmentManager!!.beginTransaction().add(socialNetworkManager, socialNetworkTag).detach(socialNetworkManager).commit()

            socialNetworkManager.setOnInitializationCompleteListener(this)
        }


        val vkShare = v.findViewById<View>(R.id.VK_share) as Button
        vkShare.setOnClickListener {
            socialNetworkPresenter.vkShare(screenshot, socialNetworkManager)
        }

        return v
    }

    override fun onResume() {
        super.onResume()
        socialNetworkPresenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        socialNetworkPresenter.detachView()
    }


    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (socialNetwork in socialNetworkManager.initializedSocialNetworks) {
            initSocialNetwork(socialNetwork)
        }
    }

    private fun initSocialNetwork(socialNetwork: com.github.gorbin.asne.core.SocialNetwork) {
        if (socialNetwork.isConnected) {
            when (socialNetwork.id) {
                VkSocialNetwork.ID -> {
                }
            }
        }
    }


}


