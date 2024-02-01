package com.vp_coding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewmodel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _noteTitle= mutableStateOf(NoteTextFieldState(hint = "Enter the title..."))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent= mutableStateOf(NoteTextFieldState(hint = "Enter the content..."))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor= mutableStateOf(Note.noteColor.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow= MutableSharedFlow<UIEvent>()
    val eventFlow=_eventFlow.asSharedFlow()

    private var currentNoteId : Int?=null

    init {
        savedStateHandle.get<Int>("noteId")?.let{noteId->
            if(noteId!=-1){
                viewModelScope.launch {
                    noteUseCase.getNote(noteId)?.also {note->
                        _noteTitle.value=noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value=noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value=note.color
                        println("the color of not is ${noteColor.value}")
                        currentNoteId=note.id
                    }


                }

            }

        }
    }

    fun onEvent(event: AddEditNoteEvent){
        when (event){

            is AddEditNoteEvent.EnteredTitle->{
                _noteTitle.value=noteTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTitleFocusState-> {
                _noteTitle.value=noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent ->{
                _noteContent.value=noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeContentFocusState -> {
                _noteContent.value=noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value=event.color
            }

            is AddEditNoteEvent.SaveNote ->{
                viewModelScope.launch {
                    try {
                        noteUseCase.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                color = noteColor.value,
                                timeStamp = System.currentTimeMillis(),
                                id=currentNoteId
                            )
                        )
                        _eventFlow.emit(UIEvent.SaveNote)
                    }
                    catch (e:InvalidNoteException){
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(message = e.message?: "Unable to save note")
                        )
                    }
                }
            }


        }
    }


    sealed class UIEvent{
        data class ShowSnackbar(val message:String) : UIEvent()
        object SaveNote : UIEvent()
    }

}


