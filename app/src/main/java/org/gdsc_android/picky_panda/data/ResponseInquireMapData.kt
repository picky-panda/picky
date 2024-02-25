package org.gdsc_android.picky_panda.data

data class ResponseInquireMapData(
    val code: Int,
    val message: String,
    val data: List<Data>?
){
    data class Data(
        val id: Int,
        val placeName: String,
        val latitude: Double,
        val longitude: Double
    )
}
