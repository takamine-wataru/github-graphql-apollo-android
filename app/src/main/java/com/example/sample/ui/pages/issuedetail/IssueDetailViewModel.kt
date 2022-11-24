package com.example.sample.ui.pages.issuedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.BuildConfig
import com.example.sample.domain.repository.IssueRepository
import com.example.sample.ui.common.bindingmodel.AssigneeBindingConverter
import com.example.sample.ui.common.bindingmodel.IssueBindingConverter
import com.example.sample.ui.common.bindingmodel.IssueBindingModel
import com.example.sample.ui.common.viewmodel.IssueItemViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class IssueDetailViewModel(
    private val issueNumber: Int,
    private val repository: IssueRepository
): ViewModel() {
    private val _issueStateFlow = MutableStateFlow<IssueBindingModel?>(null)
    val issueStateFlow = _issueStateFlow

    init {
        viewModelScope.launch {
            val issue = repository.fetchRepoIssue(issueNumber)
            println("hoghoge: binding assignee ${issue.assignee?.name}")
            _issueStateFlow.value = IssueBindingConverter.convert(issue)
            println("hogehoge: _issueStateFlow.value: ${_issueStateFlow.value!!.assignee}")
        }
    }

    fun onClickAssign(issueBindingModel: IssueBindingModel) {
        println("hogehoge: call onClickAssign")
        viewModelScope.launch {
            if(issueBindingModel.assignee == null) {
                val result = repository.addAssigneeToRepoIssue(issueBindingModel.id, BuildConfig.ASSIGNEE_ID)
                result?.let { assignee ->
                    println("hogehoge: result get")
                    _issueStateFlow.value = _issueStateFlow.value?.copy(
                        assignee = AssigneeBindingConverter.convert(assignee)
                    )
                }
            }
        }
    }

    fun onClickUnAssign(issueBindingModel: IssueBindingModel){
        viewModelScope.launch {
            println("hogehoge: call onClickUnAssign, ${issueBindingModel.assignee?.id}")
            issueBindingModel.assignee?.let { assignee ->
                println("hogehoge: assignee null")
                val result = repository.removeAssigneeFromRepoIssue(issueBindingModel.id, assignee.id)
                println("hogehoge: get result ")
                _issueStateFlow.value = _issueStateFlow.value?.copy(
                    assignee = null
                )
            }
        }
    }

}