package com.apri.guserfinder.main.viewholders

import com.apri.guserfinder.models.User

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 08/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

interface IUserViewModel {
    val name: String
    val avatarUrl: String

    val user: User
}