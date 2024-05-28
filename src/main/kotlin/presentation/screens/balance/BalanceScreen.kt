package presentation.screens.balance

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import presentation.components.ProfileBar
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import presentation.navigation.LocalNavController
import presentation.theme.BankColors
import java.math.BigDecimal

@Composable
fun BalanceScreen(
    screenState: BalanceState,
    onDialog: () -> Unit,
    closedDialog: () -> Unit,
    getBalance: (Long) -> Map<String, BigDecimal?>,
    navigate: (String, Int, String) -> Unit,
    addCurrency: (BigDecimal?, BigDecimal?, BigDecimal?, BigDecimal?) -> List<String>,
    chooseCanadianDollarForm: (BigDecimal) -> String,
    chooseRubleForm: (BigDecimal) -> String,
    chooseDollarForm: (BigDecimal) -> String,
    formatBigDecimal: (BigDecimal) -> BigDecimal,
    chooseFrancForm: (BigDecimal) -> String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LocalNavController.current.getCurrentArgs()?.let {
            ProfileBar(
                id = it["login"].toString(),
                onClick = {},
                onBalance = false,
                text = "Вернуться в меню",
                nameScreen = "Баланс"
            )
            val balance = getBalance(it["balanceId"].toString().toLong())
            val rub = formatBigDecimal(balance["rub"]!!)
            val cad = formatBigDecimal(balance["cad"]!!)
            val usd = formatBigDecimal(balance["usd"]!!)
            val eur = formatBigDecimal(balance["eur"]!!)
            val chf = formatBigDecimal(balance["chf"]!!)

            Box(
                modifier = Modifier
                    .height(435.dp)
                    .width(700.dp)
                    .shadow(
                        0.dp,
                        RoundedCornerShape(24.dp),
                        clip = false
                    )
                    .clip(shape = RoundedCornerShape(25.dp))
                    .border(
                        width = 2.dp,
                        color = BankColors.focusedBorderColor,
                        shape = RoundedCornerShape(25.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        modifier = Modifier,
                        text = "На счете:",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = BankColors.textBold
                    )
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(600.dp)
                            .height(50.dp)
                            .shadow(
                                0.dp,
                                RoundedCornerShape(24.dp),
                                clip = false
                            )
                            .clip(shape = RoundedCornerShape(25.dp))
                            .border(
                                width = 2.dp,
                                color = BankColors.focusedBorderColor,
                                shape = RoundedCornerShape(25.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "RUB: $rub ${chooseRubleForm(rub)}",
                            color = BankColors.textField,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )
                    }
                    if (usd != BigDecimal.ZERO) {
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .width(600.dp)
                                .height(50.dp)
                                .shadow(
                                    0.dp,
                                    RoundedCornerShape(24.dp),
                                    clip = false
                                )
                                .clip(shape = RoundedCornerShape(25.dp))
                                .border(
                                    width = 2.dp,
                                    color = BankColors.focusedBorderColor,
                                    shape = RoundedCornerShape(25.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = "USD: $usd ${chooseDollarForm(usd)}",
                                    color = BankColors.textField,
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp
                                )
                                OutlinedButton(
                                    modifier = Modifier
                                        .width(160.dp)
                                        .clip(shape = RoundedCornerShape(25.dp)),
                                    onClick = {
                                        navigate(
                                            it["login"].toString(),
                                            it["balanceId"].toString().toInt(),
                                            "USD",
                                        )
                                    },
                                    shape = RoundedCornerShape(25.dp),
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = BankColors.focusedBorderColor,
                                    )
                                ) {
                                    Text(
                                        text = "Пополнить",
                                        color = BankColors.textBold,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                    if (eur != BigDecimal.ZERO) {
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .width(600.dp)
                                .height(50.dp)
                                .shadow(
                                    0.dp,
                                    RoundedCornerShape(24.dp),
                                    clip = false
                                )
                                .clip(shape = RoundedCornerShape(25.dp))
                                .border(
                                    width = 2.dp,
                                    color = BankColors.focusedBorderColor,
                                    shape = RoundedCornerShape(25.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = "EUR: $eur евро",
                                    color = BankColors.textField,
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp
                                )
                                OutlinedButton(
                                    modifier = Modifier
                                        .width(160.dp)
                                        .clip(shape = RoundedCornerShape(25.dp)),
                                    onClick = {
                                        navigate(
                                            it["login"].toString(),
                                            it["balanceId"].toString().toInt(),
                                            "EUR",
                                        )
                                    },
                                    shape = RoundedCornerShape(25.dp),
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = BankColors.focusedBorderColor,
                                    )
                                ) {
                                    Text(
                                        text = "Пополнить",
                                        color = BankColors.textBold,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                    if (cad != BigDecimal.ZERO) {
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .width(600.dp)
                                .height(50.dp)
                                .shadow(
                                    0.dp,
                                    RoundedCornerShape(24.dp),
                                    clip = false
                                )
                                .clip(shape = RoundedCornerShape(25.dp))
                                .border(
                                    width = 2.dp,
                                    color = BankColors.focusedBorderColor,
                                    shape = RoundedCornerShape(25.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = "CAD: $cad ${chooseCanadianDollarForm(cad)}",
                                    color = BankColors.textField,
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp
                                )
                                OutlinedButton(
                                    modifier = Modifier
                                        .width(160.dp)
                                        .clip(shape = RoundedCornerShape(25.dp)),
                                    onClick = {
                                        navigate(
                                            it["login"].toString(),
                                            it["balanceId"].toString().toInt(),
                                            "CAD",
                                        )
                                    },
                                    shape = RoundedCornerShape(25.dp),
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = BankColors.focusedBorderColor,
                                    )
                                ) {
                                    Text(
                                        text = "Пополнить",
                                        color = BankColors.textBold,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                    if (chf != BigDecimal.ZERO) {
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .width(600.dp)
                                .height(50.dp)
                                .shadow(
                                    0.dp,
                                    RoundedCornerShape(24.dp),
                                    clip = false
                                )
                                .clip(shape = RoundedCornerShape(25.dp))
                                .border(
                                    width = 2.dp,
                                    color = BankColors.focusedBorderColor,
                                    shape = RoundedCornerShape(25.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = "CHF: $chf ${chooseFrancForm(chf)}",
                                    color = BankColors.textField,
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp
                                )
                                OutlinedButton(
                                    modifier = Modifier
                                        .width(160.dp)
                                        .clip(shape = RoundedCornerShape(25.dp)),
                                    onClick = {
                                        navigate(
                                            it["login"].toString(),
                                            it["balanceId"].toString().toInt(),
                                            "CHF",
                                        )
                                    },
                                    shape = RoundedCornerShape(25.dp),
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = BankColors.focusedBorderColor,
                                    )
                                ) {
                                    Text(
                                        text = "Пополнить",
                                        color = BankColors.textBold,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                    if (addCurrency(usd, cad, eur, chf).isNotEmpty()) {
                        OutlinedButton(
                            modifier = Modifier
                                .padding(10.dp)
                                .width(600.dp)
                                .height(50.dp)
                                .clip(shape = RoundedCornerShape(25.dp)),
                            onClick = { onDialog() },
                            shape = RoundedCornerShape(25.dp),
                            border = BorderStroke(
                                width = 2.dp,
                                color = BankColors.focusedBorderColor,
                            )
                        ) {
                            Text(
                                text = "Добавить другие валюты",
                                color = BankColors.textBold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
            if (screenState.onDialog) {
                Dialog(
                    onDismissRequest = { closedDialog() }
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            addCurrency(usd, cad, eur, chf).forEach { i ->
                                OutlinedButton(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .clip(shape = RoundedCornerShape(25.dp)),
                                    onClick = {
                                        navigate(
                                            it["login"].toString(),
                                            it["balanceId"].toString().toInt(),
                                            i
                                        )
                                    },
                                    shape = RoundedCornerShape(25.dp),
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = BankColors.focusedBorderColor,
                                    )
                                ) {
                                    Text(
                                        text = i,
                                        color = BankColors.textBold,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}