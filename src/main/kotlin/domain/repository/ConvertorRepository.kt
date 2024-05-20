package domain.repository

import domain.model.Currency

interface ConvertorRepository {
    suspend fun getCurrencyRate(): Currency
}