package com.k9998.resource.ui.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import com.k9998.resource.manage.IResource
import com.k9998.resource.manage.IResourceAction
import com.k9998.resource.ui.adapter.BaseDataBindingHolder
import com.k9998.resource.ui.autoCleared
import com.k9998.resource.ui.databinding.ResourceItemBinding
import com.k9998.resource.ui.databinding.ResourceListFragmentBinding
import com.k9998.resource.ui.model.ResourceListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResourceListFragment : Fragment() {

    private var binding by autoCleared<ResourceListFragmentBinding>()
    private val resourceListViewModel: ResourceListViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ResourceListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resourceListViewModel = resourceListViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        resourceListViewModel.resources.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = object :
                ListAdapter<Pair<IResource, LiveData<MutableList<IResourceAction<IResource>>>>, BaseDataBindingHolder<ResourceItemBinding>>(
                    object :
                        DiffUtil.ItemCallback<Pair<IResource, LiveData<MutableList<IResourceAction<IResource>>>>>() {
                        override fun areItemsTheSame(
                            oldItem: Pair<IResource, LiveData<MutableList<IResourceAction<IResource>>>>,
                            newItem: Pair<IResource, LiveData<MutableList<IResourceAction<IResource>>>>
                        ): Boolean {
                            return oldItem === newItem
                        }

                        override fun areContentsTheSame(
                            oldItem: Pair<IResource, LiveData<MutableList<IResourceAction<IResource>>>>,
                            newItem: Pair<IResource, LiveData<MutableList<IResourceAction<IResource>>>>
                        ): Boolean {
                            return oldItem == newItem
                        }
                    }) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): BaseDataBindingHolder<ResourceItemBinding> {
                    return BaseDataBindingHolder(
                        ResourceItemBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        ).apply {
                            recyclerView.layoutManager = ChipsLayoutManager.newBuilder(requireContext()).build()
                            recyclerView.addItemDecoration(SpacingItemDecoration(10, 0))
                            lifecycleOwner = viewLifecycleOwner
                        }
                    )
                }

                override fun onBindViewHolder(
                    holder: BaseDataBindingHolder<ResourceItemBinding>,
                    position: Int
                ) {
                    val item = getItem(position)
                    holder.viewDataBinding.index = position
                    holder.viewDataBinding.resource = item.first
                    holder.viewDataBinding.resourceActions = item.second
                }
            }.apply {
                this.submitList(it)
            }
        }
    }

}