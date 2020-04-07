package com.apri.guserfinder.page

import androidx.lifecycle.MutableLiveData
import com.apri.guserfinder.extension.SingleLiveEvent
import com.apri.guserfinder.models.User

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

interface IMainViewModel {
    val userViewModels: MutableLiveData<List<User>>
    val findUserErrors: SingleLiveEvent<Exception>

    fun findUser(query: String)
    fun loadMoreUser(query: String)
}