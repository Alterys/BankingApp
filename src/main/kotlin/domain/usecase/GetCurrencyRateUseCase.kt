package domain.usecase

import common.Resource
import domain.model.Currency
import domain.repository.ConvertorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCurrencyRateUseCase(
    private val repository: ConvertorRepository
) {
    operator fun invoke(): Flow<Resource<Currency>> = flow {
        try {
            emit(Resource.Loading)
            val response = repository.getCurrencyRate()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}