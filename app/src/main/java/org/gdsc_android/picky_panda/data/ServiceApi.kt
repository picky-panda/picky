package org.gdsc_android.picky_panda.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ServiceApi {

    @Headers("Content-Type: application/json")
    @POST("/restaurant/save/:restaurantId") //가게 저장
    fun saveStore(@Body request: RequestSaveStoreData)
        : Call<RequestSaveStoreData>



}