package presentation.screens.conversion

import common.Resource

import data.repository.ConvertorRepositoryImpl
import data.storage.balance.BalanceModel
import data.storage.event.UserEventModel
import domain.usecase.GetCurrencyRateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class ConversionViewModel(
    private val getCurrencyRateUseCase: GetCurrencyRateUseCase
) {

    private val _screenState = MutableStateFlow(ConversionState())
    val screenState: StateFlow<ConversionState> = _screenState

    fun getCurrencyRate(coroutineScope: CoroutineScope) {
        getCurrencyRateUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _screenState.update {
                        it.copy(
                            currencyRate = result.data,
                            isLoading = false
                        )
                    }
                }
                is Resource.Loading -> {
                    _screenState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.Error -> {
                    _screenState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(coroutineScope)
    }

    private fun getBalance(balanceId: Long): BigDecimal? {
        return BalanceModel.getBalance(balanceId)?.rub
    }

    fun withdrawBalance(balanceId: Long, sum: BigDecimal?, currency: String, moneyRate: BigDecimal?) {
        val balance = getBalance(balanceId)
        if (moneyRate != null && balance != null) {
            if (sum == null) {
                _screenState.update {
                    it.copy(
                        error = "Введите сумму денег, которую нужно конвертировать!"
                    )
                }
            } else if (sum == BigDecimal.ZERO) {
                _screenState.update {
                    it.copy(
                        error = "Невозможно конвертировать 0 рублей!"
                    )
                }
            } else if (balance >= sum) {
                val sumResult = sum * moneyRate
                if (sumResult < BigDecimal("0.01")) {
                    _screenState.update {
                        it.copy(
                            error = "Перевод не возможен так как слишком маленькое значение!"
                        )
                    }
                } else {
                    BalanceModel.withdrawBalance(sum, balanceId, "RUB")
                    BalanceModel.depositBalance(sumResult, balanceId, currency)
                    UserEventModel.addEvent(balanceId, "Конвертация средств: $sum рублей в $sumResult $currency", true)
                }
            } else {
                _screenState.update {
                    it.copy(
                        error = "У вас недостаточно средств! Ваш счёт: $balance руб."
                    )
                }
            }
        }
    }

    fun convertRubToCurrency(rub: String, currencyValue: BigDecimal?): BigDecimal? {
        return if (rub.isNotBlank() && currencyValue != null) {
            rub.toBigDecimalOrNull()?.multiply(currencyValue)?.setScale(2, RoundingMode.FLOOR)?.let {
                if (it.compareTo(BigDecimal.ZERO) == 0) BigDecimal.ZERO else it.stripTrailingZeros()
            }
        } else {
            null
        }
    }

    fun convertCurrencyToRub(currency: String, currencyValue: BigDecimal?): BigDecimal? {
        return if (currency.isNotBlank() && currencyValue != null) {
            currency.toBigDecimalOrNull()?.divide(currencyValue, 2, RoundingMode.FLOOR)?.let {
                if (it.compareTo(BigDecimal.ZERO) == 0) BigDecimal.ZERO else it.stripTrailingZeros()
            }
        } else {
            null
        }
    }


    fun onDialog() {
        _screenState.update {
            it.copy(
                onDialog = true
            )
        }
    }

    fun closedDialog() {
        _screenState.update {
            it.copy(
                onDialog = false,
                error = null
            )
        }
    }
}