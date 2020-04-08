package com.apri.guserfinder.main.viewholders

import android.view.View
import com.apri.guserfinder.base.BaseViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_list_item.view.*

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 08/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

class UserViewHolder(view: View) : BaseViewHolder(view) {

    fun bindViewModel(viewModel: IUserViewModel) {
        itemView.tvName.text = viewModel.name
        Picasso.get().load(viewModel.avatarUrl).into(itemView.ivUser)
    }
}