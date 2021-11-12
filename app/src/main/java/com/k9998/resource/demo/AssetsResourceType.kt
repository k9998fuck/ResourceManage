package com.k9998.resource.demo

import android.os.Parcelable
import com.k9998.resource.manage.ResourceType
import kotlinx.parcelize.Parcelize

@Parcelize
class AssetsResourceType : ResourceType<AssetsResource>("Assets资源", AssetsResource::class.java), Parcelable