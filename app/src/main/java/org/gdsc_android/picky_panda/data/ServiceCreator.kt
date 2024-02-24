package org.gdsc_android.picky_panda.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ServiceCreator {

    private const val Base_URL = "http://34.64.159.113:8081"

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Base_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ServiceApi = retrofit.create(ServiceApi::class.java)

}