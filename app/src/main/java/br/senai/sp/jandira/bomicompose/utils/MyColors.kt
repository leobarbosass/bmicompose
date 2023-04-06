package br.senai.sp.jandira.bomicompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import br.senai.sp.jandira.bomicompose.R

fun getColor(bmi: Double): Color {
    return if (bmi <= 18.5){
        Color.Cyan
    }
    else if (bmi >= 18.5 && bmi <= 25) {
        Color.Green
    }
    else if (bmi >= 25.1 && bmi <= 29.9) {
        Color(250,140,0)
    }
    else{
        Color.Red
    }
}

@Composable
fun getTags(bmi: Double): String {
    return if (bmi <= 18.5){
        "${stringResource(id = R.string.weight_under)}"
    }
    else if (bmi >= 18.5 && bmi <= 25) {
        "${stringResource(id = R.string.weight_ideal)}"
    }
    else if (bmi >= 25.1 && bmi <= 29.9) {
        "${stringResource(id = R.string.weight_overweight)}"
    }
    else{
        "${stringResource(id = R.string.weight_Obesity)}"
    }
}