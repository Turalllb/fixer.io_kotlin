package mobiledimension.exchangerates.di.module

import android.database.sqlite.SQLiteDatabase

import dagger.Module
import dagger.Provides
import mobiledimension.exchangerates.data.db.AppDbHelper
import mobiledimension.exchangerates.data.db.DbHelper
import mobiledimension.exchangerates.data.db.DbOpenHelper

@Module
class DbModule {

    @Provides
    internal fun provideSQLiteDatabase(dbOpenHelper: DbOpenHelper): SQLiteDatabase {
        return dbOpenHelper.writableDatabase
    }

    @Provides
    internal fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper {
        return appDbHelper
    }

}
