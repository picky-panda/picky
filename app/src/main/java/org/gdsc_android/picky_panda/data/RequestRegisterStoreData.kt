package org.gdsc_android.picky_panda.data

data class RequestRegisterStoreData(
    val placeName: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val category:CategoryClass,
    val options: String,
    val description:String
)
