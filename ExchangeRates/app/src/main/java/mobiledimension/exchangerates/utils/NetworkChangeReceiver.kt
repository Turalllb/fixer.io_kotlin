package mobiledimension.exchangerates.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.TextView

import mobiledimension.exchangerates.R

class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val rootView = (context as Activity).window.decorView.findViewById<View>(android.R.id.content)
        val textView = rootView.findViewById<View>(R.id.networkStateTextView) as TextView

        if (checkConnect(context)) {
            textView.text = ""
        } else {
            textView.text = "Нет подключения. Будут получены только сохраненные в БД результаты"
        }
    }

    private fun checkConnect(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo?
        networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}
