package com.vp_coding.cleanarchitecturenoteapp.feature_note.presentation.notes

import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType

data class NotesState(
    val notes:List<Note> = emptyList(),
    var noteOrder: NoteOrder=NoteOrder.Date(OrderType.DescendingOrder),
    val isOrderSectionVisible:Boolean=false
)
