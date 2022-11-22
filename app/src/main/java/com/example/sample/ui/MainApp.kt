package com.example.sample.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.apollographql.apollo3.ApolloClient
import com.example.sample.ui.theme.SampleTheme
import androidx.navigation.compose.rememberNavController


@Composable
fun MainApp(
    apolloClient: ApolloClient,
) {
    val navController = rememberNavController()
    SampleTheme() {
        Scaffold(
            bottomBar = { BottomNavigation(navController = navController)}
        ) { innerPadding ->
            Surface(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                color = MaterialTheme.colors.background
            ) {
                NavigationGraph(navController, apolloClient)
            }
        }
    }
}