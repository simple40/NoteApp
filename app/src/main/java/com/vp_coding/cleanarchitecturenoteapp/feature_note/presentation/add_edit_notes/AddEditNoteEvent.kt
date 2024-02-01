package com.vp_coding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_notes

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{

    data class EnteredTitle(val value:String) : AddEditNoteEvent()
    data class ChangeTitleFocusState(val focusState: FocusState) : AddEditNoteEvent()
    data class EnteredContent(val value:String) : AddEditNoteEvent()
    data class ChangeContentFocusState(val focusState: FocusState) : AddEditNoteEvent()
    data class ChangeColor(val color:Int) : AddEditNoteEvent()
    object SaveNote:AddEditNoteEvent()

}
