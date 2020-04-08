package com.apri.guserfinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.apri.guserfinder.datasource.GithubAPI
import com.apri.guserfinder.injection.apiModule
import com.apri.guserfinder.injection.viewModelModule
import io.mockk.MockK
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
@Ignore("Base unit testing class")
open class BaseUnitTest : KoinTest {

    protected val testDispatcher = TestCoroutineDispatcher()
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    open fun setup() {
        val testModule = module {
            single(override = true) { mockk<GithubAPI>() }
        }

        startKoin {
            modules(
                listOf(apiModule, viewModelModule, testModule)
            )
        }
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}