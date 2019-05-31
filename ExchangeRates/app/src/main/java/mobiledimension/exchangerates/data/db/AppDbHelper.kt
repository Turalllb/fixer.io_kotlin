package mobiledimension.exchangerates.data.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

import javax.inject.Inject


import mobiledimension.exchangerates.MyApplication
import mobiledimension.exchangerates.presenter.MainMenu.MainMenuPresenter

import mobiledimension.exchangerates.data.db.ExchangeRatesTable.COLUMN.CURRENCY
import mobiledimension.exchangerates.data.db.ExchangeRatesTable.COLUMN.DATE
import mobiledimension.exchangerates.data.db.ExchangeRatesTable.COLUMN.JSON
import mobiledimension.exchangerates.data.db.ExchangeRatesTable.TABLE


/**
 * Created by Tural on 23.12.2017.
 */

class AppDbHelper @Inject
internal constructor(private val exchangeRatesDatabase: SQLiteDatabase) : DbHelper {

    override fun setDataBase(strDate: String, currency: String, json: String) {
        // создаем объект для данных
        val cv = ContentValues()
        try {
            val format = SimpleDateFormat()
            format.applyPattern("yyyy-MM-dd") //проверить не парсит ли формат в котором дата находится изначально
            val date = format.parse(strDate)
            val unixTimeDate = date.time
            Log.d(MainMenuPresenter.LOG_TAG, unixTimeDate.toString() + "")
            // подготовим данные для вставки в виде пар: наименование столбца -
            // значение
            with(cv){
                put(DATE, unixTimeDate)
                put(CURRENCY, currency)
                put(JSON, json)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        Log.d(MainMenuPresenter.LOG_TAG, "--- Insert in ExchangeRatesDatabase: ---")
        // вставляем запись и получаем ее ID
        val rowID = exchangeRatesDatabase.insert(TABLE, null, cv)
        Log.d(MainMenuPresenter.LOG_TAG, "row inserted, ID = $rowID")

        // делаем запрос всех данных из таблицы ExchangeRatesDatabase, получаем Cursor
        val c = exchangeRatesDatabase.query(TABLE, null, null, null, null, null, null)

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            val idDate = c.getColumnIndex(DATE)
            val currencyColIndex = c.getColumnIndex(CURRENCY)
            val jsonColIndex = c.getColumnIndex(JSON)

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(MainMenuPresenter.LOG_TAG,
                        "date = " + c.getLong(idDate) +
                                ", currency = " + c.getString(currencyColIndex) +
                                ", json = " + c.getString(jsonColIndex))
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
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
        } catch (e: ParseException) { //ловим не только ParseException, но и исключение, которое выбросится,
            e.printStackTrace()     // если в момент выполнения программы, удалить базу данных с
        }

        return postModel
    }

}
 