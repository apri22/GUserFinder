package com.apri.guserfinder.datasource

import retrofit2.Response

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

data class ApiResult<R>(
    var data: R? = null,
    var response: Response<R>? = null,
    var exception: Exception? = null
) {
    val isSuccess: Boolean get() = exception == null
}