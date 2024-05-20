package data.storage.event

import java.time.LocalDateTime

data class UserEventDTO(
    val id: Long,
    val eventType: String,
    val eventDate: String,
    val eventState: String
)
