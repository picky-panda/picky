package org.gdsc_android.picky_panda.data

data class ResponseInquireMapData(
    val code: Int,
    val message: String
){
    data class Data(
        val id: Int,
        val image: String,
        val placeName: String,
        val latitude: Double,
        val longitude: Double
    )
}
