package br.senai.sp.jandira.bomicompose

import android.os.Bundle
import android.widget.Button
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.bomicompose.ui.theme.BMIComposeTheme

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
fun BMICalculator(){

    var weightState by rememberSaveable() {
        mutableStateOf("")
    }
    var heightState by rememberSaveable() {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //Header
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.bmi) ,
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
                    weightState = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )
            Text(
                text = stringResource(id = R.string.height),
                modifier = Modifier.padding(top = 8.dp)
            )
            OutlinedTextField(
                value = heightState, onValueChange = {
                    heightState = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )
            Button(
                onClick = { /*TODO*/ },
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
        Column() {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.your_score),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "0.00",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Congratulations, your weight is ideal!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(Color(40, 20, 200))
                        ) {
                            Text(
                                text = stringResource(id = R.string.reset)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(Color(40, 20, 200))
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

