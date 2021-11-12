package com.k9998.resource.ui.model

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.k9998.resource.manage.IResource
import com.k9998.resource.ui.repository.ResourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ResourceViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val savedStateHandle: SavedStateHandle,
    private val resourceRepository: ResourceRepository
) : ViewModel() {

    val resource = savedStateHandle.get<IResource>("resource")!!

    val resourceActions = resourceRepository.getResourceActions(resource)

}