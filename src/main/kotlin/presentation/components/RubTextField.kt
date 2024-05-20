package presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RubTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    currencyCode: String = "RUB"
) {
    CurrencyTextField(
        value = value.filter { it.isDigit() },
        modifier = modifier.padding(5.dp),
        onValueChange = { newValue ->
            val filteredValue = newValue.filter { it.isDigit() }
            if (filteredValue.length <= 25) {
                onValueChange(filteredValue)
            }
        },
        currencyCode = currencyCode
    )
}