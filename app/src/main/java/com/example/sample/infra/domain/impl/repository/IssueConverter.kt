package com.example.sample.infra.domain.impl.repository

import com.example.sample.domain.model.Assignee
import com.example.sample.domain.model.Issue
import com.example.sample.fragment.IssueFragment

internal object IssueConverter {
    fun convert(fragment: IssueFragment): Issue {
        return Issue(
            id = fragment.id,
            number = fragment.number,
            title = fragment.title,
            assignee = fragment.assignees.nodes?.firstOrNull()?.assigneeFragment?.let { assignee ->
                AssigneeConverter.convert(assignee)
            }
        )
    }

    fun convert(
        fragment: IssueFragment,
        assigneeName: String
    ): Issue? {
        val isAssign = fragment.assignees.nodes?.let { node ->
            val tmp = node.filter { it?.assigneeFragment?.name != assigneeName }
            tmp.isNotEmpty()
        } ?: false
        if(!isAssign) return null

        return Issue(
            id = fragment.id,
            number = fragment.number,
            title = fragment.title,
            assignee = fragment.assignees.nodes?.firstOrNull()?.assigneeFragment?.let { assignee ->
                Assignee(
                    id = assignee.id,
                    name = assignee.name
                )
            }
        )

    }
}