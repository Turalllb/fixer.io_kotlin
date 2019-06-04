package mobiledimension.exchangerates.presenter.mainMenu

import mobiledimension.exchangerates.data.db.model.ModelData
import mobiledimension.exchangerates.presenter.base.MvpPresenter
import mobiledimension.exchangerates.ui.mainMenu.MainView


interface MainPresenter<V : MainView> : MvpPresenter<V> {

    val currencies: List<String>

    val rates: MutableList<ModelData>

    val currentCurrency: String

    fun onChangedSortType(checkedId: Int)

    fun currencyChanged(position: Int)

    fun onDatePicked(formattedDate: String)

    fun setCurrentDate(currentDate: String)

}