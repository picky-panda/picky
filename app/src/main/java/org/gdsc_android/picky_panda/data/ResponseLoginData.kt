package org.gdsc_android.picky_panda.data

data class ResponseLoginData(
    val code: Int,
    val message: String,
    val data: Data?
){
    data class Data(
        val socialId: String,
        val accessToken: String,
        val refreshToken: String
    )
}
