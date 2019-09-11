package com.example.semaforapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val colors = arrayOf(
        R.color.gris,
        R.color.Rojo,
        R.color.Verde,
        R.color.Amarillo
    )
    
    var container: View? = null
    var startBtn: Button? = null
    var textView: TextView? = null
    var started = false
    val handler = Handler()
    var currentLights = Start

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container = findViewById(R.id.bg)
        startBtn = findViewById(R.id.btn_get_started)
        textView = findViewById(R.id.textView)
        startBtn!!.setOnClickListener {
            if (!started) {
                startLights()
            } else {
                stopLights()
            }
        }
    }

    companion object {
        const val Start = 0
        const val Rojo = 1
        const val Amarillo = 2
        const val Verde = 3
        const val FIVE_SECONDS = 5000L
    }

    fun setcolor(colorIndex: Int) {
        when (colorIndex) {
            Start -> Log.d("SEMAFORO", "GRIS")
            Rojo -> Log.d("SEMAFORO", "Rojo")
            Amarillo -> Log.d("SEMAFORO", "Amarillo")
            Verde -> Log.d("SEMAFORO", "Verde")
        }
        val resourceColor = colors[colorIndex]
        container!!.setBackgroundColor(ContextCompat.getColor(this, resourceColor))
    }

    fun settext(colorIndex: Int) {
        val text = when (colorIndex) {
            Rojo -> "Alto"
            Verde -> "Precaucion"
            Amarillo -> "Siga"
            else -> "Semaforo"
        }
        textView!!.text = text

    }
    private fun changeLights(color: Int){
        if(started){
            startBtn!!.text = "Parar"
            settext(color)
            setcolor(color)
            currentLights = if(currentLights < Verde) currentLights +1 else Rojo
            handler.postDelayed({
                runOnUiThread {
                    changeLights(currentLights)
                }
            }, FIVE_SECONDS)
        }
    }
    private fun startLights(){
        started = true
        currentLights = Rojo
        changeLights(currentLights)
    }
    private fun stopLights(){
        started = false
        startBtn!!.text = "Iniciar"
        setcolor(Start)
        settext(Start)
    }
}
