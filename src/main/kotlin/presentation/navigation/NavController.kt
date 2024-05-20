package presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

data class Destination(val screen: Screen, val args: Map<String, Any>? = null)

class NavController(initialDestination: Destination) {

    private val backStack: LinkedList<Destination> = LinkedList<Destination>()

    private val _currentScreen = MutableStateFlow<Screen?>(initialDestination.screen)
    val currentScreen: StateFlow<Screen?> = _currentScreen

    init {
        backStack.addLast(initialDestination)
    }


    fun navigateBack() {
        backStack.removeLastOrNull()
        _currentScreen.value = backStack.lastOrNull()?.screen
    }

    fun navigate(destination: Destination, clearBackStack: Boolean = false) {
        if (clearBackStack) {
            backStack.clear()
        }
        backStack.addLast(destination)
        _currentScreen.value = destination.screen
    }

    fun getCurrentArgs() = backStack.lastOrNull()?.args
}