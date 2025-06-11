package com.example.tobusytodie.model

import androidx.annotation.Keep
import java.util.Date

@Keep
data class Tarea(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val date: Date = Date()
)
