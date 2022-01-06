package com.vavada.aso26.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.vavada.aso26.R
import com.vavada.aso26.UiChangeInterface
import com.vavada.aso26.ui.fragment.SplashFragment


class MainActivity : AppCompatActivity(), UiChangeInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        show(SplashFragment(this, this))
hideSystemUI()
    }
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun show(fragment: Fragment) {
      supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()

    }
}