package com.example.sample.ui.components.bindingmodel


data class IssueBindingModel(
    val id: String,
    val title: String,
    val number: Int,
    val assignee: AssigneeBindingModel?
)