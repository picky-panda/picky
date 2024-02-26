package org.gdsc_android.picky_panda.data

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.DELETE
import retrofit2.http.Header

interface ServiceApi {

    //@Headers("Content-Type: application/json")

    @POST("/auth/google/login") //로그인
    fun login(
        @Body request: RequestLoginData
    ) : Call<ResponseLoginData>

    @GET("/restaurant/:restaurantId") //가게 조회
    fun inquireStore(
        @Headers("Authorization") authorization: String,
        @Path ("restaurantId") restaurantId: Int
    ): Call<ResponseInquireStoreData>

    @POST("/restaurant/save/:restaurantId") //가게 저장
    fun saveStore(
        @Headers("Authorization") authorization: String,
        @Path ("restaurantId") restaurantId: Int,
        @Body request: RequestSaveStoreData
    ) : Call<ResponseSaveStoreData>

    @POST("/restaurant/:descriptionId") //가게 description 동의
    fun describeStore(
        @Headers("Authorization") authorization: String,
        @Path ("restaurantId") restaurantId: Int,
        @Body request: RequestDescribeStoreData
    ): Call<ResponseDescribeStoreData>

    @GET("/restaurant/list") //가게 지도 조회
    fun inquireMap(
        @Headers("Authorization") authorization: String,
        @Query ("northEastX") northEastX: Double,
        @Query ("northEastY") northEastY: Double,
        @Query ("southWestX") southWestX: Double,
        @Query ("southWestY") southWestY: Double
    ): Call<ResponseInquireMapData>

    @POST("/review/:restaurantId") //리뷰 등록
    fun registerReview(
        @Headers("Authorization") authorization: String,
        @Path ("restaurantId") restaurantId: Int,
        @Body request: RequestRegisterReviewData
    ): Call<ResponseRegisterReviewDta>

    @GET("/review/:restaurantId") //리뷰 조회
    fun inquireReview(
        @Headers("Authorization") authorization: String,
        @Path ("restaurantId") restaurantId: Int
    ): Call<ResponseInquireReviewData>

    @POST("/restaurant") //가게 등록
    suspend fun registerStore(
        @Body request: RequestRegisterStoreData,
        @Header("Authorization") authorization: String
    ): Call<ResponseRegisterStoreData>

    @GET("/profile/restaurant?section=") //마이페이지 가게 목록
    fun mySectionStoreList(
        @Query ("section") section: String
    ):Call<ResponseMySectionStoreListData>

    @POST("/restaurant/description/:restaurantId") //가게 설명 등록
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
        @Header("Authorization") authorization: String
    ): Call<ResponseMyStoreListData>

    @GET("maps/api/geocode/json")
    fun getGeocodeAsync(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Deferred<GeocodingResponse>
}