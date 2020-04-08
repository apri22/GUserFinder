package com.apri.guserfinder.main

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import com.apri.guserfinder.R
import com.apri.guserfinder.base.BaseRecyclerAdapter
import com.apri.guserfinder.base.BaseViewHolder
import com.apri.guserfinder.main.viewholders.LoadingViewHolder
import com.apri.guserfinder.main.viewholders.UserViewHolder
import com.apri.guserfinder.main.viewholders.UserViewModel

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 08/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

class UserAdapter : BaseRecyclerAdapter<BaseViewHolder>() {
    private val itemType = 0
    private val loadingType = 1

    var isLoading = false
    var viewModels = listOf<UserViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == itemType) {
            val view = inflater.inflate(R.layout.user_list_item, parent, false)
            UserViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.loading_list_item, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is UserViewHolder) {
            holder.bindViewModel(viewModels[position])
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading && viewModels.isNotEmpty()) viewModels.size + 1 else viewModels.size
    }

    override fun getItemViewType(position: Int): Int {
        if (isLoading && viewModels.isNotEmpty() && position == itemCount - 1) return loadingType
        return itemType
    }

    fun updateItems(items: List<UserViewModel>) {
        this.viewModels = items
        Handler().post { this.notifyDataSetChanged() }
    }

    fun showLoading(show: Boolean = true) {
        this.isLoading = show
        Handler().post { this.notifyDataSetChanged() }
    }
}