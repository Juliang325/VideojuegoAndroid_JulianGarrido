package com.example.videojuegoandroid_juliangarrido.tablero

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import com.example.videojuegoandroid_juliangarrido.entities.Bola

class TableroView(context: Context, val tablero: Tablero) : View(context) {

    private val paint = Paint()

    // Sobreescribir el método onSizeChanged para ajustar el tamaño del tablero
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        tablero.ancho = w  // Actualizar el ancho del tablero con el ancho de la pantalla
        tablero.alto = h  // Actualizar el alto del tablero con el alto de la pantalla
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (bola in tablero.bolas) {
            paint.color = Color.parseColor(bola.color)
            canvas.drawCircle(bola.x.toFloat(), bola.y.toFloat(), bola.radio.toFloat(), paint)  // Dibujar la bola en el lienzo
        }
    }

    private val runnable = object : Runnable {
        override fun run() {
            tablero.actualizar()  // Actualizar la posición de las bolas
            tablero.manejarColisiones()  // Manejar las colisiones entre bolas

            // Solicitar un redibujado de la vista
            invalidate()

            postDelayed(this, 10)  // Programar la próxima actualización
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        post(runnable)  // Iniciar el ciclo de actualización
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(runnable)  // Detener el ciclo de actualización al salir de la vista
        super.onDetachedFromWindow()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            // Obtener la posición del toque
            val x = event.x.toInt()
            val y = event.y.toInt()

            // Crear una bola azul en la posición del toque y agregarla al tablero
            val nuevaBolaAzul = Bola(x, y, 3, 5, "#0000FF", 20f)  // Crear una bola azul en la posición del toque
            tablero.agregarBola(nuevaBolaAzul)  // Agregar la nueva bola al tablero

            // Solicitar un redibujado de la vista
            invalidate()
        }
        return true
    }


}