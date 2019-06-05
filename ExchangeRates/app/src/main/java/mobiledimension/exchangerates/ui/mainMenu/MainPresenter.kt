package mobiledimension.exchangerates.ui.mainMenu

import mobiledimension.exchangerates.data.model.ModelData
import mobiledimension.exchangerates.ui.base.MvpPresenter


interface MainPresenter<V : MainView> : MvpPresenter<V> {

    val currencies: List<String>

    val rates: MutableList<ModelData>

    val currentCurrency: String

    fun onChangedSortType(checkedId: Int)

    fun currencyChanged(position: Int)

    fun onDatePicked(formattedDate: String)

    fun setCurrentDate(currentDate: String)

}
