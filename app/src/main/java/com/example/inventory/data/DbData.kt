package com.example.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "mac")
    val mac: String,
    @ColumnInfo(name = "sensorius")
    val sensorius: String,
    @ColumnInfo(name = "stiprumas")
    val stiprumas: Int
)

@Entity
data class Measurement(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "matavimas")
    val matavimas: Int,
    @ColumnInfo(name = "x")
    val x: Int,
    @ColumnInfo(name = "y")
    val y: Int,
    @ColumnInfo(name = "atstumas")
    val atstumas: Float
)

@Entity
data class Strength(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "matavimas")
    val matavimas: Int,
    @ColumnInfo(name = "sensorius")
    val sensorius: String,
    @ColumnInfo(name = "stiprumas")
    val stiprumas: Int
)
