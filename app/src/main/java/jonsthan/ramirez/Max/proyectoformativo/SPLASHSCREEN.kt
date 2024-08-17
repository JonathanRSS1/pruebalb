package jonsthan.ramirez.Max.proyectoformativo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SPLASHSCREEN : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splashscreen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val Logo = findViewById<ImageView>(R.id.imgLogo)
        Logo.animate().alpha(1f).setDuration(1000).startDelay = 2610

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,LOGIN::class.java))
            finish()
        }, 1000)

        GlobalScope.launch (Dispatchers.IO) {
            delay(3000)
            val mainAct = Intent(this@SPLASHSCREEN, LOGIN::class.java)
            startActivity(mainAct)
        }
    }
}