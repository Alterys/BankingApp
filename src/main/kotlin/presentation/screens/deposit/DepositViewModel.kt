package presentation.screens.deposit

import data.storage.balance.BalanceModel
import data.storage.event.UserEventModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal


class DepositViewModel {

    private val _screenState = MutableStateFlow(DepositState())
    val screenState: StateFlow<DepositState> = _screenState

    fun depositBalance(sum: BigDecimal?, balanceId: Long) {
        sum ?: run {
            _screenState.update { it.copy(error = "Введите количество рублей,\n на которое производиться пополнение!") }
            return
        }
        if (sum != BigDecimal.ZERO) {
            BalanceModel.depositBalance(sum, balanceId, "RUB")
            UserEventModel.addEvent(balanceId, "Пополнение на $sum рублей", true)
        } else {
            val error = when {
                sum == BigDecimal.ZERO -> "Невозможно пополнить на 0 рублей!"
                else -> null
            }
            error?.let { _screenState.update { it.copy(error = error) } }
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