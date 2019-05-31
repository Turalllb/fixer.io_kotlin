package mobiledimension.exchangerates

import android.app.Application

import mobiledimension.exchangerates.di.AppComponent
import mobiledimension.exchangerates.di.DaggerAppComponent
import mobiledimension.exchangerates.di.module.AppModule


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }


    companion object {
        var appComponent: AppComponent? = null
            private set
    }
}
