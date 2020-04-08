package com.apri.guserfinder.datasource

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 08/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

data class APIException(val error: ApiError) : Exception()

data class ApiError(val message: String)