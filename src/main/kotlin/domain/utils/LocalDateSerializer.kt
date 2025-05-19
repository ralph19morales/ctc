package domain.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

object LocalDateSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toString()) // Converts LocalDate to ISO-8601 string
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString()) // Parses ISO-8601 string to LocalDate
    }
}
