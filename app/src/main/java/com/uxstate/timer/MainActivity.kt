package com.uxstate.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.uxstate.timer.ui.theme.TimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerTheme {

            }
        }
    }
}


@Composable
fun Timer(
    totalTime: Long,
    handleColor: Color,
    activeBarColor: Color,
    inactiveBarColor: Color,
    modifier: Modifier = Modifier,
    initialValue: Float = 0f,
    strokeWidth: Dp = 5.dp
) {


    //STATES
    //Constructs an IntSize from width and height Int values
    var size by remember{ mutableStateOf(IntSize.Zero)}
    var value by remember{ mutableStateOf(initialValue)}
    var currentTime by remember{ mutableStateOf(totalTime)}
    var isTimerRunning by remember{ mutableStateOf(false)}
}

