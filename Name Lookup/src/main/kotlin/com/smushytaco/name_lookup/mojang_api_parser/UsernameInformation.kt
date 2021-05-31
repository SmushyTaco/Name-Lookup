package com.smushytaco.name_lookup.mojang_api_parser
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.net.URL
import java.util.*
object UsernameInformation {
    val UUID.usernameHistory: List<UsernameStructure>?
        get() {
            return try {
                val string = URL("https://api.mojang.com/user/profiles/$this/names").readText()
                Json.decodeFromString(ListSerializer(UsernameStructure.serializer()), string)
            } catch (exception: Exception) {
                null
            }
        }
    val String.currentUsernameInformation: NameToUUIDStructure?
        get() {
            return try {
                val string = URL("https://api.mojang.com/users/profiles/minecraft/$this").readText()
                Json.decodeFromString(NameToUUIDStructure.serializer(), string)
            } catch (exception: Exception) {
                null
            }
        }
}