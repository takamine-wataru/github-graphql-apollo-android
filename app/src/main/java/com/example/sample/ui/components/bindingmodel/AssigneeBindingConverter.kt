package com.example.sample.ui.components.bindingmodel

import com.example.sample.domain.model.Assignee

object AssigneeBindingConverter {
    fun convert(domainModel: Assignee): AssigneeBindingModel {
        return AssigneeBindingModel(
            id = domainModel.id,
            name =  domainModel.name
        )
    }
}