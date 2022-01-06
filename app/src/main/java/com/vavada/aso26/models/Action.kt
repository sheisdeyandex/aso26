package com.vavada.aso26.models

import java.util.*

data class Action (
    val HasNext: Boolean,
    val ChangeNumber: String,
    val NextAction: String,
    val NextActionPattern: String,
    val ActualTitle: String,
    val ExpiredTitle: String,
    val Subtitle: String,
    val When: Long,
    val ActionIn: Int,
    val Authorized: Boolean
) {
    fun coolDownPassed(): Boolean {
        val now = System.currentTimeMillis()
        val passed = now - When

        return ActionIn * 1000 < passed
    }

    fun title(phone: String, accentColor: String): ColoredString { // TODO mapping!
        return if (coolDownPassed()) {
            val actionCalendar = Calendar.getInstance().apply { time = Date(When) }
            val actionTimeString = String.format(
                "%02d:%02d", actionCalendar.get(Calendar.HOUR_OF_DAY),
                actionCalendar.get(Calendar.MINUTE)
            )

            val text =
                String.format(Locale.getDefault(), ExpiredTitle, actionTimeString, phone)
            val hhIndex = text.indexOf(actionTimeString)
            val numberIndex = text.indexOf(phone)

            ColoredString(
                Text = text,
                Colors = listOf(
                    ColoredSpan(hhIndex, hhIndex + actionTimeString.length, "#FFCD04"),
                    ColoredSpan(numberIndex, numberIndex + phone.length, "#FFCD04")
                )
            )
        } else {
            val text = String.format(Locale.getDefault(), ActualTitle, phone)
            val numberIndex = text.indexOf(phone)

            ColoredString(
                Text = text,
                Colors = listOf(
                    ColoredSpan(numberIndex, numberIndex + phone.length, "#FFCD04")
                )
            )
        }
    }

    fun nextCaption(): String {
        return if (coolDownPassed()) NextAction else {
            val timePassed = (System.currentTimeMillis() - When) / 1000
            String.format(
                Locale.getDefault(), NextActionPattern,
                if (timePassed < 0) ActionIn else ActionIn - timePassed
            )
        }
    }
}