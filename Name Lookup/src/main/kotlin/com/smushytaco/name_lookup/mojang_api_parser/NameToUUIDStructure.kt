package com.smushytaco.name_lookup.mojang_api_parser
import kotlinx.serialization.Serializable
import java.util.*
@Serializable
data class NameToUUIDStructure(val name: String, @Serializable(with = UUIDSerializer::class) val id: UUID)