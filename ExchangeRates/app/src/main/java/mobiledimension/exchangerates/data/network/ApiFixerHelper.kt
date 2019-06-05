package mobiledimension.exchangerates.data.network


import mobiledimension.exchangerates.data.model.PostModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiFixerHelper {

    @GET("{date}")
    fun getData(@Path("date") date: String, @Query("access_key") accessKey: String, @Query("base") base: String): Call<PostModel>
}
