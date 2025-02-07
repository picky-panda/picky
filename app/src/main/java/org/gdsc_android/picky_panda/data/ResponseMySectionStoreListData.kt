package org.gdsc_android.picky_panda.data

data class ResponseMySectionStoreListData(
    val code: Int,
    val message: String,
    val data: Data?
){
    data class Data(
        val id: Int,
        val placeName: String,
        val address: String,
        val options: String
    )
}
