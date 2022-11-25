package com.example.sample.ui.pages.issuelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.domain.repository.IssueRepository
import com.example.sample.ui.common.bindingmodel.IssueBindingConverter
import com.example.sample.ui.common.bindingmodel.IssueBindingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class IssueListViewModel(
    private val repository: IssueRepository
): ViewModel() {

    private val _issueListStateFlow = MutableStateFlow<List<IssueBindingModel>>(emptyList())
    val issueListStateFlow = _issueListStateFlow

    init {
        viewModelScope.launch {
            repository.fetchRepoIssueList().collect { result ->
                println("collect updated issueList")
                _issueListStateFlow.value = result.map {
                    IssueBindingConverter.convert(it)
                }
            }
        }
    }
}