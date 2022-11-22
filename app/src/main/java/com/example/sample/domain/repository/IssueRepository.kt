package com.example.sample.domain.repository

import com.example.sample.domain.model.Issue

interface IssueRepository {

    suspend fun addAssigneeToRepoIssue(issueId: Int, assigneeId: String)

    suspend fun fetchRepoIssueList(): List<Issue>

    suspend fun fetchRepoAssignIssueList(assignee: String): List<Issue>

    suspend fun fetchRepoIssue(number: Int): Issue

}