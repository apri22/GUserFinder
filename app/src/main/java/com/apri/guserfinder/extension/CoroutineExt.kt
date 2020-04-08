package com.apri.guserfinder.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apri.guserfinder.datasource.APIException
import com.apri.guserfinder.datasource.ApiError
import com.apri.guserfinder.datasource.ApiResult
import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.Response

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//


suspend fun <R> doAsyncNetworkCall(block: suspend () -> Response<R>): Deferred<Response<R>> {
    return GlobalScope.async(Dispatchers.IO) { block.invoke() }
}

suspend fun <R> Deferred<Response<R>>.awaitForResult(): ApiResult<R> {
    val result = ApiResult<R>()
    try {
        val response = await()
        result.response = response
        result.data = response.body()
    } catch (ex: Exception) {
        result.exception = ex
    } finally {
        return result
    }
}

fun <R> ApiResult<R>.withSuccessResult(block: (R) -> Unit): ApiResult<R> {
    if (this.isSuccess) {
        response?.body()?.notNull { block(it) }
    }
    return this
}

fun <R> ApiResult<R>.whenFailure(block: (Exception) -> Unit) {
    if (!this.isSuccess) {
        exception?.notNull { block(it) }
    } else if (response?.body() == null && response?.errorBody() != null) {
        val jsonError = response?.errorBody()?.string()
        val apiError = Gson().fromJson<ApiError>(jsonError, ApiError::class.java)
        block(APIException(apiError))
    }
}

fun ViewModel.uiJob(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.Main) {
        block()
    }
}

fun launchMain(block: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        block.invoke()
    }
}