package mobiledimension.exchangerates.ui.base

import mobiledimension.exchangerates.ui.mainMenu.NoView

open class BasePresenter<V : BaseView> : MvpPresenter<V> {

    protected var view: V = NoView() as V


    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = NoView() as V
    }

}

