package io.github.bersoncrios.notesapp

import android.app.Application

class Application: Application() {
    var username: String? = null
    var userId: String? = null

    companion object{
        var instance: io.github.bersoncrios.notesapp.Application? = null
            get() {
                if (field == null){
                    //create a new instance
                    field = io.github.bersoncrios.notesapp.Application()
                }
                return field
            }
            private set
    }
}