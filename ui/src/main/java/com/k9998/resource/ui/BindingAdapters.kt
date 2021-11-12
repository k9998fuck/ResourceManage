package com.k9998.resource.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.k9998.resource.manage.IResource
import com.k9998.resource.manage.IResourceAction
import com.k9998.resource.ui.adapter.BaseDataBindingHolder
import com.k9998.resource.ui.databinding.ResourceActionBinding

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("app:resourceActions")
    fun setList(view: RecyclerView, resourceActions: List<IResourceAction<IResource>>?) {
        if (resourceActions != null) {
            view.adapter = object :
                ListAdapter<IResourceAction<IResource>, BaseDataBindingHolder<ResourceActionBinding>>(
                    object : DiffUtil.ItemCallback<IResourceAction<IResource>>() {
                        override fun areItemsTheSame(
                            oldItem: IResourceAction<IResource>,
                            newItem: IResourceAction<IResource>
                        ): Boolean {
                            return oldItem === newItem
                        }

                        override fun areContentsTheSame(
                            oldItem: IResourceAction<IResource>,
                            newItem: IResourceAction<IResource>
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): BaseDataBindingHolder<ResourceActionBinding> {
                    return BaseDataBindingHolder(
                        ResourceActionBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }

                override fun onBindViewHolder(
                    holder: BaseDataBindingHolder<ResourceActionBinding>,
                    position: Int
                ) {
                    holder.viewDataBinding.resourceAction = getItem(position)
                }
            }.apply {
                this.submitList(resourceActions)
            }
        }
    }

}