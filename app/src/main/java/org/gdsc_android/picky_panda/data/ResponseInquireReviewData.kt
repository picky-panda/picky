package org.gdsc_android.picky_panda.data

data class ResponseInquireReviewData(
    val code: Int,
    val message: String
){
    data class Data(
        val userId: Int,
        val reviewList: List<reviewList>
    )

    data class reviewList(
        val id: Int,
        val writerId: Int,
        val content: String,
        val createdAt: String
    )
}
