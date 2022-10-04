package com.compose.cocktaildakk_compose.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
  modifier: Modifier = Modifier,
  leadingIcon: @Composable() (() -> Unit)? = null,
  trailingIcon: @Composable() (() -> Unit)? = null,
  placeholderText: String = "검색어를 입력해주세요.",
  fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
  focusRequest: FocusRequester? = null,
  keyboardOptions: KeyboardOptions? = null,
  keyboardActions: KeyboardActions? = null,
  value: TextFieldValue,
  onvalueChanged: (TextFieldValue) -> Unit
) {
  BasicTextField(modifier = modifier
    .focusRequester(focusRequest ?: return),
    value = value,
    onValueChange = {
      if (it.selection.length <= 25) onvalueChanged(it)
    },
    singleLine = true,
    cursorBrush = SolidColor(MaterialTheme.colors.primary),
    textStyle = LocalTextStyle.current.copy(
      color = Color.Black,
      fontSize = fontSize,
      fontWeight = FontWeight.Bold
    ),
    keyboardOptions = keyboardOptions ?: KeyboardOptions(),
    keyboardActions = keyboardActions ?: KeyboardActions(),
    decorationBox = { innerTextField ->
      Row(
        modifier.padding(20.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (leadingIcon != null) leadingIcon()
        Box(Modifier.weight(1f)) {
          if (value.text.isEmpty()) Text(
            placeholderText,
            style = LocalTextStyle.current.copy(
              color = Color.Black.copy(alpha = 0.3f),
              fontSize = fontSize,
            )
          )
          innerTextField()
        }
        if (trailingIcon != null) trailingIcon()
      }
    }
  )
}