package mobiledimension.exchangerates.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mobiledimension.exchangerates.presenter.mainMenu.MainMenuPresenter;
import mobiledimension.exchangerates.presenter.mainMenu.MainPresenter;
import mobiledimension.exchangerates.ui.mainMenu.MainView;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    MainPresenter<MainView> provideMainPresenter(MainMenuPresenter<MainView> presenter) {
        return presenter;
    }



}
