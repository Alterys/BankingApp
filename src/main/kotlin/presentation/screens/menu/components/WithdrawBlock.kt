package presentation.screens.menu.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.theme.BankColors

@Composable
fun WithdrawBlock(
    listButton: List<String>,
    onClickWithdraw: (String) -> Unit,
    onClickWithdrawScreen: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(340.dp)
            .shadow(0.dp, RoundedCornerShape(24.dp), clip = false)
            .clip(shape = RoundedCornerShape(25.dp))
            .border(
                width = 2.dp,
                color = BankColors.focusedBorderColor,
                shape = RoundedCornerShape(25.dp)
            )
            .padding(5.dp),
    ) {
        Column {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Снять",
                style = TextStyle(
                    color = BankColors.textBold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listButton.forEach {
                        OutlinedButton(
                            modifier = Modifier.width(110.dp)
                                .padding(10.dp)
                                .clip(shape = RoundedCornerShape(25.dp)),
                            onClick = { onClickWithdraw(it) },
                            shape = RoundedCornerShape(25.dp),
                            border = BorderStroke(
                                width = 2.dp,
                                color = BankColors.focusedBorderColor,

                                )
                        ) {
                            Text(
                                text = "$it ₽",
                                color = BankColors.textBold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                OutlinedButton(
                    modifier = Modifier
                        .height(56.dp)
                        .width(200.dp)
                        .padding(10.dp)
                        .clip(shape = RoundedCornerShape(25.dp)),
                    onClick = onClickWithdrawScreen,
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = BankColors.focusedBorderColor,
                    )
                ) {
                    Text(
                        text = "Другая сумма",
                        color = BankColors.textBold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
