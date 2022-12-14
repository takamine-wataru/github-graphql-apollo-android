package com.example.sample.ui.pages.myissuelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sample.ui.common.IssueItemBlock
import org.koin.androidx.compose.getViewModel

@Composable
internal fun MyIssueListPage(
    navController: NavController,
    viewModel: MyIssueListViewModel = getViewModel()
){
    val issueList by viewModel.issueListStateFlow.collectAsState()

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
                Text("MyIssueListPage", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(24.dp))
                issueList.forEach { issue ->
                    IssueItemBlock(issue, {})
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}