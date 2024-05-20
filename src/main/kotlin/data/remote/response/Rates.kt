package data.remote.response

import com.google.gson.annotations.SerializedName

data class Rates(
    @SerializedName("CAD") val cad: Double,
    @SerializedName("USD") val usd: Double,
    @SerializedName("RUB") val rub: Double,
    @SerializedName("EUR") val eur: Double,
    @SerializedName("CHF") val chf: Double
)
