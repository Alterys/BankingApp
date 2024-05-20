package presentation.screens.conversion

import domain.model.Currency

data class ConversionState(
    val currencyRate: Currency? = null,
    val error: String? = null,
    val onDialog: Boolean = false,
    val isLoading: Boolean = false
)
