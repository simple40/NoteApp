package com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository

class DeleteNote(val repository: NoteRepository) {

    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}