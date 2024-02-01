package com.vp_coding.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import com.vp_coding.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDatabase
import com.vp_coding.cleanarchitecturenoteapp.feature_note.data.repository.NoteRepositoryImpl
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app:Application):NoteDatabase
    {
        return Room.databaseBuilder(app,
        NoteDatabase::class.java,
            "note_db"
            ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db:NoteDatabase) : NoteRepository
    {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository) :NoteUseCase{
        return NoteUseCase(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
            )
    }

}