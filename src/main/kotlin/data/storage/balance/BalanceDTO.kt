package data.storage.balance

import java.math.BigDecimal

data class BalanceDTO(
    val rub: BigDecimal,
    val eur: BigDecimal,
    val cad: BigDecimal,
    val usd: BigDecimal,
    val chf: BigDecimal
)
