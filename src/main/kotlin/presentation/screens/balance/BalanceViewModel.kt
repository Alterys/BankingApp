package presentation.screens.balance

import data.storage.balance.BalanceModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import presentation.navigation.Destination
import presentation.navigation.NavController
import presentation.navigation.Screen
import java.math.BigDecimal
import java.math.RoundingMode

class BalanceViewModel(
    private val navController: NavController
) {

    private val _screenState = MutableStateFlow(BalanceState())
    val screenState: StateFlow<BalanceState> = _screenState

    fun navigate(login: String, balanceId: Int, currency: String) {
        navController.navigate(
            Destination(
                Screen.ConversionScreen,
                mapOf("login" to login, "balanceId" to balanceId, "currency" to currency)
            )
        )
    }

    fun chooseRubleForm(amount: BigDecimal): String {
        val intValue = amount.setScale(0, RoundingMode.DOWN)
        val lastTwoDigits = intValue.remainder(BigDecimal(100)).toInt()
        val lastDigit = intValue.remainder(BigDecimal(10)).toInt()

        return if (lastTwoDigits in 11..14) {
            "рублей"
        } else {
            when (lastDigit) {
                1 -> "рубль"
                in 2..4 -> "рубля"
                else -> "рублей"
            }
        }
    }

    fun chooseCanadianDollarForm(amount: BigDecimal): String {
        val intValue = amount.setScale(0, RoundingMode.DOWN)
        val lastDigit = intValue.remainder(BigDecimal.TEN)
        val exceptions = listOf(
            BigDecimal.TEN + BigDecimal.ONE, BigDecimal.TEN + BigDecimal.valueOf(2),
            BigDecimal.TEN + BigDecimal.valueOf(3), BigDecimal.TEN + BigDecimal.valueOf(4)
        )
        val fractionalPart = amount.subtract(intValue)
        val hasFractionalPart = fractionalPart.multiply(BigDecimal.TEN).setScale(0, RoundingMode.DOWN) != BigDecimal.ZERO
        if (hasFractionalPart) return "канадский доллоров"
        return if (intValue in BigDecimal("11")..BigDecimal("14") || intValue in exceptions) "канадских долларов"
        else when (lastDigit) {
            BigDecimal.ONE -> "канадский доллар"
            in BigDecimal("2")..BigDecimal("4") -> "канадских доллара"
            else -> "канадских долларов"
        }
    }

    fun chooseFrancForm(amount: BigDecimal): String {
        val intValue = amount.setScale(0, RoundingMode.DOWN)
        val lastDigit = intValue.remainder(BigDecimal.TEN).toInt()
        val exceptions = listOf(
            BigDecimal.TEN + BigDecimal.ONE, BigDecimal.TEN + BigDecimal.valueOf(2),
            BigDecimal.TEN + BigDecimal.valueOf(3), BigDecimal.TEN + BigDecimal.valueOf(4)
        )
        val fractionalPart = amount.subtract(intValue)
        val hasFractionalPart = fractionalPart.multiply(BigDecimal.TEN).setScale(0, RoundingMode.DOWN) != BigDecimal.ZERO
        if (hasFractionalPart) return "швейцарских франков"
        return if (intValue in BigDecimal("11")..BigDecimal("14") || intValue in exceptions) "швейцарских франков"
        else when (lastDigit) {
            1 -> "швейцарский франк"
            in 2..4 -> "швейцарских франка"
            else -> "швейцарских франков"
        }
    }

    fun chooseDollarForm(amount: BigDecimal): String {
        val intValue = amount.setScale(0, RoundingMode.DOWN)
        val lastDigit = intValue.remainder(BigDecimal.TEN).toInt()
        val exceptions = listOf(
            BigDecimal.TEN + BigDecimal.ONE, BigDecimal.TEN + BigDecimal.valueOf(2),
            BigDecimal.TEN + BigDecimal.valueOf(3), BigDecimal.TEN + BigDecimal.valueOf(4)
        )

        val fractionalPart = amount.subtract(intValue)
        val hasFractionalPart = fractionalPart != BigDecimal.ZERO

        if (hasFractionalPart) {
            return "долларов"
        }

        if (intValue in BigDecimal("11")..BigDecimal("14") || intValue in exceptions) {
            return "долларов"
        }

        return when (lastDigit) {
            1 -> "доллар"
            in 2..4 -> "доллара"
            else -> "долларов"
        }
    }

    fun getBalance(id: Long): Map<String, BigDecimal?> {
        with(BalanceModel.getBalance(id)) {
            return if (this != null) mapOf(
                "rub" to rub,
                "eur" to eur,
                "cad" to cad,
                "usd" to usd,
                "chf" to chf
            ) else mapOf()
        }
    }

    fun addCurrency(usd: BigDecimal?, cad: BigDecimal?, eur: BigDecimal?, chf: BigDecimal?): List<String> {
        return listOfNotNull(
            usd.takeIf { (it?.compareTo(BigDecimal.ZERO) ?: 0) <= 0 }?.let { "USD" },
            cad.takeIf { (it?.compareTo(BigDecimal.ZERO) ?: 0) <= 0 }?.let { "CAD" },
            eur.takeIf { (it?.compareTo(BigDecimal.ZERO) ?: 0) <= 0 }?.let { "EUR" },
            chf.takeIf { (it?.compareTo(BigDecimal.ZERO) ?: 0) <= 0 }?.let { "CHF" }
        )
    }

    fun formatBigDecimal(input: BigDecimal): BigDecimal {
        val scaled = input.setScale(2, RoundingMode.HALF_UP)
        val remainder = scaled.remainder(BigDecimal.ONE)
        return if (remainder.compareTo(BigDecimal.ZERO) == 0) {
            scaled.setScale(0, RoundingMode.DOWN)
        } else {
            scaled.stripTrailingZeros()
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