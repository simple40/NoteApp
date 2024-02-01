package com.vp_coding.cleanarchitecturenoteapp.feature_note.data.repository

import com.vp_coding.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDao
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao):NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNoes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}