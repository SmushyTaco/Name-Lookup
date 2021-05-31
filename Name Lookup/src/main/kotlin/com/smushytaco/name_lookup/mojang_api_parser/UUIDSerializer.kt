package com.smushytaco.name_lookup.mojang_api_parser
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*
object UUIDSerializer : KSerializer<UUID> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): UUID {
        val stringBuilder = StringBuilder()
        val string = decoder.decodeString()
        for (i in string.indices) {
            stringBuilder.append(string[i])
            if (i == 7 || i == 11 || i == 15 || i == 19) stringBuilder.append('-')
        }
        return UUID.fromString(stringBuilder.toString())
    }
    override fun serialize(encoder: Encoder, value: UUID) { encoder.encodeString(value.toString()) }
}