package mobiledimension.exchangerates.ui.mainMenu


import mobiledimension.exchangerates.ui.base.BaseView

interface MainView : BaseView {

    fun refreshSpinner()

    fun spinnerSetSelection(position: Int)

    fun refreshAdapterModelDate()

    fun showToast(message: String)
}
