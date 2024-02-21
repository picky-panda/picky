package org.gdsc_android.picky_panda.data

data class ResponseSaveStoreData(
    val code: Int,
    val message: String
){
    data class Data(
        val isSaved: Boolean,
        val image: String,
        val placeName: String,
        val latitude: Double,
        val longitude: Double,
        val descriptions: Object
    )
}
