package mobiledimension.exchangerates.data.network

import javax.inject.Inject

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiFixer @Inject
constructor() {

    @get:Provides
    internal val apiFixerHelper: ApiFixerHelper

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        apiFixerHelper = retrofit.create(ApiFixerHelper::class.java)
    }

}
