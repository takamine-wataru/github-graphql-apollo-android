package com.example.sample.domain.model

data class Issue(
    val id: String,
    val number: Int,
    val title: String,
    val assignee: Assignee?
)
