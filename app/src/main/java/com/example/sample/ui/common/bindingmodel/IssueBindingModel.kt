package com.example.sample.ui.common.bindingmodel


data class IssueBindingModel(
    val id: String,
    val title: String,
    val number: Int,
    val assignee: AssigneeBindingModel?
)