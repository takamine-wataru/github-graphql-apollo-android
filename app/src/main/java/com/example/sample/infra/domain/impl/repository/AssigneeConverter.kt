package com.example.sample.infra.domain.impl.repository

import com.example.sample.domain.model.Assignee
import com.example.sample.domain.model.Issue
import com.example.sample.fragment.AssigneeFragment
import com.example.sample.fragment.IssueFragment

object AssigneeConverter {
    fun convert(fragment: AssigneeFragment): Assignee {
        return Assignee(
            id = fragment.id,
            name = fragment.name
        )
    }
}