package com.example.sample.ui.pages.myissuelist

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.apollographql.apollo3.ApolloClient

@Composable
fun MyIssueListPage(
    navController: NavController,
    apolloClient: ApolloClient
){
    Column(){
        Text("MyIssueListPage")
    }
}