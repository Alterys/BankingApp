package data.remote.response

import com.google.gson.annotations.SerializedName

data class CurrencyRate(
    @SerializedName("rates") val rates: Rates
)
