package com.dfavilav.zararickmorty.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)
