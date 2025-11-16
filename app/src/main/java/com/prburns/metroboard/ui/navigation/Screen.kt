package com.prburns.metroboard.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Rail : Screen("rail", "Rail", Icons.Default.Train)
    data object Bus : Screen("bus", "Bus", Icons.Default.DirectionsBus)
    data object Alerts : Screen("alerts", "Alerts", Icons.Default.Warning)
}

val bottomNavItems = listOf(
    Screen.Rail,
    Screen.Bus,
    Screen.Alerts
)
