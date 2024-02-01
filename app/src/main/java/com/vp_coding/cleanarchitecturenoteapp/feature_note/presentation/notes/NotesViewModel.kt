package com.vp_coding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCase
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
):ViewModel() {

    private val _state= mutableStateOf(NotesState())
    val state: State<NotesState> =_state

    private var recentlyDletedNote: Note?= null

    private var notesJob:Job ?=null

    init {
        getNotes(NoteOrder.Date(OrderType.DescendingOrder))
    }

    fun onEvent(event:NotesEvent){
        when(event){

            is NotesEvent.Order->{
                    if(state.value.noteOrder::class== event.noteOrder::class &&
                            state.value.noteOrder.orderType == event.noteOrder.orderType)
                    {
                        return
                    }
                _state.value.noteOrder=event.noteOrder
                getNotes(event.noteOrder)
            }

            is NotesEvent.DeleteNote->{
                viewModelScope.launch {
                    noteUseCase.deleteNote(event.note)
                    recentlyDletedNote=event.note
                }
            }

            is NotesEvent.RestoreNote->{
                viewModelScope.launch {
                    noteUseCase.addNote(recentlyDletedNote ?:return@launch)
                    recentlyDletedNote=null
                }
            }

            is NotesEvent.ToggleOrderSection->{
                _state.value=state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }

        }
    }

    fun getNotes(noteOrder: NoteOrder){

        notesJob?.cancel()
        notesJob=noteUseCase.getNotes(noteOrder)
            .onEach { notes->
                _state.value=state.value.copy(
                    notes=notes,
                    noteOrder=noteOrder
                )
            }.launchIn(viewModelScope)
    }
}