package com.apri.guserfinder.extension

import androidx.lifecycle.MutableLiveData

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//


fun <T> MutableLiveData<List<T>>.postAppending(datas: List<T>?) {
    if (datas == null) return

    if (this.value != null) {
        this.postValue(this.value!! + datas)
    } else {
        this.postValue(datas)
    }
}

fun <T> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}
