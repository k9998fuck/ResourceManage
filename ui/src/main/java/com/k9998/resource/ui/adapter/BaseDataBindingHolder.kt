package com.k9998.resource.ui.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

data class BaseDataBindingHolder<T : ViewDataBinding>(val viewDataBinding: T) :
    RecyclerView.ViewHolder(viewDataBinding.root)