package com.apri.guserfinder.main.viewholders

import com.apri.guserfinder.base.BaseViewModel
import com.apri.guserfinder.models.User

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 08/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

class UserViewModel(override val user: User) : BaseViewModel(), IUserViewModel {
    override val name: String = user.login
    override val avatarUrl: String = user.avatarUrl
}