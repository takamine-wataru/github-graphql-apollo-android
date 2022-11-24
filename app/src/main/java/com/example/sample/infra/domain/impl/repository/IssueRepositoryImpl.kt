package com.example.sample.infra.domain.impl.repository

import com.apollographql.apollo3.ApolloClient
import com.example.sample.*
import com.example.sample.domain.model.Assignee
import com.example.sample.domain.model.Issue
import com.example.sample.domain.repository.IssueRepository

class IssueRepositoryImpl(
    private val apolloClient: ApolloClient
): IssueRepository {
    override suspend fun addAssigneeToRepoIssue(issueId: String, assigneeId: String): Assignee? {
        val mutation = AddAssigneeRepoIssueMutation(issueId,  assigneeId)
        val response = apolloClient.mutation(mutation).execute().dataAssertNoErrors

        return response.addAssigneesToAssignable?.assignable?.assignees?.nodes?.firstOrNull()?.assigneeFragment.let { fragment ->
            fragment?.let {
                AssigneeConverter.convert(it)
            }
        }
    }

    override suspend fun removeAssigneeFromRepoIssue(issueId: String, assigneeId: String): Assignee? {
        val mutation = RemoveAssigneesFromRepoIssueMutation(issueId, assigneeId)
        val response = apolloClient.mutation(mutation).execute().dataAssertNoErrors

        return response.removeAssigneesFromAssignable?.assignable?.assignees?.nodes?.firstOrNull()?.assigneeFragment.let { fragment ->
            fragment?.let {
                AssigneeConverter.convert(it)
            }
        }
    }
    override suspend fun fetchRepoIssueList(): List<Issue> {
        val query = RepoIssueListQuery(
            owner = BuildConfig.REPOSITORY_OWNER,
            name = BuildConfig.REPOSITORY_NAME
        )
        val response = apolloClient.query(query).execute().dataAssertNoErrors
        val issueList = requireNotNull(response.repository?.issues?.nodes)
        return issueList.mapNotNull { issue ->
            issue?.issueFragment?.let {
                IssueConverter.convert(it)
            }

        }
    }

    // FIXME: graphqlのクエリ上でfilterが効かなかったので、repository層でfilteringする
    override suspend fun fetchRepoAssignIssueList(): List<Issue> {
        val query = RepoAssignIssueListQuery(
            owner = BuildConfig.REPOSITORY_OWNER,
            name = BuildConfig.REPOSITORY_NAME,
            assignee = BuildConfig.ASSIGNEE_NAME
        )
        val response = apolloClient.query(query).execute().dataAssertNoErrors

        return response.repository?.issues?.nodes?.mapNotNull { issue ->
            issue?.issueFragment?.let {
                IssueConverter.convert(it, BuildConfig.ASSIGNEE_NAME)
            }
        } ?: emptyList()
    }

    override suspend fun fetchRepoIssue(number: Int): Issue {
        val query = RepoIssueQuery(
            owner = BuildConfig.REPOSITORY_OWNER,
            name = BuildConfig.REPOSITORY_NAME,
            number = number
        )
        val response = apolloClient.query(query).execute().dataAssertNoErrors
        val issueFragment = requireNotNull(response.repository?.issue?.issueFragment)

        return IssueConverter.convert(issueFragment)
    }
}