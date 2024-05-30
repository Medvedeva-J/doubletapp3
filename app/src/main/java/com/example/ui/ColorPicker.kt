package com.example.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRadioButton
import com.example.doubletapp3.R
import com.example.util.dp

class ColorPicker(
    private val context: Context,

    private val selectedColorSample: View,
    private val colorPickerView: View,
    private val colorInfo: View
) {

    private val colors: List<Int> = (0 until SQUARES).map{
        Color.HSVToColor(floatArrayOf(
            360f / SQUARES * (it + 1) - 360f / SQUARES / 2f, 0.9f, 1f)
        )
    }
    var selectedColor: Int? = null

    companion object {
        private const val SQUARES = 16
    }

    init {
        createGradient()
        for (color in colors) {
            (colorPickerView as RadioGroup).addView(createSquares(color.toString()))
        }
    }

    private fun createSquares(color: String): AppCompatRadioButton {
        val radioButton = AppCompatRadioButton(context)
        radioButton.setBackgroundResource(R.drawable.color_picker_selector)
        radioButton.buttonDrawable = null
        radioButton.layoutParams = getParams()
        radioButton.text = color
        radioButton.setTextColor(android.R.color.transparent.toInt())
        radioButton.setOnClickListener{
            changeColorSample(color.toInt())
        }
        return radioButton
    }

    private fun createGradient(): GradientDrawable {
        val colors = getColorArray()
        val selectorBg: GradientDrawable =
            colorPickerView.background as GradientDrawable

        selectorBg.gradientType = GradientDrawable.LINEAR_GRADIENT
        selectorBg.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        selectorBg.colors = colors

        return selectorBg
    }

    fun changeColorSample(color: Int){
        selectedColor = color
        val backgroundHabitColor: GradientDrawable =
            selectedColorSample.background as GradientDrawable
        backgroundHabitColor.setColor(color)
        getColorInfo()
    }

    private fun getColorArray(): IntArray{
        return (0..360).step(60)
            .map { it.toFloat() }
            .map { floatArrayOf(it, 0.9f, 1f) }
            .map { Color.HSVToColor(it) }
            .toIntArray()
    }

    private fun getColorInfo(){
        var hsvColor = "(0, 0, 0)"
        var rgbColor = "(0, 0, 0)"

        if (selectedColor != null) {
            val r = Color.red(selectedColor!!)
            val g = Color.green(selectedColor!!)
            val b = Color.blue(selectedColor!!)

            val hsv = FloatArray(3)
            Color.colorToHSV(selectedColor!!, hsv)
            val h = hsv[0].toInt()
            val s = (hsv[1] * 100).toInt()
            val v = (hsv[2] * 100).toInt()
            rgbColor = "($r, $g, $b)"
            hsvColor = "($h, $s, $v)"
        }

        val editText = colorInfo as TextView
        editText.text = "HSV: $hsvColor\nRGB: $rgbColor"
    }


    private fun getParams(): LinearLayout.LayoutParams{
        val params = LinearLayout.LayoutParams(44.dp, 44.dp)
        params.topMargin = 14.dp
        params.bottomMargin = 14.dp
        params.marginEnd = 7.dp
        params.marginStart = 7.dp
        return params
    }
}