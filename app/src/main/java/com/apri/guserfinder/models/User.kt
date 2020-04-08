package com.apri.guserfinder.models

import com.google.gson.annotations.SerializedName

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

data class User(
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)