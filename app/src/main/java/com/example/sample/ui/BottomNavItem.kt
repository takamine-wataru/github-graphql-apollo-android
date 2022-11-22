package com.example.sample.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.sample.R

sealed class BottomNavItem(var title:String, var icon: ImageVector, var route:String){

    object Issue : BottomNavItem("Issue", Icons.Filled.Home,"issue")
    object MyIssue: BottomNavItem("My Issue",Icons.Filled.Person,"my_issue")

}