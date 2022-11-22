package com.example.sample.ui.pages.issuelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sample.ui.BottomNavItem
import com.example.sample.ui.common.IssueItemBlock
import com.example.sample.ui.common.viewmodel.IssueItemViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun IssueListPage(
    navController: NavController,
    listViewModel: IssueListViewModel = getViewModel(),
    itemViewModel: IssueItemViewModel = getViewModel(),
){
    val issueList by listViewModel.issueListStateFlow.collectAsState()
    val navigation by itemViewModel.navigationStateFlow.collectAsState()

    LaunchedEffect(navigation) {
        navigation?.let { issueId ->
            navController.navigate("${BottomNavItem.Issue.route}/issuedetail?id=${issueId}")
        }
    }

    val scrollableState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize()) {
        if(issueList.isEmpty()) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .verticalScroll(scrollableState)
            ) {
                Text("IssueListPage", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(24.dp))
                issueList.forEach { issue ->
                    IssueItemBlock(issue, itemViewModel::onClickIssue)
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}