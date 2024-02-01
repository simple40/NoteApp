package com.vp_coding.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.vp_coding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier=Modifier,
    noteOrder: NoteOrder=NoteOrder.Date(OrderType.DescendingOrder),
    onORderChange:(NoteOrder)->Unit
) {
    Column(modifier=modifier)
    {
            Row(modifier = Modifier.fillMaxWidth()) {
                DefaultRadioButton(
                    text = "Title",
                    selected = noteOrder is NoteOrder.Title,
                    onSelected = {
                        onORderChange(NoteOrder.Title(noteOrder.orderType))
                    })
                Spacer(modifier = Modifier.width(8.dp))


                DefaultRadioButton(
                    text = "Date",
                    selected = noteOrder is NoteOrder.Date,
                    onSelected = {
                        onORderChange(NoteOrder.Date(noteOrder.orderType))
                    })
                Spacer(modifier = Modifier.width(8.dp))


                DefaultRadioButton(
                        text = "Color",
                        selected = noteOrder is NoteOrder.Color,
                        onSelected = {
                            onORderChange(NoteOrder.Color(noteOrder.orderType))
                        })

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {

            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.AscendingOrder,
                onSelected = {
                    onORderChange(noteOrder.copy(OrderType.AscendingOrder))
                })
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.DescendingOrder,
                onSelected = {
                    onORderChange(noteOrder.copy(OrderType.DescendingOrder))
                })

        }

    }
}