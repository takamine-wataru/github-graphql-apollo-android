package com.example.sample.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sample.ui.common.bindingmodel.IssueBindingModel
import kotlinx.coroutines.flow.MutableStateFlow

internal class IssueItemViewModel: ViewModel() {

    private val _navigationStateFlow = MutableStateFlow<Int?>(null)
    val navigationStateFlow = _navigationStateFlow

    fun onClickIssue(bindingModel: IssueBindingModel) {
        navigationStateFlow.value = bindingModel.number
    }
}