package presentation.screens.menu

import data.storage.balance.BalanceModel
import data.storage.event.UserEventModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import presentation.navigation.Destination
import presentation.navigation.NavController
import presentation.navigation.Screen
import presentation.screens.event.Event
import java.math.BigDecimal

class MenuViewModel(
    private val navController: NavController
) {

    private val _screenState = MutableStateFlow(MenuState())
    val screenState: StateFlow<MenuState> = _screenState


    fun getUserEvents(idUser: Long) {
        val events = UserEventModel.getUserEvents(idUser).map {
            Event(
                id = it.id,
                eventDate = it.eventDate,
                eventType = it.eventType,
                eventState = it.eventState
            )
        }
        _screenState.update {
            it.copy(
                eventList = events
            )
        }
    }

    fun navigation(mapArgs: Map<String, Any>, screen: Screen) {
        val login = mapArgs["login"]
        val balanceId = mapArgs["balanceId"]
        val id = mapArgs["id"]
        if (login != null && balanceId != null && id != null) {
            navController.navigate(
                Destination(
                    screen,
                    mapOf("login" to login, "balanceId" to balanceId, "id" to id)
                )
            )
        }
    }
    private fun getBalance(balanceId: Long): BigDecimal? {
        return BalanceModel.getBalance(balanceId)?.rub
    }

    fun withdrawBalance(sum: BigDecimal, balanceId: Long) {
        val balance = getBalance(balanceId)
        if (balance != null) {
            if (balance >= sum) {
                BalanceModel.withdrawBalance(sum, balanceId, "RUB")
                UserEventModel.addEvent(balanceId, "Снятие наличных: $sum рублей", true)
            } else {
                _screenState.update {
                    it.copy(
                        error = "У вас недостаточно средств!\n Ваш счёт: $balance руб."
                    )
                }
                UserEventModel.addEvent(balanceId, "Снятие наличных: $sum рублей", false)
            }
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
                onDialog = false
            )
        }
    }
}