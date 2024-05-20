package data.storage.balance

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.math.BigDecimal

object BalanceModel : Table("balance") {
    val id = long("id").autoIncrement()
    private val rub = text("rub")
    private val eur = text("eur")
    private val cad = text("cad")
    private val usd = text("usd")
    private val chf = text("chf")

    override val primaryKey = PrimaryKey(id)

    fun getBalance(id: Long): BalanceDTO? {
        return transaction {
            BalanceModel.selectAll().where { BalanceModel.id eq id }.singleOrNull()?.let {
                BalanceDTO(
                    rub = it[rub].toBigDecimal(),
                    eur = it[eur].toBigDecimal(),
                    cad = it[cad].toBigDecimal(),
                    usd = it[usd].toBigDecimal(),
                    chf = it[chf].toBigDecimal()
                )
            }
        }
    }

    fun withdrawBalance(withdraw: BigDecimal, balanceId: Long, currency: String) {
        return transaction {
            BalanceModel.update(where = { BalanceModel.id eq balanceId }) { balanceUpdate ->
                BalanceModel.selectAll().where { id eq balanceId }.singleOrNull()?.let { balanceSelect ->
                    when (currency) {
                        "USD" -> balanceUpdate[usd] = (balanceSelect[usd].toBigDecimal() - withdraw).toString()
                        "RUB" -> balanceUpdate[rub] = (balanceSelect[rub].toBigDecimal() - withdraw).toString()
                        "EUR" -> balanceUpdate[eur] = (balanceSelect[eur].toBigDecimal() - withdraw).toString()
                        "CAD" -> balanceUpdate[cad] = (balanceSelect[cad].toBigDecimal() - withdraw).toString()
                        "CHF" -> balanceUpdate[chf] = (balanceSelect[chf].toBigDecimal() - withdraw).toString()
                    }
                }
            }
        }
    }

    fun depositBalance(deposit: BigDecimal, balanceId: Long, currency: String) {
        return transaction {
            BalanceModel.update(where = { BalanceModel.id eq balanceId }) { balanceUpdate ->
                BalanceModel.selectAll().where { id eq balanceId }.singleOrNull()?.let { balanceSelect ->
                    when (currency) {
                        "USD" -> balanceUpdate[usd] = (balanceSelect[usd].toBigDecimal() + deposit).toString()
                        "RUB" -> balanceUpdate[rub] = (balanceSelect[rub].toBigDecimal() + deposit).toString()
                        "EUR" -> balanceUpdate[eur] = (balanceSelect[eur].toBigDecimal() + deposit).toString()
                        "CAD" -> balanceUpdate[cad] = (balanceSelect[cad].toBigDecimal() + deposit).toString()
                        "CHF" -> balanceUpdate[chf] = (balanceSelect[chf].toBigDecimal() + deposit).toString()
                    }
                }
            }
        }
    }
}