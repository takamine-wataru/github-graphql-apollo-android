package com.example.sample.ui

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Issue,
        BottomNavItem.MyIssue
    )
    androidx.compose.material.BottomNavigation(
       backgroundColor = Color.White,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = null, modifier = Modifier, tint = Color.Black) },
                label = { Text(item.title, color = Color.Black) },
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route)}
            )
        }
    }
}