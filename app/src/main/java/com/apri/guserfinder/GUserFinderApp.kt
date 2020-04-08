package com.apri.guserfinder

import android.app.Application
import com.apri.guserfinder.injection.apiModule
import com.apri.guserfinder.injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

class GUserFinderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@GUserFinderApp)
            modules(listOf(viewModelModule, apiModule))
        }
    }
}