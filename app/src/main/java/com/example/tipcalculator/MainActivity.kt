package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat
import androidx.compose.runtime.mutableStateOf as mutableStateOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen(){
    var amount by remember {
        mutableStateOf("")
    }
    var tipPercent by remember {
        mutableStateOf("")
    }
    var amount1 = amount.toDoubleOrNull() ?: 0.0
    var tip = calculateTip(amount1,tipPercent.toDoubleOrNull()?:15.0)
    Column(modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.heading),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldGenerator(value = amount, onValueChanged = {amount = it})
        TextFieldGenerator2(value = tipPercent, onValueChanged = {tipPercent = it})
        Text(text = tip.toString(), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TextFieldGenerator(value: String , onValueChanged:(String)->Unit){


    TextField(
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        label = { Text(text = stringResource(id = R.string.texfieldHeading))},
        modifier = Modifier.fillMaxWidth()

    )
}

@Composable
fun TextFieldGenerator2(value: String , onValueChanged:(String)->Unit){


    TextField(
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        label = { Text(text = stringResource(id = R.string.TipHeading))},
        modifier = Modifier.fillMaxWidth()

    )
}

private fun calculateTip(amount:Double,tip:Double):String{
    val finalAmount = amount * (tip/100.0)
    return NumberFormat.getCurrencyInstance().format(finalAmount)

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        HomeScreen()
    }
}