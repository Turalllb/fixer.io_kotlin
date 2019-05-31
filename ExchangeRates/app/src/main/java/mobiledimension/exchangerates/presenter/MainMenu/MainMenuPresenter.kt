package mobiledimension.exchangerates.presenter.MainMenu

import java.util.ArrayList
import java.util.Arrays
import java.util.Collections

import javax.inject.Inject

import mobiledimension.exchangerates.R
import mobiledimension.exchangerates.data.DataManager
import mobiledimension.exchangerates.data.db.model.ModelData
import mobiledimension.exchangerates.data.db.model.PostModel
import mobiledimension.exchangerates.presenter.base.BasePresenter
import mobiledimension.exchangerates.ui.MainMenu.MainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import mobiledimension.exchangerates.data.db.model.ModelData.Companion.COMPARATOR_NAME
import mobiledimension.exchangerates.data.db.model.ModelData.Companion.COMPARATOR_VALUE_ASCENDING
import mobiledimension.exchangerates.data.db.model.ModelData.Companion.COMPARATOR_VALUE_DESCENDING


class MainMenuPresenter<V : MainView> @Inject
internal constructor(private val dataManager: DataManager) : BasePresenter<V>(), MainPresenter<V> {
    private val ACCESS_KEY = "0cd4416cd335bb08486b95e597b8c6b3" //Для доступа к апи сайта. Есть ограничения в бесплатной версии.
    override var currentCurrency = "EUR"
    override val rates: MutableList<ModelData> = mutableListOf() //список из моделей (валюта курс)
    private val currenciesArrayList = ArrayList(Arrays.asList("EUR")) //Список валют для спиннера
    private var checkedId: Int = 0
    private var currentDate: String = ""

    override val currencies: List<String>
        get() = currenciesArrayList



    override fun setCurrentDate(currentDate: String) {
        this.currentDate = currentDate
    }


    override fun currencyChanged(position: Int) {
        currentCurrency = currenciesArrayList[position]
        uploadData()
        view.refreshAdapterModelDate()
    }


    private fun uploadData() {
        rates.clear() //заранее очищаю список валют
        val postModel = dataManager.downloadFromDataBase(currentDate, currentCurrency)
        if (postModel != null) {
            setData(postModel)
        } else {
            dataManager.downloadDataFromNetwork(currentDate, ACCESS_KEY, currentCurrency, object : Callback<PostModel> {
                override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                    validationOfData(response.body()!!)
                }

                override fun onFailure(call: Call<PostModel>, t: Throwable) {
                    view.showToast("Сетевая ошибка")
                }
            })
        }
    }

    //Некоторые специфические для Fixer апи проверки
    private fun validationOfData(postModel: PostModel) {
        if (postModel.date == null) {
            view.refreshAdapterModelDate()
            view.showToast("В бесплатной версии доступны только курсы по отношению к EUR")
        } else if (postModel.date != currentDate) {
            view.refreshAdapterModelDate() //обновляю, чтобы показать List без результатов
            view.showToast("Курсы обновляются в рабочие дни после 16.00 по msk")
        } else {
            //Если всё в порядке
            setData(postModel)
            //Сохраняю в БД
            dataManager.setDataBase(postModel)
        }
    }

    private fun setData(postModel: PostModel) {
        rates.addAll(postModel.ratesList)
        currenciesArrayList.clear()
        currenciesArrayList.addAll(postModel.currenciesArrayList)
        currenciesArrayList.sort()
        Collections.sort(currenciesArrayList)
        view.refreshSpinner()
        /* Не всегда в спиннере после обновления будет стоять валюта по которой сделан запрос,
           так как список спиннера тоже всегда обновляется, поэтому вручную устанавливаю текущую валюту*/
        view.spinnerSetSelection(currenciesArrayList.indexOf(currentCurrency))
        sorting()
        view.refreshAdapterModelDate()
    }


    override fun onDatePicked(date: String) {
        currentDate = date
        uploadData()
    }


    override fun onChangedSortType(checkedId: Int) {
        this.checkedId = checkedId
        sorting()
        view.refreshAdapterModelDate()
    }

    private fun sorting() {
        when (checkedId) {
            R.id.radioButton1 -> Collections.sort(rates, COMPARATOR_NAME)
            R.id.radioButton2 -> Collections.sort(rates, COMPARATOR_VALUE_ASCENDING)
            R.id.radioButton3 -> Collections.sort(rates, COMPARATOR_VALUE_DESCENDING)
        }
    }

    companion object {
        val LOG_TAG = "myLogs"
    }
}
