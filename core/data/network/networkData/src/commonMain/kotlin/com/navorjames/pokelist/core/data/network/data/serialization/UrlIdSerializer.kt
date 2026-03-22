package com.navorjames.pokelist.core.data.network.data.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A custom [KSerializer] for [Int] that extracts an integer ID from a URL string.
 *
 * This serializer is specifically designed for APIs (like PokeAPI) where resource IDs
 * are provided as URLs. It parses the URL by taking the last path segment and
 * converting it to an integer.
 *
 * Example: `https://pokeapi.co/api/v2/pokemon/25/` becomes `25`.
 */
object UrlIdSerializer: KSerializer<Int> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "UrlId",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: Int) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Int = decoder.decodeString()
        .trimEnd('/')
        .substringAfterLast('/')
        .toInt()
}