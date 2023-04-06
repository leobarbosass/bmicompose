package br.senai.sp.jandira.bomicompose.utils

import androidx.compose.ui.graphics.Color
import kotlin.math.pow

fun bmiCalculate(weight: Int, height: Double): Double {
    return weight / (height / 100).pow(2)
}
