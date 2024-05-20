package presentation.screens.transfers

import data.storage.balance.BalanceModel
import data.storage.event.UserEventModel
import data.storage.user.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

class MoneyTransfersViewModel {

    private val _screenState = MutableStateFlow(MoneyTransfersState())
    val screenState: StateFlow<MoneyTransfersState> = _screenState

    private fun getBalanceUser(userId: String): Long? {
        val user = UserModel.getUser(userId)
        return user?.balanceId ?: when {
            userId.isBlank() -> {
                _screenState.update { it.copy(error = "Введите ID пользователя!") }
                null
            }

            else -> {
                _screenState.update { it.copy(error = "Вы ввели неверный ID пользователя!") }
                null
            }
        }
    }

    private fun getBalance(balanceId: Long): BigDecimal? {
        return BalanceModel.getBalance(balanceId)?.rub
    }

    fun moneyTransfers(balanceId: Long, userId: String, sum: BigDecimal?) {
        sum?.let { transferAmount ->
            if (transferAmount == BigDecimal.ZERO) {
                _screenState.update {
                    it.copy(error = "Невозможно перевести 0 рублей!")
                }
                return
            }
            val balanceUser = getBalanceUser(userId) ?: return
            val balance = getBalance(balanceId) ?: return

            if (balance < transferAmount) {
                _screenState.update {
                    it.copy(error = "У вас недостаточно средств для перевода! Ваш счёт: $balance руб.")
                }
                UserEventModel.addEvent(balanceId, "Перевод $transferAmount рублей", false)
            } else if (balanceId == balanceUser) {
                _screenState.update {
                    it.copy(error = "Нельзя перевести самому себе деньги.")
                }
            } else {
                BalanceModel.withdrawBalance(transferAmount, balanceId, "RUB")
                BalanceModel.depositBalance(transferAmount, balanceUser, "RUB")
                UserEventModel.addEvent(balanceId, "Перевод $transferAmount рублей", true)
            }
        } ?: _screenState.update {
            it.copy(error = "Введите количество рублей, которые нужно перевести!")
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