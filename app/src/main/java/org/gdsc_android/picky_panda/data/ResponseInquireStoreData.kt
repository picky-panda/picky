package org.gdsc_android.picky_panda.data

data class ResponseInquireStoreData(
    val code: Int,
    val message: String
){
    data class Data(
        val isSaved: Boolean,
        val image: String,
        val placeName: String,
        val latitude: Double,
        val longitude: Double,
        val descriptions: List<Description>
    )

    data class Description(
        val description: String,
        val isAgreed: Int,
        val isDisagreed: Int
    )
}
