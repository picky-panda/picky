package org.gdsc_android.picky_panda.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    //@Headers("Content-Type: application/json")

    @POST("/auth/google/login") //로그인
    fun login(
        @Body request: RequestLoginData
    ) : Call<ResponseLoginData>

    @GET("/restaurant/:restaurantId") //가게 조회
    fun inquireStore(
        @Path ("restaurantId") restaurantId: Int
    ): Call<ResponseInquireStoreData>

    @POST("/restaurant/save/:restaurantId") //가게 저장
    fun saveStore(
        //@Headers("Authorization") token: String,
        @Path ("restaurantId") restaurantId: Int,
        @Body request: RequestSaveStoreData
    ) : Call<ResponseSaveStoreData>

    @POST("/restaurant/:descriptionId") //가게 description 동의
    fun describeStore(
        @Path ("restaurantId") restaurantId: Int,
        @Body request: RequestDescribeStoreData
    ): Call<ResponseDescribeStoreData>

    @GET("/restaurant/list") //가게 지도 조회
    fun inquireMap(
        @Query ("northEastX") northEastX: Double,
        @Query ("northEastY") northEastY: Double,
        @Query ("southWestX") southWestX: Double,
        @Query ("southWestY") southWestY: Double
    ): Call<ResponseInquireMapData>

    @POST("/review/:restaurantId") //리뷰 등록
    fun registerReview(
        @Path ("restaurantId") restaurantId: Int,
        @Body request: RequestRegisterReviewData
    ): Call<ResponseRegisterReviewDta>

    @GET("/review/:restaurantId") //리뷰 조회
    fun inquireReview(
        @Path ("restaurantId") restaurantId: Int
    ): Call<ResponseInquireReviewData>



}