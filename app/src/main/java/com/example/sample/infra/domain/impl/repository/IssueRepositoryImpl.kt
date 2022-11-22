package com.example.sample.infra.domain.impl.repository

import com.apollographql.apollo3.ApolloClient
import com.example.sample.*
import com.example.sample.domain.model.Assignee
import com.example.sample.domain.model.Issue
import com.example.sample.domain.repository.IssueRepository

class IssueRepositoryImpl(
    private val apolloClient: ApolloClient
): IssueRepository {
    override suspend fun addAssigneeToRepoIssue(issueId: Int, assigneeId: String) {
        val mutation = AddAssigneeRepoIssueMutation(issueId.toString(), assigneeId)
        val response = apolloClient.mutation(mutation).execute()
    }
    override suspend fun fetchRepoIssueList(): List<Issue> {
        val query = RepoIssueListQuery(
            owner = BuildConfig.REPOSITORY_OWNER,
            name = BuildConfig.REPOSITORY_NAME
        )
        val response = apolloClient.query(query).execute()
        val issueList = requireNotNull(response.data?.repository?.issues?.nodes)
        return issueList.mapNotNull { issue ->
            issue?.issueFragment?.let {
                IssueConverter.convert(it)
            }

        }
    }

    override suspend fun fetchRepoAssignIssueList(assignee: String): List<Issue> {
        val query = RepoAssignIssueListQuery(
            owner = BuildConfig.REPOSITORY_OWNER,
            name = BuildConfig.REPOSITORY_NAME,
            assignee = BuildConfig.ASSIGNEE_NAME
        )
        val response = apolloClient.query(query).execute()
        val issueList = requireNotNull(response.data?.repository?.issues?.nodes)

        return issueList.mapNotNull { issue ->
            issue?.issueFragment?.let {
                IssueConverter.convert(it)
            }

        }
    }

    override suspend fun fetchRepoIssue(number: Int): Issue {
        val query = RepoIssueQuery(
            owner = BuildConfig.REPOSITORY_OWNER,
            name = BuildConfig.REPOSITORY_NAME,
            number = number
        )
        val response = apolloClient.query(query).execute()
        val issueFragment = requireNotNull(response.data?.repository?.issue?.issueFragment)

        return IssueConverter.convert(issueFragment)
    }
}