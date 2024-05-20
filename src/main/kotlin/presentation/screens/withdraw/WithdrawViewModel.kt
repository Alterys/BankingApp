package presentation.screens.withdraw

import data.storage.balance.BalanceModel
import data.storage.event.UserEventModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

class WithdrawViewModel {

    private val _screenState = MutableStateFlow(WithdrawState())
    val screenState: StateFlow<WithdrawState> = _screenState

    private fun getBalance(balanceId: Long): BigDecimal? {
        return BalanceModel.getBalance(balanceId)?.rub
    }

    fun withdrawBalance(balanceId: Long, sum: BigDecimal?) {

        sum ?: run {
            _screenState.update { it.copy(error = "Введите количество рублей, которые нужно снять!") }
            return
        }
        val balance = getBalance(balanceId)
        if (balance != null && sum != BigDecimal.ZERO && balance >= sum) {
            BalanceModel.withdrawBalance(sum, balanceId, "RUB")
            UserEventModel.addEvent(balanceId, "Снятие наличных: $sum рублей", true)

        } else if (balance != null && balance < sum) {
            val error = "У вас недостаточно средств! Ваш счёт: $balance руб."
            _screenState.update { it.copy(error = error) }
            UserEventModel.addEvent(balanceId, "Снятие наличных: $sum рублей", false)
        } else {
            val error = when {
                sum == BigDecimal.ZERO -> "Невозможно снять 0 рублей!"
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
