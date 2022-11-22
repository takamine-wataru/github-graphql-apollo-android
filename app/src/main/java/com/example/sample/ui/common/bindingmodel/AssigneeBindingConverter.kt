package com.example.sample.ui.common.bindingmodel

import com.example.sample.domain.model.Assignee

object AssigneeBindingConverter {
    fun convert(domainModel: Assignee): AssigneeBindingModel {
        return AssigneeBindingModel(
            id = domainModel.id,
            name =  domainModel.name
        )
    }
}