package data.storage.user

import data.storage.balance.BalanceModel
import data.storage.event.UserEventModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object UserModel : Table("user") {
    val id = long("id").autoIncrement()
    private val login = varchar("login", 50)
    private val balanceId = reference("balance", BalanceModel.id)

    override val primaryKey = PrimaryKey(id)

    fun getUser(login: String): UserDTO? {
        return transaction {
            UserModel.selectAll().where { UserModel.login eq login }.singleOrNull()?.let {
                UserDTO(
                    id = it[UserModel.id],
                    login = it[UserModel.login],
                    balanceId = it[balanceId]
                )
            }
        }
    }
}

