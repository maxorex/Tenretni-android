package com.example.tenretni.core.helpers

import com.example.tenretni.core.Constants
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object DateHelper {

    fun formatUTCToSystemDefault(dateStringISO: String) : String {

        try {

            val dateInstant = Instant.parse(dateStringISO).atZone(ZoneOffset.UTC)
            val formatter = DateTimeFormatter.ofPattern(Constants.ISO_DATETIME_PATTERN_WITHOUT_SECONDS)

            val utcDateTime = LocalDateTime.parse(formatter.format(dateInstant))
            val currentZoneOffset = ZoneId.systemDefault().rules.getOffset(utcDateTime.toJavaLocalDateTime())

            val dateTimeWithOffset = OffsetDateTime.of(utcDateTime.toJavaLocalDateTime(), ZoneOffset.UTC)
                .withOffsetSameInstant(currentZoneOffset)

            return DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN).format(dateTimeWithOffset)
        } catch (_ : Exception) {
            throw IllegalArgumentException()
        }

    }

}


