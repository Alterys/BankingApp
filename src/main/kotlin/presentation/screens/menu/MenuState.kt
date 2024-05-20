package presentation.screens.menu

import presentation.screens.event.Event

data class MenuState(
    var error: String? = null,
    var onDialog: Boolean = false,
    val eventList: List<Event>? = null
)