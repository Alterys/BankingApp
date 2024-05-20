package presentation.screens.menu

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.components.DialogComplete
import presentation.components.DialogError
import presentation.navigation.*
import presentation.components.ProfileBar
import presentation.screens.menu.components.OtherBlock
import presentation.screens.menu.components.WithdrawBlock
import java.math.BigDecimal

@Composable
fun MenuScreen(
    navigate: (Map<String, Any>, Screen) -> Unit,
    withdrawBalance: (BigDecimal, Long) -> Unit,
    getUserEvents: (Long) -> Unit,
    screenState: MenuState,
    onDialog: () -> Unit,
    closedDialog: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LocalNavController.current.getCurrentArgs()?.let {
            ProfileBar(
                id = it["login"].toString(),
                onClick = { navigate(it, Screen.BalanceScreen) },
                onBalance = true,
                text = null,
                nameScreen = null
            )
            getUserEvents(it["id"].toString().toLong())
            Row(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WithdrawBlock(
                    listButton = listOf("500", "1000", "5000"),
                    onClickWithdraw = { withdraw ->
                        withdrawBalance(
                            BigDecimal.valueOf(withdraw.toLong()),
                            it["balanceId"].toString().toLong()
                        )
                        getUserEvents(it["id"].toString().toLong())
                        onDialog()
                    },
                    onClickWithdrawScreen = { navigate(it, Screen.WithdrawScreen) }
                )
                OtherBlock(
                    onClickTransfers = { navigate(it, Screen.MoneyTransfers) },
                    onClickDeposit =  { navigate(it, Screen.DepositScreen) },
                    onClickEvent = { navigate(it, Screen.EventScreen) },
                    textDeposit = "Пополнение",
                    textTransfers = "Переводы",
                    textEvent = "История",
                    event =  screenState.eventList?.isNotEmpty() ?: false
                )
            }
        }
    }
    if (screenState.onDialog) {
        if (screenState.error != null) {
            DialogError(
                error = screenState.error!!,
                onClick = { closedDialog() }
            )
        } else {
            DialogComplete(
                onClick = { closedDialog() }
            )
        }
    }
}



