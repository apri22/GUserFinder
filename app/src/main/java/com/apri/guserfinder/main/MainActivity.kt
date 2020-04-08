package com.apri.guserfinder.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apri.guserfinder.R
import com.apri.guserfinder.datasource.APIException
import com.apri.guserfinder.extension.launchMain
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: IMainViewModel by viewModel<MainViewModel>()
    private val adapter by lazy { UserAdapter() }
    private var lastQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setupRecyclerView()
        this.setupSearchView()
        this.observeViewModel()
    }

    private fun setupSearchView() {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private var searchFor = ""
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (lastQuery == query) return false
                doSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                doSearch(newText)
                return true
            }

            private fun doSearch(keyword: String?) {
                searchFor = keyword ?: ""
                launchMain {
                    delay(300)
                    if (keyword != searchFor) return@launchMain
                    refreshFindUser(keyword)
                }
            }

        })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                val visibleThreshold = 1
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    Log.d("ScrollState", "Bottom ${recyclerView.canScrollVertically(1)}")
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    val totalItemCount = linearLayoutManager.itemCount

                    if (!adapter.isLoading && adapter.viewModels.isNotEmpty() &&
//                        !recyclerView.canScrollVertically(1) &&
                        dy > 0 &&
                        totalItemCount <= (lastVisibleItem + visibleThreshold)
                    ) {
                        adapter.showLoading()
                        viewModel.loadMoreUser(searchView.query.toString())

                    }
                }
            })

        swipeRefresh.setOnRefreshListener {
            refreshFindUser(searchView.query.toString())
        }
    }

    private fun refreshFindUser(query: String) {
        lastQuery = query
        llInfo.visibility = View.GONE
        swipeRefresh.isRefreshing = true
        this.viewModel.findUser(query)
    }

    private fun observeViewModel() {
        this.viewModel.userViewModels.observe(this, Observer {
            if (it.isEmpty() && !swipeRefresh.isRefreshing) {
                showNotFoundState()
            }
            adapter.updateItems(it)
        })
        this.viewModel.hideLoading.observe(this, Observer {
            swipeRefresh.isRefreshing = false
            adapter.showLoading(false)
        })
        this.viewModel.findUserErrors.observe(this, Observer {
            showErrorState(it)
        })
    }

    private fun showErrorState(exception: Exception) {
        val errorMessage =
            if (exception is APIException) exception.error.message else exception.localizedMessage
        if (adapter.itemCount > 0) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        } else {
            ivInfo.setImageResource(R.drawable.ic_error_outline)
            tvInfo.text = errorMessage
            llInfo.visibility = View.VISIBLE
        }
    }

    private fun showNotFoundState() {
        ivInfo.setImageResource(R.drawable.ic_search)
        tvInfo.text = getString(R.string.data_not_found_label)
        llInfo.visibility = View.VISIBLE
    }
}
