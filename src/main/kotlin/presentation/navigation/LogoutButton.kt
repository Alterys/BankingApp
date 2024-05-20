package presentation.navigation

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import presentation.theme.BankColors

@Composable
fun LogoutButton() {
    val navController = LocalNavController.current
    val openDialog = remember { mutableStateOf(false) }
    TextButton(
        onClick = {
            openDialog.value = true
        },
        content = {
            Text(
                text = "Выход",
                color = BankColors.textBold
            )
        }
    )
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = {
                Text(
                    text = "Подтверждение действия",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BankColors.textBold
                )
            },
            text = {
                Text(
                    text = "Вы действительно хотите выйти?",
                    color = BankColors.textField
                )
            },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        openDialog.value = false
                        navController.navigate(
                            Destination(Screen.AuthScreen),
                            clearBackStack = true
                        )
                    }
                ) {
                    Text(
                        text = "Да",
                        fontSize = 16.sp,
                        color = BankColors.textField
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { openDialog.value = false }
                ) {
                    Text(
                        text = "Нет",
                        fontSize = 16.sp,
                        color = BankColors.textField
                    )
                }
            }
        )
    }
}