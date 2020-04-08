package com.apri.guserfinder

import androidx.lifecycle.MutableLiveData
import com.apri.guserfinder.datasource.GithubAPI
import com.apri.guserfinder.extension.SingleLiveEvent
import com.apri.guserfinder.models.DataResult
import com.apri.guserfinder.models.User
import com.apri.guserfinder.main.MainViewModel
import com.apri.guserfinder.main.viewholders.UserViewModel
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.junit.Test
import org.koin.core.inject
import retrofit2.Response

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

@ExperimentalCoroutinesApi
class MainViewModelTest : BaseUnitTest() {

    @InjectMockKs
    lateinit var viewModel: MainViewModel

    val githubAPI by inject<GithubAPI>()

    @Test
    fun `Initial condition`() {
        viewModel.userViewModels.value?.shouldBeEmpty()
        viewModel.page shouldBeEqualTo 0
    }

    @Test
    fun `Call find user should load query with page 1`() = testDispatcher.runBlockingTest {
        viewModel = spyk(viewModel)
        val query = "Test"
        every { viewModel.fetchUsers(any(), any()) } just Runs

        viewModel.page shouldBeEqualTo 0
        viewModel.findUser(query)
        viewModel.page shouldBeEqualTo 1
        verify(exactly = 1) {
            viewModel.fetchUsers(query, 1)
        }
    }

    @Test
    fun `Call load more should load next page`() = testDispatcher.runBlockingTest {
        viewModel = spyk(viewModel)
        val query = "Test"
        every { viewModel.fetchUsers(any(), any()) } just Runs

        viewModel.page = 1
        viewModel.loadMoreUser(query)
        viewModel.page shouldBeEqualTo 2
        verify(exactly = 1) {
            viewModel.fetchUsers(query, 2)
        }
    }

    @Test
    fun `fetch user when success`() = testDispatcher.runBlockingTest {
        val user = User("Test", "url")
        val result = DataResult(1, listOf(user))
        val response: Response<DataResult<List<User>>> = mockk()
        every { response.body() } returns result
        val queryArg = slot<String>()
        val pageArg = slot<Int>()
        coEvery { githubAPI.findUser(capture(queryArg), capture(pageArg)) } returns response

        viewModel.fetchUsers("Key", 1)

        coVerify(exactly = 1) { githubAPI.findUser(any(), any()) }
        viewModel.userViewModels.value?.get(0)?.user shouldBeEqualTo user
        viewModel.userViewModels.value?.size shouldBeEqualTo 1
        queryArg.captured shouldBeEqualTo "Key"
        pageArg.captured shouldBeEqualTo 1
    }

    @Test
    fun `fetch user when failure`() = testDispatcher.runBlockingTest {
        val response: Response<DataResult<List<User>>> = mockk()
        val exception = Exception("error")
        every { response.body() } throws exception
        val queryArg = slot<String>()
        val pageArg = slot<Int>()
        coEvery { githubAPI.findUser(capture(queryArg), capture(pageArg)) } returns response

        viewModel.page = 1
        viewModel.fetchUsers("Key", 1)

        coVerify(exactly = 1) { githubAPI.findUser(any(), any()) }
        viewModel.findUserErrors.value shouldBeEqualTo exception
        viewModel.userViewModels.value shouldBeEqualTo null
        queryArg.captured shouldBeEqualTo "Key"
        pageArg.captured shouldBeEqualTo 1
        viewModel.page shouldBeEqualTo 0
    }
}