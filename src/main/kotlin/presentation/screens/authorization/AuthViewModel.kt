package presentation.screens.authorization

import data.storage.user.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import presentation.navigation.Destination
import presentation.navigation.NavController
import presentation.navigation.Screen

class AuthViewModel(
    private val navController: NavController,
) {
    private val _screenState = MutableStateFlow(AuthState())
    val screenState: StateFlow<AuthState> = _screenState

    fun login(login: String) {
        val user = UserModel.getUser(login)
        val error = when {
            user != null -> {
                navigate(user.login, user.balanceId, user.id)
                null
            }
            login.isBlank() -> "Введите ID пользователя!"
            else -> "Вы ввели неверный ID пользователя!"
        }
        _screenState.update { it.copy(error = error) }
    }

    private fun navigate(login: String, balanceId: Long, id: Long) {
        navController.navigate(
            Destination(
                Screen.MenuScreen,
                mapOf("login" to login, "balanceId" to balanceId, "id" to id)
            )
        )
    }
}