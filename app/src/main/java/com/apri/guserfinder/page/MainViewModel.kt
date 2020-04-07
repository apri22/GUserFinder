package com.apri.guserfinder.page

import androidx.lifecycle.MutableLiveData
import com.apri.guserfinder.base.BaseViewModel
import com.apri.guserfinder.datasource.GithubAPI
import com.apri.guserfinder.extension.*
import com.apri.guserfinder.models.User
import org.koin.core.inject

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

class MainViewModel : BaseViewModel(), IMainViewModel {
    override val userViewModels: MutableLiveData<List<User>> = MutableLiveData()
    override val findUserErrors: SingleLiveEvent<Exception> = SingleLiveEvent()

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
                .withSuccessResult {
                    userViewModels.postAppending(it.items)
                }.whenFailure {
                    findUserErrors.postValue(it)
                }
        }
    }
}