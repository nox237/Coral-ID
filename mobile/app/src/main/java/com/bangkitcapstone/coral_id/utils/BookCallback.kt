package com.bangkitcapstone.coral_id.utils

import com.bangkitcapstone.coral_id.data.source.local.entity.CoralsEntity
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse

interface BookCallback {
    fun onItemClicked(coral: CoralsEntity)
}