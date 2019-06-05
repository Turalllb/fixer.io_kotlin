package mobiledimension.exchangerates.data.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log

import java.text.ParseException
import java.text.SimpleDateFormat

import javax.inject.Inject


import mobiledimension.exchangerates.ui.mainMenu.MainMenuPresenter

import mobiledimension.exchangerates.data.db.ExchangeRatesTable.COLUMN.CURRENCY
import mobiledimension.exchangerates.data.db.ExchangeRatesTable.COLUMN.DATE
import mobiledimension.exchangerates.data.db.ExchangeRatesTable.COLUMN.JSON
import mobiledimension.exchangerates.data.db.ExchangeRatesTable.TABLE


/**
 * Created by Tural on 23.12.2017.
 */

class AppDbHelper @Inject
internal constructor(private val exchangeRatesDatabase: SQLiteDatabase) : DbHelper {

    override fun setDataBase(strDate: String?, currency: String?, json: String) {
        val cv = ContentValues()
        try {
            val format = SimpleDateFormat()
            format.applyPattern("yyyy-MM-dd")
            val date = format.parse(strDate)
            val unixTimeDate = date.time

            with(cv){
                put(DATE, unixTimeDate)
                put(CURRENCY, currency)
                put(JSON, json)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val rowID = exchangeRatesDatabase.insert(TABLE, null, cv)
        Log.d(MainMenuPresenter.LOG_TAG, "row inserted, ID = $rowID")

        val c = exchangeRatesDatabase.query(TABLE, null, null, null, null, null, null)

        if (c.moveToFirst()) {
            val idDate = c.getColumnIndex(DATE)
            val currencyColIndex = c.getColumnIndex(CURRENCY)
            val jsonColIndex = c.getColumnIndex(JSON)

            do {
                Log.d(MainMenuPresenter.LOG_TAG,
                        "date = " + c.getLong(idDate) +
                                ", currency = " + c.getString(currencyColIndex) +
                                ", json = " + c.getString(jsonColIndex))
            } while (c.moveToNext())
        } else
            Log.d(MainMenuPresenter.LOG_TAG, "0 rows")
        c.close()
    }


    override fun getStrPostModel(strDate: String, currency: String): String? {
        val unixTimeDate: Long
        var postModel: String? = null

        try {
            val format = SimpleDateFormat()
            format.applyPattern("yyyy-MM-dd")
            val date = format.parse(strDate)
            unixTimeDate = date.time

            val c = exchangeRatesDatabase.query(TABLE,
                    arrayOf(JSON),
                    "date = ? AND currency = ?",
                    arrayOf(java.lang.Long.toString(unixTimeDate), currency), null, null, null)

            postModel = if (c != null && c.moveToFirst()) c.getString(c.getColumnIndex(JSON)) else null
            c?.close()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return postModel
    }

}
 