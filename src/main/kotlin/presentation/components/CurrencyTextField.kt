package presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import presentation.theme.BankColors

@Composable
fun CurrencyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    currencyCode: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = BankColors.focusedBorderColor,
            unfocusedBorderColor = BankColors.unfocusedBorderColor,
            textColor = BankColors.textField
        ),
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        trailingIcon = { Text(text = currencyCode, color = Color.LightGray) },
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
    )
}

