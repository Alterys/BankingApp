package presentation

import data.storage.user.UserModel
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.storage.balance.BalanceModel
import data.storage.event.UserEventModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import presentation.navigation.Destination
import presentation.navigation.NavController
import presentation.navigation.Navigation
import presentation.navigation.Screen

fun main() = application {
    Window(
        onCloseRequest = { exitApplication() },
        title = "Банковское Приложение"
    ) {
        val database = Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")
        transaction(database) {
            SchemaUtils.create(UserModel, BalanceModel, UserEventModel)
        }
        val navController = NavController(
            Destination(
                Screen.AuthScreen
            )
        )

        Navigation(navController)
    }
}