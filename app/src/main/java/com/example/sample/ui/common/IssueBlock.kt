package com.example.sample.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sample.ui.common.bindingmodel.IssueBindingModel

@Composable
fun IssueItemBlock(
    issue: IssueBindingModel,
    onClick: (IssueBindingModel) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick(issue) }
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Column () {
            Text(
                text = issue.title,
                style = TextStyle(fontSize = 20.sp)
            )
            Text(
                text = "#${issue.number}",
                color = Color.Gray,
                style = TextStyle(fontSize = 15.sp)
            )

        }
        Spacer(Modifier.weight(1f))
        issue.assignee?.name?.let {
            Text(it)
        }
    }
}