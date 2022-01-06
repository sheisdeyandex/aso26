package com.vavada.aso26.models

import androidx.annotation.Keep

@Keep
data class ColoredString(
    val Text: String,
    val Colors: List<ColoredSpan>
)

@Keep
data class ColoredSpan(
    val From: Int,
    val To: Int,
    val Color: String
)