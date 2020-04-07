package com.apri.guserfinder.base

import androidx.recyclerview.widget.RecyclerView

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

abstract class BaseRecyclerAdapter<T : BaseViewHolder> : RecyclerView.Adapter<T>() {

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.markAttached()
    }

    override fun onViewRecycled(holder: T) {
        super.onViewRecycled(holder)
        holder.markDetached()
    }
}