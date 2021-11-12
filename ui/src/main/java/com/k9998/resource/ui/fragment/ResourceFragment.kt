package com.k9998.resource.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.k9998.resource.manage.IResource
import com.k9998.resource.manage.IResourceAction
import com.k9998.resource.manage.ResourceManage
import com.k9998.resource.ui.adapter.BaseDataBindingHolder
import com.k9998.resource.ui.autoCleared
import com.k9998.resource.ui.databinding.ResourceActionBinding
import com.k9998.resource.ui.databinding.ResourceItemBinding
import com.k9998.resource.ui.databinding.ResourceItemFragmentBinding
import com.k9998.resource.ui.model.ResourceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResourceFragment : Fragment() {

    private var binding by autoCleared<ResourceItemFragmentBinding>()
    private val resourceViewModel: ResourceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ResourceItemFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resourceViewModel = resourceViewModel
        resourceViewModel.resourceActions.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = object :
                ListAdapter<IResourceAction<in IResource>, BaseDataBindingHolder<ResourceActionBinding>>(
                    object : DiffUtil.ItemCallback<IResourceAction<in IResource>>() {
                        override fun areItemsTheSame(
                            oldItem: IResourceAction<in IResource>,
                            newItem: IResourceAction<in IResource>
                        ): Boolean {
                            return oldItem === newItem
                        }

                        override fun areContentsTheSame(
                            oldItem: IResourceAction<in IResource>,
                            newItem: IResourceAction<in IResource>
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
                        ).apply {
                            lifecycleOwner = viewLifecycleOwner
                        }
                    )
                }

                override fun onBindViewHolder(
                    holder: BaseDataBindingHolder<ResourceActionBinding>,
                    position: Int
                ) {
                    holder.viewDataBinding.resourceAction = getItem(position)
                }

            }.apply {
                this.submitList(it)
            }
        }


    }

}