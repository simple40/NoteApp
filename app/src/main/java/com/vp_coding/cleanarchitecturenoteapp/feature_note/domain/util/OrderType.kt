package com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.util

sealed class OrderType{
    object AscendingOrder:OrderType()
    object DescendingOrder:OrderType()
}
