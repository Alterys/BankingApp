package presentation.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.theme.BankColors

@Composable
fun AuthScreen(login: (String) -> Unit, screenState: AuthState) {

    var id by remember { mutableStateOf("") }
    var isTextFieldFocused by remember { mutableStateOf(false) }


    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Авторизация",
                fontSize = 32.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = BankColors.textBold
            )

            OutlinedTextField(
                value = id.filter {
                    it.isDigit()
                },
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp)
                    .focusTarget()
                    .onFocusChanged { focusState -> isTextFieldFocused = focusState.isFocused },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    val filtered = it.filter { filterId ->
                        filterId.isDigit()
                    }
                    if (filtered.length <= 25) {
                        id = filtered
                    }
                },
                shape = RoundedCornerShape(25.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BankColors.focusedBorderColor,
                    unfocusedBorderColor = BankColors.unfocusedBorderColor,
                    textColor = BankColors.textField
                ),
                leadingIcon = {
                    Icon(Icons.Outlined.Person, contentDescription = "Person")
                },
                label = {
                    Text(
                        text = "Введи ваш ID",
                        color = if (isTextFieldFocused) BankColors.focusedBorderColor else BankColors.textDim
                    )
                }
            )
            Button(
                modifier = Modifier
                    .padding(16.dp)
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
                onClick = { login(id) },
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp,
                    hoveredElevation = 0.dp,
                    focusedElevation = 0.dp
                )
            ) {
                Text(
                    text = "Войти",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            screenState.error?.let {
                Text(
                    text = it,
                    color = BankColors.error,
                    fontSize = 16.sp
                )
            }
        }
    }
}
