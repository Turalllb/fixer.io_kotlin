package mobiledimension.exchangerates.ui.base

interface MvpPresenter<V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}
