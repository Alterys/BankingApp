package data.storage.event


import data.storage.balance.BalanceModel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object UserEventModel : Table("event") {
    val id = long("id").autoIncrement()
    private val idUser = long("id_user")
    private val eventType = text("type")
    private val eventState = bool("event_state")
    private val eventDate = text("date")


    override val primaryKey = PrimaryKey(id)

    fun addEvent(idUser: Long, eventType: String, eventState: Boolean) {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        transaction {
            UserEventModel.insert {
                it[UserEventModel.idUser] = idUser
                it[UserEventModel.eventType] = eventType
                it[UserEventModel.eventState] = eventState
                it[eventDate] = LocalDateTime.now().format(formatter)

            }
        }
    }

    private fun mapToUserEventDTO(resultRow: ResultRow): UserEventDTO {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        val eventDate = resultRow[UserEventModel.eventDate]
        val formattedEventDate = LocalDateTime.parse(eventDate, formatter).toString()
        return UserEventDTO(
            id = resultRow[id],
            eventType = resultRow[eventType],
            eventDate = formattedEventDate,
            eventState = if (resultRow[eventState]) "Удачно" else "Неудачно"
        )
    }

    fun getUserEvents(idUser: Long): List<UserEventDTO> {
        return transaction {
            UserEventModel.selectAll().where { UserEventModel.idUser eq idUser }.map { mapToUserEventDTO(it) }
        }
    }
}

