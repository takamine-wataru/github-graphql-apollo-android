package com.example.sample.ui.pages.myissuelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.domain.repository.IssueRepository
import com.example.sample.ui.common.bindingmodel.IssueBindingConverter
import com.example.sample.ui.common.bindingmodel.IssueBindingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class MyIssueListViewModel(
    private val repository: IssueRepository
): ViewModel() {

    private val _issueListStateFlow = MutableStateFlow<List<IssueBindingModel>>(emptyList())
    val issueListStateFlow = _issueListStateFlow

    init {
        viewModelScope.launch {
            val issue = repository.fetchRepoAssignIssueList()
            _issueListStateFlow.value = issue.map {
                IssueBindingConverter.convert(it)
            }
        }
    }
}