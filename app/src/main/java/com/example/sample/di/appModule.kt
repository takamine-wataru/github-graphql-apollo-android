package com.example.sample.di

import com.apollographql.apollo3.ApolloClient
import com.example.sample.domain.repository.IssueRepository
import com.example.sample.infra.ApolloClientFactory
import com.example.sample.infra.domain.impl.repository.IssueRepositoryImpl
import com.example.sample.ui.common.viewmodel.IssueItemViewModel
import com.example.sample.ui.pages.issuelist.IssueListViewModel
import com.example.sample.ui.pages.myissuelist.MyIssueListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        IssueListViewModel(get())
    }
    viewModel {
        MyIssueListViewModel(get())
    }
    viewModel {
        IssueItemViewModel()
    }
    single<IssueRepository> { IssueRepositoryImpl(apolloClient = get())}
    single {
        ApolloClientFactory.create()
    }
}