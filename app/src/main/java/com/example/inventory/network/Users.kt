package com.example.inventory.network

data class Users(
    val id: Int,
    val mac: String,
    val sensorius: String,
    val stiprumas: Int
)

data class Measurements(
    val matavimas: Int,
    val x: Int,
    val y: Int,
    val atstumas: Float
)

data class Strengths(
    val id: Int,
    val matavimas: Int,
    val sensorius: String,
    val stiprumas: Int
)
