package com.felipepalma14.stockAnalysisFlow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.felipepalma14.stockAnalysisFlow.core.Constants.SPLASH_TIME
import com.felipepalma14.stockAnalysisFlow.features.stock.ui.StockInfoActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, StockInfoActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }
}