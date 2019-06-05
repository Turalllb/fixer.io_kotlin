package mobiledimension.exchangerates.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import javax.inject.Inject

import mobiledimension.exchangerates.ui.mainMenu.MainMenuPresenter


/**
 * Created by Tural on 23.12.2017.
 */

class DbOpenHelper @Inject
constructor(context: Context, path: String) : SQLiteOpenHelper(context, path + "/"
        + "ExchangeRatesDatabase", null, 1) {

    override fun onCreate(ExchangeRatesDatabase: SQLiteDatabase) {
        Log.d(MainMenuPresenter.LOG_TAG, "--- onCreate database ---")
        // создаем таблицу с полями
        ExchangeRatesDatabase.execSQL(ExchangeRatesTable.CREATE_SCRIPT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}

