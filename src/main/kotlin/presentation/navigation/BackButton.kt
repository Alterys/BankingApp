package presentation.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentation.theme.BankColors

@Composable
fun BackButton(text: String) {
    val navController = LocalNavController.current
    Row(
        modifier = Modifier.padding(5.dp),
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
                onClick = { navController.navigateBack() },
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
                    text = text
                )
            }
        }
    }
}