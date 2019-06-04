package mobiledimension.exchangerates.data.db

interface DbHelper {

    fun setDataBase(strDate: String?, currency: String?, json: String)

    fun getStrPostModel(strDate: String, currency: String): String?
}
