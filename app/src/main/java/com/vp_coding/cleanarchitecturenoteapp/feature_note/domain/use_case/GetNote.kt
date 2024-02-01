package com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository

class GetNote(val repository: NoteRepository) {
    suspend operator fun invoke(id:Int):Note?{
        return repository.getNoteById(id)
    }
}