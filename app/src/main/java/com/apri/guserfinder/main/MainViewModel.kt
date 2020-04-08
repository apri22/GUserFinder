package com.apri.guserfinder.main

import androidx.lifecycle.MutableLiveData
import com.apri.guserfinder.base.BaseViewModel
import com.apri.guserfinder.datasource.GithubAPI
import com.apri.guserfinder.extension.*
import com.apri.guserfinder.main.viewholders.UserViewModel
import org.koin.core.inject

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

class MainViewModel : BaseViewModel(), IMainViewModel {
    override val userViewModels: MutableLiveData<List<UserViewModel>> = MutableLiveData()
    override val findUserErrors: SingleLiveEvent<Exception> = SingleLiveEvent()
    override val hideLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val githubAPI by inject<GithubAPI>()
    var page: Int = 0

    override fun findUser(query: String) {
        this.page = 1
        userViewModels.postValue(listOf())
        this.fetchUsers(query, page)
    }

    override fun loadMoreUser(query: String) {
        this.page++
        this.fetchUsers(query, page)
    }

    fun fetchUsers(query: String, page: Int) {
        uiJob {
            doAsyncNetworkCall { githubAPI.findUser(query, page) }
                .awaitForResult()
                .withSuccessResult { result ->
                    hideLoading.postValue(true)
                    val viewModels = result.items.map { UserViewModel(it) }
                    userViewModels.postAppending(viewModels)
                }.whenFailure {
                    hideLoading.postValue(true)
                    this@MainViewModel.page--
                    findUserErrors.postValue(it)
                }
        }
    }
}