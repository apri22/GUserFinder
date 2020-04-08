package com.apri.guserfinder.main

import androidx.lifecycle.MutableLiveData
import com.apri.guserfinder.extension.SingleLiveEvent
import com.apri.guserfinder.main.viewholders.UserViewModel
import com.apri.guserfinder.models.User

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

interface IMainViewModel {
    val userViewModels: MutableLiveData<List<UserViewModel>>
    val findUserErrors: SingleLiveEvent<Exception>
    val hideLoading: SingleLiveEvent<Boolean>

    fun findUser(query: String)
    fun loadMoreUser(query: String)
}