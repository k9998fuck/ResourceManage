package com.k9998.resource.ui.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.k9998.resource.manage.IResource
import com.k9998.resource.manage.IResourceAction
import com.k9998.resource.manage.ResourceManage
import com.k9998.resource.manage.ResourceType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceRepository @Inject constructor(@ApplicationContext val appContext: Context) {

    val TAG = "ResourceRepository"

    fun getResourceTypes(): LiveData<MutableList<ResourceType<*>>> {
        return object : LiveData<MutableList<ResourceType<*>>>() {

            val resourceManage = ResourceManage.getInstance()
            val listener = object : ResourceManage.Listener {
                override fun change(resourceTypes: MutableList<ResourceType<*>>) {
                    value = resourceTypes
                }

                override fun <T : IResource?> change(
                    resourceTypes: ResourceType<T>,
                    resources: MutableList<T>
                ) {

                }

                override fun <T : IResource?> change(
                    resource: IResource,
                    resourceActions: MutableList<IResourceAction<T>>
                ) {

                }

            }

            override fun onActive() {
                value = resourceManage.resourceTypes
                resourceManage.addListener(listener)
            }

            override fun onInactive() {
                resourceManage.removeListener(listener)
            }

        }
    }

    fun <T : IResource> getResources(targetResourceType: ResourceType<T>): LiveData<MutableList<T>> {
        return object : LiveData<MutableList<T>>() {

            val resourceManage = ResourceManage.getInstance()
            val listener = object : ResourceManage.Listener {
                override fun change(resourceTypes: MutableList<ResourceType<*>>) {
                }

                override fun <T2 : IResource> change(
                    resourceTypes: ResourceType<T2>,
                    resources: MutableList<T2>
                ) {
                    if (targetResourceType == resourceTypes) {
                        value = resources as MutableList<T>
                    }
                }

                override fun <T2 : IResource> change(
                    resource: IResource,
                    resourceActions: MutableList<IResourceAction<T2>>
                ) {

                }

            }

            override fun onActive() {
                value = resourceManage.getResources(targetResourceType)
                resourceManage.addListener(listener)
            }

            override fun onInactive() {
                resourceManage.removeListener(listener)
            }

        }
    }

    fun <T : IResource> getResourceActions(targetResource: T): LiveData<MutableList<IResourceAction<T>>> {
        return object : LiveData<MutableList<IResourceAction<T>>>() {

            val resourceManage = ResourceManage.getInstance()
            val listener = object : ResourceManage.Listener {
                override fun change(resourceTypes: MutableList<ResourceType<*>>) {
                }

                override fun <T2 : IResource> change(
                    resourceTypes: ResourceType<T2>,
                    resources: MutableList<T2>
                ) {

                }

                override fun <T2 : IResource> change(
                    resource: IResource,
                    resourceActions: MutableList<IResourceAction<T2>>
                ) {
                    if (targetResource == resource) {
                        value = resourceActions as MutableList<IResourceAction<T>>
                    }
                }

            }

            override fun onActive() {
                value = resourceManage.getResourceAction(targetResource)
                resourceManage.addListener(listener)
            }

            override fun onInactive() {
                resourceManage.removeListener(listener)
            }

        }
    }


}