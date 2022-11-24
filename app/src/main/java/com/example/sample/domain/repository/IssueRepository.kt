package com.example.sample.domain.repository

import com.example.sample.domain.model.Assignee
import com.example.sample.domain.model.Issue

interface IssueRepository {

    suspend fun addAssigneeToRepoIssue(issueId: String, assigneeId: String): Assignee?

    suspend fun removeAssigneeFromRepoIssue(issueId: String, assigneeId: String): Assignee?

    suspend fun fetchRepoIssueList(): List<Issue>

    suspend fun fetchRepoAssignIssueList(): List<Issue>

    suspend fun fetchRepoIssue(number: Int): Issue

}