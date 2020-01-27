package sample.my.force_version_up.sample

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


object ApiManager {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)
}

interface ApiService {
    @GET("users/{user}/repos")
    fun getVersionJson(): Call<VersionModel>
}

data class VersionModel(
    val version: String
)