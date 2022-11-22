package com.example.sample.ui.pages.issuelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.domain.repository.IssueRepository
import com.example.sample.ui.components.bindingmodel.IssueBindingConverter
import com.example.sample.ui.components.bindingmodel.IssueBindingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class IssueListViewModel(
    private val repository: IssueRepository
): ViewModel() {

    private val _issueListStateFlow = MutableStateFlow<List<IssueBindingModel>>(emptyList())
    val issueListStateFlow = _issueListStateFlow

    init {
        viewModelScope.launch {
            val issue = repository.fetchRepoIssueList()
            _issueListStateFlow.value = issue.map {
                IssueBindingConverter.convert(it)
            }
        }
    }
}