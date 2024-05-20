package data.remote

import data.remote.response.CurrencyRate
import retrofit2.http.GET

interface ConvertorApi {
    @GET("latest.js")
    suspend fun getCurrencyRate(): CurrencyRate
}