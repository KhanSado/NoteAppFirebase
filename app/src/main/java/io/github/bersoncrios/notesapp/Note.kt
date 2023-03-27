package io.github.bersoncrios.notesapp

import com.google.firebase.Timestamp

data class Note(
    val title: String,
    val noteBody: String,

    val userId:String,
    val timeAdded: Timestamp,
    var username: String
)