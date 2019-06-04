package mobiledimension.exchangerates.di.module

import android.content.Context
import android.os.Environment

import dagger.Module
import dagger.Provides

@Module
class PathModule {

    @Provides
    internal fun getPath(context: Context): String {
        //Проверяем наличие внешней памяти и возращаем путь для создания БД
        return when (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            true -> try {
                context.getExternalFilesDir(null)!!.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                context.filesDir.absolutePath
            }
            false -> context.filesDir.absolutePath
        }
    }
}