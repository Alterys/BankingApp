package presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.navigation.BackButton
import presentation.navigation.LogoutButton
import presentation.theme.BankColors

@Composable
fun ProfileBar(id: String, onClick: () -> Unit, onBalance: Boolean, text: String?, nameScreen: String?) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (!onBalance && text != null && nameScreen != null) {
            BackButton(text)
            Row(
                modifier = Modifier
            ) {
                Box(
                    modifier = Modifier
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = BankColors.textBold,
                        fontSize = 16.sp,
                        text = nameScreen
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier.padding(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .shadow(0.dp, RoundedCornerShape(24.dp), clip = false)
                        .clip(shape = RoundedCornerShape(25.dp))
                        .border(
                            width = 2.dp,
                            color = BankColors.focusedBorderColor,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        modifier = Modifier
                            .shadow(0.dp, RoundedCornerShape(24.dp), clip = false)
                            .clip(shape = RoundedCornerShape(25.dp)),
                        onClick = onClick,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp,
                            disabledElevation = 0.dp,
                            hoveredElevation = 0.dp,
                            focusedElevation = 0.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowLeft,
                            contentDescription = "Показать меню",
                            tint = BankColors.textBold
                        )
                        Text(
                            modifier = Modifier,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = BankColors.textBold,
                            text = "Получить баланс"
                        )
                    }
                }
            }
            Row(
                modifier = Modifier,
            ) {
                Box(
                    modifier = Modifier
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = BankColors.textBold,
                        fontSize = 16.sp,
                        text = "Меню"
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .shadow(0.dp, RoundedCornerShape(24.dp), clip = false)
                .clip(shape = RoundedCornerShape(25.dp))
                .border(
                    width = 2.dp,
                    color = BankColors.focusedBorderColor,
                    shape = RoundedCornerShape(25.dp)
                )
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier,

                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp,
                        hoveredElevation = 0.dp,
                        focusedElevation = 0.dp
                    )
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Показать меню",
                            tint = BankColors.textBold
                        )
                        Text(
                            modifier = Modifier,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = BankColors.textBold,
                            text = "ID: $id"
                        )
                    }
                }
            }
            DropdownMenu(
                modifier = Modifier,
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                LogoutButton()
            }
        }
    }
}
