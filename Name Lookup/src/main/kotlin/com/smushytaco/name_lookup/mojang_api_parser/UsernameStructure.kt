package com.smushytaco.name_lookup.mojang_api_parser
import kotlinx.serialization.Serializable
@Serializable
data class UsernameStructure(val name: String, val changedToAt: Long? = null)