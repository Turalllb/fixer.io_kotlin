package mobiledimension.exchangerates.data

import com.google.gson.Gson

import javax.inject.Inject

import mobiledimension.exchangerates.data.db.DbHelper
import mobiledimension.exchangerates.data.model.PostModel
import mobiledimension.exchangerates.data.network.ApiFixerHelper
import retrofit2.Callback

class DataManager @Inject
internal constructor(private val dbHelper: DbHelper, private val apiFixerHelper: ApiFixerHelper) {

    fun downloadDataFromNetwork(currentDate: String, ACCESS_KEY: String, currentCurrency: String, callback: Callback<PostModel>) {
        apiFixerHelper.getData(currentDate, ACCESS_KEY, currentCurrency).enqueue(callback)
    }


    fun downloadFromDataBase(currentDate: String, currentCurrency: String): PostModel? {
        val strPostModel = dbHelper.getStrPostModel(currentDate, currentCurrency)
        return if (strPostModel != null) {
            //Если в БД уже есть результат
            Gson().fromJson(strPostModel, PostModel::class.java)
        } else null
    }

    fun setDataBase(postModel: PostModel) {
        dbHelper.setDataBase(postModel.date, postModel.base, postModel.toString())
    }


}
