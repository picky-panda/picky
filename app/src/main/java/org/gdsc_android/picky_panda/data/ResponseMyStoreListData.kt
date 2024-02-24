package org.gdsc_android.picky_panda.data

data class ResponseMyStoreListData(
    val code: Int,
    val message: String,
){
    data class Data(
        val profileImage: String,
        val email: String,
        val myDescriptionCount: Int,
        val myReviewCount: Int,
        val mySavedRestaurantCount: Int
    )

    data class recentlyEvaluatedList(
        val id: Int,
        val restaurantImage: String,
        val placeName: String,
        val address: String,
        val options: String
    )

}
