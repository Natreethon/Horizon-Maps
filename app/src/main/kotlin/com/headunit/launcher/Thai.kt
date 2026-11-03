package com.headunit.launcher

import java.util.Locale

/** Thai at the presentation boundary only: never translate keys, URLs, or stored user data. */
object Thai {
    val locale: Locale = Locale.forLanguageTag("th-TH")
    const val tomtomLanguage = "th-TH"
    const val mapboxLanguage = "th"

    fun text(value: String): String {
        if (value.isBlank()) return value
        val trimmed = value.trim()
        val translated = ThaiCatalog.entries[trimmed.lowercase(Locale.ROOT)]
        if (translated != null) {
            val start = value.indexOf(trimmed)
            return value.substring(0, start) + translated + value.substring(start + trimmed.length)
        }
        // Composite cards still keep names and unrecognised service text as supplied.
        if ('\n' in value) return value.split('\n').joinToString("\n") { text(it) }
        if (" · " in value) return value.split(" · ").joinToString(" · ") { text(it) }
        return value
    }

    fun country(code: String): String = Locale("", code).getDisplayCountry(locale).ifBlank { code }

    fun voiceAvailable(result: Int?): Boolean = result != null && result >= 0
}
