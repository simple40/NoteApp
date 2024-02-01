package com.vp_coding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_notes.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.vp_coding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_notes.AddEditNoteEvent
import com.vp_coding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_notes.AddEditNoteViewmodel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navControllet:NavController,
    noteColor: Int,
    viewmodel: AddEditNoteViewmodel= hiltViewModel()
) {

    val titleState=viewmodel.noteTitle.value
    val contentState=viewmodel.noteContent.value
    val scaffoldState= rememberScaffoldState()

    val noteBackgroundAnimatable= remember{
        Animatable(
            Color(if(noteColor != -1) noteColor
                else viewmodel.noteColor.value)
        )
    }

    val scope= rememberCoroutineScope()

    LaunchedEffect(true){
        viewmodel.eventFlow.collectLatest {event->
            when(event){
                is AddEditNoteViewmodel.UIEvent.ShowSnackbar->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditNoteViewmodel.UIEvent.SaveNote->{
                    navControllet.navigateUp()
                }
            }

        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewmodel.onEvent(AddEditNoteEvent.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save Note" )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Note.noteColor.forEach{color ->
                    val colorInt=color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                shape = CircleShape,
                                color = if (viewmodel.noteColor.value == colorInt)
                                    Color.Black
                                else
                                    Color.Transparent
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = color,
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewmodel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                    )

                }


            }
            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextFieldff(
                text = titleState.text,
                hint =titleState.hint ,
                isHintVisible = titleState.isHintVisible,
                onValueChanged = {
                                 viewmodel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewmodel.onEvent(AddEditNoteEvent.ChangeTitleFocusState(it))
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextFieldff(
                text = contentState.text,
                hint =contentState.hint ,
                isHintVisible = contentState.isHintVisible,
                onValueChanged = {
                    viewmodel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewmodel.onEvent(AddEditNoteEvent.ChangeContentFocusState(it))
                },
                modifier = Modifier.fillMaxHeight(),
                textStyle = MaterialTheme.typography.body1
            )
        }
    }

}