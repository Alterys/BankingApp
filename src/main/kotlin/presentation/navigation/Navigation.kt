package presentation.navigation

import androidx.compose.runtime.*
import data.repository.ConvertorRepositoryImpl
import domain.usecase.GetCurrencyRateUseCase
import presentation.screens.authorization.AuthScreen
import presentation.screens.authorization.AuthViewModel
import presentation.screens.balance.BalanceViewModel
import presentation.screens.balance.BalanceScreen
import presentation.screens.conversion.ConversionScreen
import presentation.screens.conversion.ConversionViewModel
import presentation.screens.deposit.DepositScreen
import presentation.screens.deposit.DepositViewModel
import presentation.screens.event.EventScreen
import presentation.screens.event.EventViewModel
import presentation.screens.menu.MenuScreen
import presentation.screens.menu.MenuViewModel
import presentation.screens.transfers.MoneyTransfers
import presentation.screens.transfers.MoneyTransfersViewModel
import presentation.screens.withdraw.WithdrawScreen
import presentation.screens.withdraw.WithdrawViewModel

import kotlin.system.exitProcess

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}

@Composable
fun Navigation(navController: NavController) {
    val currentScreen by navController.currentScreen.collectAsState()

    CompositionLocalProvider(LocalNavController provides navController) {
        when (currentScreen) {
            Screen.AuthScreen -> {
                val authViewModel = remember {
                    AuthViewModel(navController)
                }
                AuthScreen(
                    login = authViewModel::login,
                    screenState = authViewModel.screenState.collectAsState().value
                )
            }

            is Screen.MenuScreen -> {
                val menuViewModel = remember { MenuViewModel(navController) }
                MenuScreen(
                    navigate = menuViewModel::navigation,
                    withdrawBalance = menuViewModel::withdrawBalance,
                    screenState = menuViewModel.screenState.collectAsState().value,
                    getUserEvents = menuViewModel::getUserEvents,
                    onDialog = menuViewModel::onDialog,
                    closedDialog = menuViewModel::closedDialog
                )
            }

            is Screen.BalanceScreen -> {
                val balanceViewModel = remember { BalanceViewModel(navController) }
                BalanceScreen(
                    screenState = balanceViewModel.screenState.collectAsState().value,
                    onDialog = balanceViewModel::onDialog,
                    closedDialog = balanceViewModel::closedDialog,
                    getBalance = balanceViewModel::getBalance,
                    navigate = balanceViewModel::navigate,
                    addCurrency = balanceViewModel::addCurrency,
                    chooseDollarForm = balanceViewModel::chooseDollarForm,
                    chooseRubleForm = balanceViewModel::chooseRubleForm,
                    chooseCanadianDollarForm = balanceViewModel::chooseCanadianDollarForm,
                    formatBigDecimal = balanceViewModel::formatBigDecimal,
                    chooseFrancForm = balanceViewModel::chooseFrancForm
                )
            }

            is Screen.DepositScreen -> {
                val depositViewModel = remember { DepositViewModel() }
                DepositScreen(
                    screenState = depositViewModel.screenState.collectAsState().value,
                    onDialog = depositViewModel::onDialog,
                    closedDialog = depositViewModel::closedDialog,
                    depositBalance = depositViewModel::depositBalance
                )
            }

            is Screen.WithdrawScreen -> {
                val withdrawViewModel = remember { WithdrawViewModel() }
                WithdrawScreen(
                    withdrawBalance = withdrawViewModel::withdrawBalance,
                    screenState = withdrawViewModel.screenState.collectAsState().value,
                    onDialog = withdrawViewModel::onDialog,
                    closedDialog = withdrawViewModel::closedDialog
                )
            }

            is Screen.ConversionScreen -> {
                val conversionViewModel = remember {
                    ConversionViewModel(
                        GetCurrencyRateUseCase(ConvertorRepositoryImpl())
                    )
                }
                ConversionScreen(
                    screenState = conversionViewModel.screenState.collectAsState().value,
                    getCurrencyRate = conversionViewModel::getCurrencyRate,
                    withdrawBalance = conversionViewModel::withdrawBalance,
                    onDialog = conversionViewModel::onDialog,
                    closedDialog = conversionViewModel::closedDialog,
                    convertCurrencyToRub = conversionViewModel::convertCurrencyToRub,
                    convertRubToCurrency = conversionViewModel::convertRubToCurrency
                )
            }

            is Screen.MoneyTransfers -> {
                val moneyTransfersViewModel = remember { MoneyTransfersViewModel() }
                MoneyTransfers(
                    screenState = moneyTransfersViewModel.screenState.collectAsState().value,
                    onDialog = moneyTransfersViewModel::onDialog,
                    closedDialog = moneyTransfersViewModel::closedDialog,
                    moneyTransfers = moneyTransfersViewModel::moneyTransfers
                )
            }
            is Screen.EventScreen -> {
                val eventViewModel = remember { EventViewModel() }
                EventScreen(
                    screenState = eventViewModel.screenState.collectAsState().value,
                    getUserEvents = eventViewModel::getUserEvents
                )
            }

            null -> exitProcess(0)
        }
    }
}


