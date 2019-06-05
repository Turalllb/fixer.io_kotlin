package mobiledimension.exchangerates.ui.mainMenu

import mobiledimension.exchangerates.ui.mainMenu.MainView

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
