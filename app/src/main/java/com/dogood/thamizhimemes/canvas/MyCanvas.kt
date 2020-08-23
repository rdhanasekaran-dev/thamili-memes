package com.dogood.thamizhimemes.canvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration

class MyCanvas(context: Context):View(context) {

    private var path = Path()
    var paths= arrayListOf<Path>()
    var undonePaths= arrayListOf<Path>()

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    private val paint = Paint().apply {
        color = Color.YELLOW
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = 12f // default: Hairline-width (really thin)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for(path in paths){
            canvas!!.drawPath(path,paint)
        }

        canvas!!.drawPath(path,paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(motionTouchEventX,motionTouchEventY)
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(motionTouchEventX,motionTouchEventY)
            }
            MotionEvent.ACTION_UP -> {
                paths.add(path)
                path=Path()
            }
        }

        invalidate()
        return true
    }

    fun clickUndo(){
        if(paths.size>0){
            undonePaths.add(paths.removeAt(paths.size-1))
            invalidate()
        }else{

        }
    }

    fun clickRedo(){
        if(undonePaths.size>0){
            paths.add(undonePaths.removeAt(undonePaths.size-1))
            invalidate()
        }else{

        }
    }


}