package com.apri.guserfinder.datasource

import com.apri.guserfinder.models.DataResult
import com.apri.guserfinder.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

interface GithubAPI {

    @GET("search/users")
    suspend fun findUser(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10
    ): Response<DataResult<List<User>>>

}