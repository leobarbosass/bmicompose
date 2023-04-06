package br.senai.sp.jandira.bomicompose

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.bomicompose.ui.theme.BMIComposeTheme
import br.senai.sp.jandira.bomicompose.utils.bmiCalculate
import br.senai.sp.jandira.bomicompose.utils.getColor
import br.senai.sp.jandira.bomicompose.utils.getTags
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMIComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BMICalculator()
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BMICalculator() {


    var weightState by rememberSaveable() {
        mutableStateOf("")
    }
    var heightState by rememberSaveable() {
        mutableStateOf("")
    }

    var expandState by remember {
        mutableStateOf(false)
    }

    var bmiScoreState by remember {
        mutableStateOf(0.0)
    }

    var isWeightError by remember {
        mutableStateOf(false)
    }

    var isHeightError by remember {
        mutableStateOf(false)
    }

    // Objeto que controla a ordem em q o usuario digita
    val weightFocusRequester = FocusRequester()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        //Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.bmi),
                contentDescription = "bmi icon",
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = stringResource(id = R.string.app_title),
                color = Color.DarkGray,
                fontSize = 32.sp,
                letterSpacing = 2.sp
            )
        }
        //Form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 32.dp, end = 32.dp)
        ) {
            Text(
                text = stringResource(id = R.string.weight)
            )
            OutlinedTextField(
                value = weightState, onValueChange = {
                    var lastChar = if (it.length == 0) {
                        isWeightError = true
                        it
                    }
                    else {
                        it.get(it.length - 1)
                        isWeightError = false
                    }

                    var newValue = if (lastChar == '.' || lastChar == ',')
                        it.dropLast(1)
                    else
                        it
                    weightState = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .focusRequester(weightFocusRequester),
                trailingIcon = {
                    if(isWeightError) Icon(imageVector = Icons.Default.Warning, contentDescription = "")
                },
                isError = isWeightError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )
            //se o weight for true aparece o texto na tela
            if(isWeightError){
                Text(
                    text = stringResource(id = R.string.weight_error),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                    textAlign = TextAlign.End
                )
            }
            
            Text(
                text = stringResource(id = R.string.height),
                modifier = Modifier.padding(top = 8.dp)
            )
            OutlinedTextField(
                value = heightState, onValueChange = {
                    var lastChar = if (it.length == 0) {
                        isHeightError = true
                        it
                    }
                    else {
                        it.get(it.length - 1)
                        isHeightError = false
                    }

                    var newValue = if (lastChar == '.' || lastChar == ',')
                        it.dropLast(1)
                    else
                        it
                    heightState = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                trailingIcon = {
                    if(isHeightError) Icon(imageVector = Icons.Default.Warning, contentDescription = "")
                },
                isError = isHeightError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )
            if(isHeightError){
                Text(
                    text = stringResource(id = R.string.height_error),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                    textAlign = TextAlign.End
                )
            }
            Button(
                onClick = {
                    isWeightError = weightState.length == 0
                    isHeightError = heightState.length == 0
                    if(isHeightError == false && isWeightError == false) {
                        bmiScoreState = bmiCalculate(weightState.toInt(), heightState.toDouble())
                        expandState = true
                    }
                },
                modifier = Modifier
                    .padding(top = 26.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(Color(40, 160, 50)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.calculate),
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
        }

        //Footer
        AnimatedVisibility(
            visible = expandState,
            enter = slideIn(tween(durationMillis = 700)) {
                IntOffset(0, it.height)
            },
            exit = slideOut(tween(durationMillis = 700)) {
                IntOffset(0, it.height)
            }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight(1f)
                    .padding(top = 40.dp),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                backgroundColor = getColor(bmiScoreState),

                ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        text = stringResource(id = R.string.your_score),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        //format é para declarar apenas 2 decimais e ja retorna o bmiScore como string
                        text = String.format("%.2f", bmiScoreState),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = getTags(bmiScoreState),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                expandState = false
                                weightState = ""
                                heightState = ""
                                weightFocusRequester.requestFocus()
                            },
                            colors = ButtonDefaults.buttonColors(Color.White)
                        ) {
                            Text(
                                text = stringResource(id = R.string.reset)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(Color.White)
                        ) {
                            Text(
                                text = stringResource(id = R.string.share)
                            )
                        }
                    }
                }
            }
        }
    }
}

