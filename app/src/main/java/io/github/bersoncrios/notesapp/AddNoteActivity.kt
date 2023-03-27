package io.github.bersoncrios.notesapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import io.github.bersoncrios.notesapp.databinding.ActivityAddNoteBinding
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddNoteBinding


    //Credentials
    var currentUserId: String = ""
    var currentUserName: String = ""

    //Firebase
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser

    //Firebase Firestore
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var collectionReference: CollectionReference = db.collection("Note")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        auth = Firebase.auth

        binding.apply {
            if (Application.instance != null) {
                currentUserId = auth.currentUser?.uid.toString()
                currentUserName = auth.currentUser?.displayName.toString()
            }

            btnAdd.setOnClickListener{
                saveNote()
            }
        }
    }

    private fun saveNote() {
        var title: String = binding.etTitle.text.toString().trim()
        var description: String = binding.etDescription.text.toString().trim()

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)){

            val timestamp: Timestamp = Timestamp(Date())

            val note: Note = Note(
                title,
                description,
                currentUserId,
                timestamp,
                currentUserName
            )

            // add the new Journal
            collectionReference.add(note)
                .addOnSuccessListener {
                    val intent: Intent = Intent(this, NotesActivity::class.java)
                    startActivity(intent)
                    finish()
                }

        } else {
            Log.d("TAG", "saveNote: deu erro")
        }
    }
}