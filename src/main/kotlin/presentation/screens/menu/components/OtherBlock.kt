package presentation.screens.menu.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.theme.BankColors

@Composable
fun OtherBlock(
    onClickDeposit: () -> Unit,
    onClickEvent: () -> Unit,
    onClickTransfers: () -> Unit,
    textEvent: String,
    textDeposit: String,
    textTransfers: String,
    event: Boolean
) {
    Box(
        modifier = Modifier.height(200.dp)
            .width(340.dp)
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
            ).padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            if (event) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .width(160.dp).padding(10.dp).width(120.dp)
                            .clip(shape = RoundedCornerShape(25.dp)),
                        onClick = onClickEvent,
                        shape = RoundedCornerShape(25.dp),
                        border = BorderStroke(
                            width = 2.dp,
                            color = BankColors.focusedBorderColor,
                        )
                    ) {
                        Text(
                            text = textEvent,
                            color = BankColors.textBold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .width(160.dp).padding(10.dp).width(120.dp)
                        .clip(shape = RoundedCornerShape(25.dp)),
                    onClick = onClickDeposit,
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = BankColors.focusedBorderColor,
                    )
                ) {
                    Text(
                        text = textDeposit,
                        color = BankColors.textBold,
                        fontSize = 16.sp
                    )
                }
                OutlinedButton(
                    modifier = Modifier
                        .width(160.dp).padding(10.dp).width(120.dp)
                        .clip(shape = RoundedCornerShape(25.dp)),
                    onClick = onClickTransfers,
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = BankColors.focusedBorderColor,
                    )
                ) {
                    Text(
                        text = textTransfers,
                        color = BankColors.textBold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}