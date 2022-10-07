package com.compose.cocktaildakk_compose.ui.onboarding

import android.view.LayoutInflater
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.ImageWithBackground
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.shawnlin.numberpicker.NumberPicker

@Composable
fun OnboardAgeScreen(navController: NavController = rememberNavController()) {
  ImageWithBackground(
    modifier = Modifier
      .fillMaxSize()
      .blur(20.dp),
    backgroundDrawableResId = R.drawable.img_onboard_back,
    contentDescription = "Img Onboard Back"
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
    ) {
      Column(
        modifier = Modifier
          .fillMaxHeight(0.3f)
          .padding(40.dp, 0.dp)
      ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
          text = "나이를\n알려 주세요.",
          fontSize = 36.sp,
          modifier = Modifier,
          fontWeight = FontWeight.Bold
        )
      }
      NumberPicker(
        modifier = Modifier
          .padding(0.dp, 24.dp)
          .fillMaxHeight(0.7f)
      )
      Surface(
        modifier = Modifier
          .align(CenterHorizontally)
          .background(color = Color.Transparent)
          .clickable {
            navController.navigate("onboard_sex")
          },
        color = Color.Transparent
      ) {
        Text(
          text = "다음",
          modifier = Modifier
            .border(
              brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
              width = 1.dp,
              shape = CircleShape
            )
            .padding(20.dp, 10.dp),
        )
      }
      Spacer(modifier = Modifier.fillMaxHeight(0.2f))
    }
  }
}

@Composable
fun NumberPicker(modifier: Modifier) {
  AndroidView(
    modifier = modifier.fillMaxWidth(),
    factory = { context ->
      val view = LayoutInflater.from(context).inflate(R.layout.number_picker, null)
      val numberPicker = view.findViewById<NumberPicker>(R.id.age_picker)
      numberPicker.apply {
        minValue = 20
        maxValue = 45
        value = 20
        textSize = 55f
        textColor = context.getColor(R.color.white)
        selectedTextSize = 120f
        wheelItemCount = 8
        selectedTextColor = context.getColor(R.color.lightblue)
        lineSpacingMultiplier = 50f
        setItemSpacing(5)
        isFadingEdgeEnabled = true
        wrapSelectorWheel = true
      }

      numberPicker.setOnValueChangedListener(object :
        android.widget.NumberPicker.OnValueChangeListener,
        NumberPicker.OnValueChangeListener {
        override fun onValueChange(picker: android.widget.NumberPicker?, oldVal: Int, newVal: Int) {

        }

        override fun onValueChange(
          picker: NumberPicker?,
          oldVal: Int,
          newVal: Int
        ) {
        }
      })
      numberPicker
    }
  )
}

@Preview
@Composable
fun PreviewOnboardAgeScreen() {
  OnboardAgeScreen()
}