package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import kotlin.math.round
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
fun HomeScreen() {
    val focus = LocalFocusManager.current
    var amount by remember {
        mutableStateOf("")
    }
    var tipPercent by remember {
        mutableStateOf("")
    }
    var roundUp by remember{
        mutableStateOf(false)
    }
    val amount1 = amount.toDoubleOrNull() ?: 0.0

    var tip = calculateTip(amount1, tipPercent.toDoubleOrNull() ?: 15.0)
    if(roundUp){
        tip = round(tip)
    }
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.heading),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldGenerator(
            R.string.texfieldHeading,
            value = amount,
            onValueChanged = { amount = it },
            KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            KeyAction = KeyboardActions (onNext ={ focus.moveFocus(FocusDirection.Down)} )
        )
        TextFieldGenerator(
            R.string.TipHeading,
            value = tipPercent,
            onValueChanged = { tipPercent = it },
            KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            KeyAction = KeyboardActions (onDone ={ focus.clearFocus(true)} )
        )
        SwitchProvider(roundUp = roundUp, onclickchange = {roundUp=it})
        Text(text = "Rs.$tip", fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TextFieldGenerator(
    @StringRes label: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    KeyBoard: KeyboardOptions,
    KeyAction:KeyboardActions
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = KeyBoard,
        singleLine = true,
        label = { Text(text = stringResource(id = label)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardActions = KeyAction
    )
}

@Composable
fun SwitchProvider(
    roundUp : Boolean,
    onclickchange:(Boolean)->Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = stringResource(id = R.string.Round))
        Switch(checked = roundUp, onCheckedChange = onclickchange, modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.End))
    }
}


private fun calculateTip(amount: Double, tip: Double): Double {
    return amount * (tip / 100.0)

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        HomeScreen()
    }
}