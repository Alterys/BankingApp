package domain.model

import java.math.BigDecimal

data class Currency(
    val cad: BigDecimal,
    val usd: BigDecimal,
    val rub: BigDecimal,
    val eur: BigDecimal,
    val chf: BigDecimal
)
