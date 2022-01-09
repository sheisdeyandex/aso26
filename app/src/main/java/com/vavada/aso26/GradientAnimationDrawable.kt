package com.vavada.aso26

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.GradientDrawable

class GradientAnimationDrawable(
    start: Int = Color.parseColor("A201B0"),
    center: Int = Color.parseColor("71C7EB"),
    end: Int = Color.parseColor("#71C7EB"),
    frameDuration: Int = 1000,
    enterFadeDuration: Int = 0,
    exitFadeDuration: Int = 1000
) : AnimationDrawable() {

    private val gradientStart = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, intArrayOf(start, center, end))
        .apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.LINEAR_GRADIENT
        }

    private val gradientCenter = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, intArrayOf(center, end, start))
        .apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.LINEAR_GRADIENT
        }

    private val gradientEnd = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, intArrayOf(end, start, center))
        .apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.LINEAR_GRADIENT
        }

    init {
        addFrame(gradientStart, frameDuration)
        addFrame(gradientCenter, frameDuration)
        addFrame(gradientEnd, frameDuration)
        setEnterFadeDuration(enterFadeDuration)
        setExitFadeDuration(exitFadeDuration)
        isOneShot = false
    }

}
