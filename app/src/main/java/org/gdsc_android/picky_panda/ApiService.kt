package org.gdsc_android.picky_panda

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.Currency

interface ApiService {

    @GET()
    fun getCoinTicker(
        @Path("order_currency") orderCurrency: String,

    ): Call<Ticker>
}