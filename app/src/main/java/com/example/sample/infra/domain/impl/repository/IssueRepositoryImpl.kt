package com.example.sample.infra.domain.impl.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.NormalizedCache
import com.apollographql.apollo3.cache.normalized.apolloStore
import com.apollographql.apollo3.cache.normalized.watch
import com.example.sample.*
import com.example.sample.domain.model.Assignee
import com.example.sample.domain.model.Issue
import com.example.sample.domain.repository.IssueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IssueRepositoryImpl(
    private val apolloClient: ApolloClient,

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

    override suspend fun fetchRepoIssueList(): Flow<List<Issue>> {
        val query = RepoIssueListQuery(
            owner = BuildConfig.REPOSITORY_OWNER,
            name = BuildConfig.REPOSITORY_NAME
        )
        return apolloClient.query(query).watch().map { response ->
            val data = response.dataAssertNoErrors
            val issueList = requireNotNull(data.repository?.issues?.nodes)
            issueList.mapNotNull { issue ->
                issue?.issueFragment?.let {
                    IssueConverter.convert(it)
                }
            }
        }
    }

    // FIXME: graphqlのクエリ上でfilterが効かなかったので、repository層でfilteringする
    override suspend fun fetchRepoAssignIssueList(): Flow<List<Issue>> {
        val query = RepoAssignIssueListQuery(
            owner = BuildConfig.REPOSITORY_OWNER,
            name = BuildConfig.REPOSITORY_NAME,
            assignee = BuildConfig.ASSIGNEE_NAME
        )
        return apolloClient.query(query).watch().map { response ->
            val data = response.dataAssertNoErrors
            val issueList = requireNotNull(data.repository?.issues?.nodes)
            issueList.mapNotNull { issue ->
                issue?.issueFragment?.let {
                    IssueConverter.convert(it, BuildConfig.ASSIGNEE_NAME)
                }
            }
        }
    }

    override suspend fun fetchRepoIssue(number: Int): Issue {
        val query = RepoIssueQuery(
            owner = BuildConfig.REPOSITORY_OWNER,
            name = BuildConfig.REPOSITORY_NAME,
            number = number
        )
        val dump = apolloClient.apolloStore.dump()
        val prettifyDump = NormalizedCache.prettifyDump(dump)
        print("hogehoge; $prettifyDump")
        val response = apolloClient.query(query).execute().dataAssertNoErrors
        val issueFragment = requireNotNull(response.repository?.issue?.issueFragment)

        return IssueConverter.convert(issueFragment)
    }
}