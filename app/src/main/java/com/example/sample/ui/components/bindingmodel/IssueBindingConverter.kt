package com.example.sample.ui.components.bindingmodel

import com.example.sample.domain.model.Issue

object IssueBindingConverter {
    fun convert(domainModel: Issue) :IssueBindingModel {
        return IssueBindingModel(
            id = domainModel.id,
            title = domainModel.title,
            number = domainModel.number,
            assignee = domainModel.assignee?.let {
                AssigneeBindingConverter.convert(it)
            }
        )
    }
}