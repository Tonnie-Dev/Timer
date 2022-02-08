package com.uxstate.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uxstate.timer.ui.theme.TimerTheme
import kotlinx.coroutines.delay
import java.lang.Math.PI
import kotlin.math.cos
import kotlin.math.sin


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
    var size by remember { mutableStateOf(IntSize.Zero) }

    //current percentage of our time in relation to the total time
    var value by remember { mutableStateOf(initialValue) }
    var currentTime by remember { mutableStateOf(totalTime) }
    var isTimerRunning by remember { mutableStateOf(false) }



    //launchedEffect with 2 keys

    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning, block ={

        //loop to check if there is time remaing

        if (currentTime>0 && isTimerRunning){

            //delay coroutine
            delay(100L)

            //subtract 100L from the current time
            currentTime -= 100L

            //update the handle value
            value = currentTime/totalTime.toFloat()
        }

    } )
    //Box to put everything
    Box(modifier = modifier.onSizeChanged {
        //whenever the size changes we get a new size in form of IntSize
            newIntSize ->

        size = newIntSize
    }, contentAlignment = Alignment.Center) {


        Canvas(modifier = modifier, onDraw = {

            //1st Arc
            //draw an arc which is a part of a circle
            drawArc(
                color = inactiveBarColor,
                startAngle = -215f,
                sweepAngle = 250f,

                //stops connecting to the center which forms a pie
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            //2nd Arc

            drawArc(
                color = activeBarColor,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                useCenter = false,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                ),
                startAngle = -215f, sweepAngle = 250f * value
            )

            //center of the circle
            val center = Offset(size.width.toFloat() / 2, size.height.toFloat() / 2)
            val beta = (250f * value + 145f) * (180 / PI).toFloat()

            val radius = size.width / 2f

            // calculate unit circle side a & b
            val a = cos(beta) * radius
            val b = sin(beta) * radius

            //Draw Handle on the canvas
//Draws a sequence of points according to the given PointMode.
            drawPoints(
                points = listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Polygon,
                color = handleColor,
                strokeWidth = (strokeWidth * 3f).toPx(),
                cap = StrokeCap.Round
            )

        })

        //outside the canvas we draw a text to show time
        Text(
            text = (currentTime / 1000L).toString(),
            color = Color.White,
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold
        )

        //Draw Button

        Button(
            onClick = {

                      //timer needs a reset

                      if (currentTime <=0L){
currentTime = totalTime
                          isTimerRunning = true
                      }else{

                          //negate
                          isTimerRunning = !isTimerRunning
                      }
            },
            modifier = Modifier.align(Alignment.BottomCenter),

            //content color is the color of the enclosed texts
            colors = ButtonDefaults.buttonColors(
                backgroundColor =
                if (!isTimerRunning || currentTime <= 0) Color.Green else Color.Red
            )
        ) {

            Text(
                text = if (isTimerRunning && currentTime >= 0)
                    "Stop"
                else if (!isTimerRunning && currentTime > 0)
                    "Start"
                else "Restart"
            )
        }
    }


}

