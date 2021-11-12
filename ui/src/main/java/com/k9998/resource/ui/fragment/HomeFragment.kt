package com.k9998.resource.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.k9998.resource.manage.ResourceType
import com.k9998.resource.ui.autoCleared
import com.k9998.resource.ui.databinding.HomeFragmentBinding
import com.k9998.resource.ui.model.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding by autoCleared<HomeFragmentBinding>()
    private val homeViewModel: HomeViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        homeViewModel.resourceTypes.observe(viewLifecycleOwner) {
            binding.viewPager.adapter = MyFragmentStateAdapter(this, it)
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = it[position].name
            }.attach()
        }
    }

    class MyFragmentStateAdapter(
        fragment: Fragment,
        private val resourceTypes: List<ResourceType<*>>
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = resourceTypes.size

        override fun createFragment(position: Int): Fragment {
            val fragment = ResourceListFragment()
            fragment.arguments = bundleOf("resourceType" to resourceTypes[position])
            return fragment
        }

    }

}