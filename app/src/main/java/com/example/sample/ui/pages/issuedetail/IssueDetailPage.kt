package com.example.sample.ui.pages.issuedetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.apollographql.apollo3.ApolloClient

@Composable
fun IssueDetailPage(
    id: String,
    navController: NavController,
){
    Column(){
        Text("IssueDetailPage")
    }
}