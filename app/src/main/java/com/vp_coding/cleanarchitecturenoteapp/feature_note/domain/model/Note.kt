package com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vp_coding.cleanarchitecturenoteapp.ui.theme.*


@Entity(tableName = "note")
data class Note(
    val title:String,
    val content:String,
    val timeStamp:Long,
    val color:Int,
    @PrimaryKey val id:Int?=null
){
    companion object{
        val noteColor= listOf(RedOrange, RedPink,BabyBlue,Violet, LightGreen)
    }
}

class InvalidNoteException(  message : String) : Exception(message)
