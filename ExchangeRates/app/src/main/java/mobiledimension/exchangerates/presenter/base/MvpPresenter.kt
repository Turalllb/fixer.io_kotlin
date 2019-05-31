package mobiledimension.exchangerates.presenter.base

import mobiledimension.exchangerates.ui.base.BaseView

interface MvpPresenter<V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}
