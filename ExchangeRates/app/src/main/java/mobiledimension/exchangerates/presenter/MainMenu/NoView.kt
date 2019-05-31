package mobiledimension.exchangerates.presenter.MainMenu

import mobiledimension.exchangerates.ui.MainMenu.MainView

//Implementation of the pattern Null object
class NoView : MainView {
    init {
        try {
            throw NullPointerException("View is null")
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }


    override fun refreshSpinner() {}

    override fun spinnerSetSelection(position: Int) {}

    override fun refreshAdapterModelDate() {}

    override fun showToast(message: String) {}
}
