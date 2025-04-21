package com.smushytaco.name_lookup.mojang_api_parser
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.net.URI
import java.util.*
object UsernameInformation {
    val UUID.usernameHistory: List<UsernameStructure>?
        get() {
            return try {
                val string = URI("https://api.mojang.com/user/profiles/$this/names").toURL().readText()
                Json.decodeFromString(ListSerializer(UsernameStructure.serializer()), string)
            } catch (_: Exception) {
                null
            }
        }
    val String.currentUsernameInformation: NameToUUIDStructure?
        get() {
            return try {
                val string = URI("https://api.mojang.com/users/profiles/minecraft/$this").toURL().readText()
                Json.decodeFromString(NameToUUIDStructure.serializer(), string)
            } catch (_: Exception) {
                null
            }
        }
}