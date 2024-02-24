package org.gdsc_android.picky_panda.data

data class RequestRegisterStoreData(
    val placeName: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    //val categort:ENUM,
    val options: String,
    val description:String
)
