package presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import presentation.theme.BankColors

@Composable
fun DialogError(error: String, onClick: () -> Unit) {
    Dialog(
        onDismissRequest = {}
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(36.dp),
                    text = error,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = BankColors.textBold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .padding(end = 10.dp, bottom = 10.dp)
                        .align(Alignment.End)
                ) {
                    OutlinedButton(
                        onClick = onClick
                    ) {
                        Text(
                            text = "ОК",
                            fontSize = 16.sp,
                            color = BankColors.textField
                        )
                    }
                }
            }
        }
    }
}