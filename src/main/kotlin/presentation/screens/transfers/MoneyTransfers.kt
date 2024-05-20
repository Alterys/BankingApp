package presentation.screens.transfers

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
import presentation.components.*
import presentation.navigation.LocalNavController
import presentation.theme.BankColors
import java.math.BigDecimal

@Composable
fun MoneyTransfers(
    screenState: MoneyTransfersState,
    moneyTransfers: (Long, String, BigDecimal?) -> Unit,
    onDialog: () -> Unit,
    closedDialog: () -> Unit
) {
    var sum by remember {
        mutableStateOf("")
    }
    var id by remember {
        mutableStateOf("")
    }
    Column {
        LocalNavController.current.getCurrentArgs()?.let {
            ProfileBar(
                id = it["login"].toString(),
                onClick = {},
                onBalance = false,
                text = "Вернуться в меню",
                nameScreen = "Переводы денежных средств"
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CurrencyTextField(
                    value = id.filter { filter ->
                        filter.isDigit()
                    },
                    modifier = Modifier.padding(5.dp),
                    onValueChange = { change ->
                        val filtered = change.filter { filterId ->
                            filterId.isDigit()
                        }
                        if (filtered.length <= 25) {
                            id = filtered
                        }
                    },
                    currencyCode = "ID"
                )

                RubTextField(
                    value = sum,
                    onValueChange = { newSum ->
                        sum = newSum
                    }
                )
                Button(
                    modifier = Modifier
                        .padding(5.dp)
                        .width(120.dp)
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
                        moneyTransfers(
                            it["balanceId"].toString().toLong(),
                            id,
                            sum.takeIf { takeIf -> takeIf.isNotBlank() }?.toBigDecimal()
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
                        text = "Перевести",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
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
}