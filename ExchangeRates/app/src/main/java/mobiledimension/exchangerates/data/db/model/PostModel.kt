package mobiledimension.exchangerates.data.db.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


/*Этот подход использовать "ручной парсинг" и POJO класс сделан ради эксперимента.
 Можно было для json объекта rates использовать HashMap, в который Gson может парсить из "коробки", но тогда бы пришлось изобретать сортировки для HashMap. */
class  PostModel {

    @SerializedName("base")
    @Expose
    val base: String? = null

    @SerializedName("date")
    @Expose
    val date: String? = null

    @SerializedName("rates")
    @Expose
    private val ratesJson: JsonObject? = null

    private var _ratesList: MutableList<ModelData>? = null
    internal val ratesList: MutableList<ModelData>
        get() {
            if(_ratesList == null){
                parseToModelDate()
            }
            return _ratesList  ?: throw AssertionError("Set to null by another thread")
        }


    private var _currenciesArrayList: ArrayList<String>? = null
    internal val currenciesArrayList: ArrayList<String>
        get() {
            if (_currenciesArrayList == null) {
                parseToModelDate()
            }
            return _currenciesArrayList ?: throw AssertionError("Set to null by another thread")
        }


    private fun parseToModelDate() {
        _currenciesArrayList = arrayListOf()
        _ratesList = mutableListOf()


        for ((key, value) in ratesJson!!.entrySet()) {
            val doubleValue = value.asDouble
            ratesList.add(ModelData(key, doubleValue))
            currenciesArrayList.add(key)
        }
    }


    override fun toString(): String {
        return Gson().toJson(this)
    }
}