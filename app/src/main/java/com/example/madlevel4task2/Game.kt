package com.example.madlevel4task2

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "game_table")
data class Game(
    @ColumnInfo(name = "result")
    var result: String,

    @ColumnInfo(name = "date")
    var date: Date,

    @DrawableRes
    @ColumnInfo(name = "computer")
    var computer: Int,

    @DrawableRes
    @ColumnInfo(name = "user")
    var user: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
)