package mobiledimension.exchangerates.presenter.base

import mobiledimension.exchangerates.presenter.MainMenu.NoView
import mobiledimension.exchangerates.ui.base.BaseView

open class BasePresenter<V : BaseView> : MvpPresenter<V> {

    protected var view: V = NoView() as V


    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = NoView() as V
    }

}

