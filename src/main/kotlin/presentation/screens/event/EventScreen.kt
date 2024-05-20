package presentation.screens.event

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.components.ProfileBar
import presentation.navigation.LocalNavController
import presentation.theme.BankColors

@Composable
fun EventScreen(
    getUserEvents: (Long) -> Unit,
    screenState: EventState,
) {
    Column {
        LocalNavController.current.getCurrentArgs()?.let {
            ProfileBar(
                id = it["login"].toString(),
                onClick = {},
                onBalance = false,
                text = "Вернуться в меню",
                nameScreen = "История операций"
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (screenState.eventList == null) {
                    getUserEvents(it["id"].toString().toLong())
                    Row(
                        Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box {
                            CircularProgressIndicator(
                                color = BankColors.textBold
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LazyColumn {
                            items(screenState.eventList) { event ->
                                Box(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .width(700.dp)
                                        .height(50.dp)
                                        .shadow(
                                            0.dp,
                                            RoundedCornerShape(24.dp),
                                            clip = false
                                        )
                                        .clip(shape = RoundedCornerShape(25.dp))
                                        .border(
                                            width = 2.dp,
                                            color = BankColors.focusedBorderColor,
                                            shape = RoundedCornerShape(25.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = event.id.toString() + " " + event.eventType + " " + event.eventDate
                                            .lowercase()
                                            .replace("t", " ") + " " + event.eventState,
                                        color = BankColors.textField,
                                        textAlign = TextAlign.Center,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
