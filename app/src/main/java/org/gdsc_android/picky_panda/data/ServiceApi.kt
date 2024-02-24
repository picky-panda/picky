package org.gdsc_android.picky_panda.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @Headers("Content-Type: application/json")
    @POST("/restaurant/save/:restaurantId") //가게 저장
    fun saveStore(@Body request: RequestSaveStoreData)
        : Call<RequestSaveStoreData>

    @POST("/restaurant") //가게 등록
    fun registerStore(
        @Body request: RequestRegisterStoreData
    ): Call<ResponseRegisterStoreData>

    @GET("/profile/restaurant?section=") //마이페이지 가게 목록
    fun mySectionStoreList(
        @Query ("section") section: String
    ):Call<ResponseMySectionStoreListData>

    @POST("/restaurant/description/{restaurantId}") //가게 설명 등록
    fun storeDescription(
        @Path("restaurantId") restaurantId: Int,
        @Body request: RequestRegisterDescriptionData
    ) : Call<ResponseRegisterDescriptionData>

    @DELETE("/review/:restaurantId/:reviewId") //리뷰 삭제
    fun reviewDelete(
        @Path ("restaurantId") restaurantId: Int,
        @Path ("reviewId") reviewId: Int
    ) : Call<ResponseReviewDeleteData>

    @GET("/profile") //마이페이지 조회
    fun myStoreList(
    ): Call<ResponseMyStoreListData>
}