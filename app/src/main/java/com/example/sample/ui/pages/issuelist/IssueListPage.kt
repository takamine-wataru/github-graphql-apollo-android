package com.example.sample.ui.pages.issuelist

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.apollographql.apollo3.ApolloClient

@Composable
fun IssueListPage(
    navController: NavController,
    apolloClient: ApolloClient
){
    Column(){
        Text("IssueListPage")
    }
}