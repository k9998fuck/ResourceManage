package com.k9998.resource.ui.model

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.k9998.resource.manage.ResourceType
import com.k9998.resource.ui.repository.ResourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ResourceListViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val savedStateHandle: SavedStateHandle,
    private val resourceRepository: ResourceRepository
) : ViewModel() {

    val resources = resourceRepository.getResources(savedStateHandle.get<ResourceType<*>>("resourceType")!!).map {
        it.map { resource ->
            resource to resourceRepository.getResourceActions(resource)
        }
    }

}