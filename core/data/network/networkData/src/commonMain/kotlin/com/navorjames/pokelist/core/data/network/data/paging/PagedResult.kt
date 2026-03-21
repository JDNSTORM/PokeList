package com.navorjames.pokelist.core.data.network.data.paging

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedResult<T: Any>(
    @SerialName("count")
    val count: Int = 0,
    @SerialName("next")
    val nextUrl: String? = null,
    @SerialName("previous")
    val previousUrl: String? = null,
    @SerialName("results")
    val items: List<T> = emptyList()
)