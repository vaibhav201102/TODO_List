package com.vaibhavjoshi.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TodoList(

    @PrimaryKey
    @ColumnInfo(name = "dateAddedItem") val dateAddedItem : Date,
    @ColumnInfo(name = "listNoteText") val listNoteText : String,

)
