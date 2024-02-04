package com.example.videojuegoandroid_juliangarrido

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.videojuegoandroid_juliangarrido.entities.Bola
import com.example.videojuegoandroid_juliangarrido.tablero.Tablero
import com.example.videojuegoandroid_juliangarrido.tablero.TableroView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el tamaño de la pantalla
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val tablero = Tablero(screenWidth, screenHeight)  // Crear un <link>tablero</link> con el ancho y alto de la pantalla


        val bola1 = Bola(100, 100, 5, 3, "#FF0000", 20f)  // Crear una bola roja
        val bola2 = Bola(200, 200, 3, 5, "#0000FF", 20f)  // Crear una bola azul

        tablero.agregarBola(bola1)
        tablero.agregarBola(bola2)

        val tableroView = TableroView(this, tablero)
        setContentView(tableroView)  // Establecer la vista personalizada como el contenido de la actividad

        tableroView.setOnTouchListener { _, event ->
            tableroView.onTouchEvent(event)  // Delegar el evento táctil a TableroView
        }

        // Configurar un temporizador para actualizar el tablero y redibujar las bolas
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                tablero.actualizar()  // Actualizar la posición de las bolas
                runOnUiThread { tableroView.invalidate() }  // Redibujar la vista
            }
        }
        timer.schedule(task, 0, 1000 / 60)  // Actualizar aproximadamente 60 veces por segundo
    }
}