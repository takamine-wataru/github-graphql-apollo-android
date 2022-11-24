package com.example.sample.ui.pages.issuedetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sample.BuildConfig
import com.example.sample.ui.common.IssueItemBlock
import com.example.sample.ui.common.viewmodel.IssueItemViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun IssueDetailPage(
    issueNumber: Int,
    navController: NavController,
    detailViewModel: IssueDetailViewModel = getViewModel { parametersOf(issueNumber) },
    itemViewModel: IssueItemViewModel = getViewModel(),
){

    val issue by detailViewModel.issueStateFlow.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        issue?.also {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Text("IssueDetailPage", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(24.dp))
                IssueItemBlock(issue = it, onClick = itemViewModel::onClickIssue)
                Spacer(Modifier.height(24.dp))
                val isAssign = it.assignee?.id == BuildConfig.ASSIGNEE_ID
                if(isAssign) {
                    TextButton(onClick = { detailViewModel.onClickUnAssign(it)}) {
                        Text(text = "unassign")
                    }
                } else {
                    TextButton(onClick = { detailViewModel.onClickAssign(it) }) {
                        Text(text = "assign")
                    }
                }
            }
        } ?: run {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}