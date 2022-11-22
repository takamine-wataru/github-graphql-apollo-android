package com.example.sample.ui

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.apollographql.apollo3.ApolloClient
import com.example.sample.ui.pages.issuedetail.IssueDetailPage
import com.example.sample.ui.pages.issuelist.IssueListPage
import com.example.sample.ui.pages.myissuelist.MyIssueListPage

@Composable
fun NavigationGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Issue.route,
        route = "root-graph"
    ) {
        navigation(
            startDestination = "${BottomNavItem.Issue.route}/issuelist",
            route = BottomNavItem.Issue.route
        ) {
            composable(
                route = "${BottomNavItem.Issue.route}/issuelist",
            ) {
                IssueListPage(
                    navController = navController,
                )
            }
            composable(
                route = "${BottomNavItem.Issue.route}/issuedetail?id={id}",
                arguments = listOf(
                    navArgument(
                        name = "id"
                    ) {
                        type = NavType.StringType
                    }
                ),
            ) { backStackEntry ->
                val id = requireNotNull(backStackEntry.arguments?.getString("id"))
                IssueDetailPage(
                    id = id,
                    navController = navController,
                )
            }
        }
        navigation(
            startDestination = "${BottomNavItem.MyIssue.route}/myissuelist",
            route = BottomNavItem.MyIssue.route
        ) {
            composable(
                route = "${BottomNavItem.MyIssue.route}/myissuelist",
            ) {
                MyIssueListPage(
                    navController = navController,
                )
            }
        }
    }
}