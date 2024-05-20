package presentation.navigation

sealed class Screen {
    data object AuthScreen : Screen()
    data object MenuScreen : Screen()
    data object BalanceScreen : Screen()
    data object DepositScreen : Screen()
    data object WithdrawScreen : Screen()
    data object ConversionScreen : Screen()
    data object MoneyTransfers : Screen()
    data object EventScreen : Screen()
}