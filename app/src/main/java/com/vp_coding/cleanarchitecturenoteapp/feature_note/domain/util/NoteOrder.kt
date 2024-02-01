package com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.util

sealed class NoteOrder(val orderType: OrderType){

    class Title(orderType: OrderType):NoteOrder(orderType)
    class Date(orderType: OrderType):NoteOrder(orderType)
    class Color(orderType: OrderType):NoteOrder(orderType)

    fun copy(orderType: OrderType):NoteOrder{
        when (this){
            is NoteOrder.Title->return NoteOrder.Title(orderType)
            is NoteOrder.Date ->return NoteOrder.Date(orderType)
            is NoteOrder.Color->return NoteOrder.Color(orderType)
        }
    }
}
