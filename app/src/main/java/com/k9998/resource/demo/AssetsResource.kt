package com.k9998.resource.demo

import android.os.Parcelable
import com.k9998.resource.manage.IResource
import kotlinx.parcelize.Parcelize

@Parcelize
data class AssetsResource(val path: String) : IResource, Parcelable {
    override fun getName(): String {
        return path
    }
}