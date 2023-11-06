package com.example.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "first_signal")
    val firstSignal: Int,
    @ColumnInfo(name = "second_signal")
    val secondSignal: Int,
    @ColumnInfo(name = "third_signal")
    val thirdSignal: Int
)

