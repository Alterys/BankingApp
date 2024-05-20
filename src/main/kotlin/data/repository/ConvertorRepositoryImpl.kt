package data.repository

import common.Constants
import data.remote.ConvertorApi
import domain.model.Currency
import domain.repository.ConvertorRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConvertorRepositoryImpl : ConvertorRepository {

    private val api: ConvertorApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ConvertorApi::class.java)

    override suspend fun getCurrencyRate(): Currency {
        val result = api.getCurrencyRate().rates
        return Currency(
            cad = result.cad.toBigDecimal(),
            usd = result.usd.toBigDecimal(),
            rub = result.rub.toBigDecimal(),
            eur = result.eur.toBigDecimal(),
            chf = result.chf.toBigDecimal()
        )
    }


}