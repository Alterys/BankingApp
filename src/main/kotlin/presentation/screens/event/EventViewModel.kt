package presentation.screens.event

import data.storage.event.UserEventModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import presentation.screens.transfers.MoneyTransfersState

class EventViewModel {

    private val _screenState = MutableStateFlow(EventState())
    val screenState: StateFlow<EventState> = _screenState

    fun getUserEvents(idUser: Long) {
       val events = UserEventModel.getUserEvents(idUser).map {
           Event(
               id = it.id,
               eventDate = it.eventDate,
               eventType = it.eventType,
               eventState = it.eventState
           )
       }
       _screenState.update {
           it.copy(
               eventList = events
           )
       }
    }
}