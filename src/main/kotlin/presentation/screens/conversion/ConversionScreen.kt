package presentation.screens.conversion


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import presentation.components.*
import presentation.navigation.LocalNavController
import presentation.theme.BankColors
import java.math.BigDecimal

@Composable
fun ConversionScreen(
    screenState: ConversionState,
    getCurrencyRate: (CoroutineScope) -> Unit,
    withdrawBalance: (Long, BigDecimal?, String, BigDecimal?) -> Unit,
    onDialog: () -> Unit,
    closedDialog: () -> Unit,
    convertRubToCurrency: (String, BigDecimal?) -> BigDecimal?,
    convertCurrencyToRub: (String, BigDecimal?) -> BigDecimal?
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        getCurrencyRate(coroutineScope)
    }

    val currencyRate = screenState.currencyRate
    LocalNavController.current.getCurrentArgs()?.let {
        val currency = it["currency"]
        Column {
            ProfileBar(
                id = it["login"].toString(),
                onClick = {},
                onBalance = false,
                text = "Вернуться в баланс",
                nameScreen = "Конвертация наличных"
            )


            if (currencyRate == null) {
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box {
                        CircularProgressIndicator(
                            color = BankColors.textBold
                        )
                    }
                }
            } else {
                Column {
                    val currencyValue: BigDecimal? = when (currency) {
                        "USD" -> currencyRate.usd
                        "EUR" -> currencyRate.eur
                        "CAD" -> currencyRate.cad
                        "CHF" -> currencyRate.chf
                        else -> {
                            null
                        }
                    }

                    var rub by remember {
                        mutableStateOf("")
                    }
                    var updatedCurrency by remember {
                        mutableStateOf("")
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "1 рубль = ${String.format("%.6f", currencyValue)} $currency",
                            fontSize = 16.sp,
                            color = BankColors.textBold,
                            fontWeight = FontWeight.Bold
                        )
                        CurrencyTextField(
                            modifier = Modifier.padding(10.dp),
                            value = rub.filter { filteredValue ->
                                filteredValue.isDigit() || filteredValue == '.'
                            },
                            onValueChange = { change ->
                                val filteredChange = change.filter { filtered -> filtered.isDigit() || filtered == '.' }
                                rub = filteredChange
                                val conversionResult = convertRubToCurrency(rub, currencyValue)
                                updatedCurrency = conversionResult?.toString() ?: ""
                            },
                            currencyCode = "RUB"
                        )
                        CurrencyTextField(
                            value = updatedCurrency.filter { filteredValue ->
                                filteredValue.isDigit() || filteredValue == '.'
                            },
                            onValueChange = { change ->
                                val filteredChange = change.filter { filtered -> filtered.isDigit() || filtered == '.' }
                                updatedCurrency = filteredChange
                                val conversionResult =
                                    convertCurrencyToRub(updatedCurrency, currencyValue)
                                rub = conversionResult?.toString() ?: ""
                            },
                            currencyCode = currency.toString()
                        )
                        Button(
                            modifier = Modifier
                                .padding(10.dp)
                                .width(200.dp)
                                .height(45.dp)
                                .shadow(0.dp, RoundedCornerShape(24.dp), clip = false)
                                .clip(shape = RoundedCornerShape(25.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            BankColors.buttonDark,
                                            BankColors.buttonLight
                                        )
                                    )
                                ),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                            onClick = {
                                withdrawBalance(
                                    it["balanceId"].toString().toLong(),
                                    rub.takeIf { takeIf -> takeIf.isNotBlank() }?.toBigDecimal(),
                                    currency.toString(),
                                    currencyValue
                                )
                                onDialog()
                            },
                            elevation = ButtonDefaults.elevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 0.dp,
                                disabledElevation = 0.dp,
                                hoveredElevation = 0.dp,
                                focusedElevation = 0.dp
                            )
                        ) {
                            Text(
                                text = "Конвертировать",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                        }
                    }
                }
            }
        }
    }
    if (screenState.onDialog) {
        if (screenState.error != null) {
            DialogError(
                error = screenState.error,
                onClick = { closedDialog() }
            )
        } else {
            DialogComplete(
                onClick = { closedDialog() }
            )
        }
    }
}