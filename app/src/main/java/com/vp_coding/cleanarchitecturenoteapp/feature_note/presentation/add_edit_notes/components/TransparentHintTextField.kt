package com.vp_coding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_notes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextFieldff(
    text : String,
    hint: String,
    isHintVisible: Boolean,
    modifier: Modifier=Modifier,
    onValueChanged: (String) ->(Unit),
    textStyle: TextStyle=TextStyle(),
    singleLine: Boolean=false,
    onFocusChange:(FocusState)->(Unit)
){

    Box(modifier = Modifier){
        BasicTextField(
            value =text ,
            onValueChange =onValueChanged,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                },
            singleLine = singleLine
            )
        if(isHintVisible){
            Text(text = hint, style = textStyle, color = Color.DarkGray)
        }
    }

}