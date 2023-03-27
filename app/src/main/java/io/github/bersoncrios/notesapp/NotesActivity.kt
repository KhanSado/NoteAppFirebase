package io.github.bersoncrios.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import io.github.bersoncrios.notesapp.databinding.ActivityNotesBinding

class NotesActivity : AppCompatActivity() {

    lateinit var binding: ActivityNotesBinding


    //firebase references
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    var db = FirebaseFirestore.getInstance()

    lateinit var notesList: MutableList<Note>
    lateinit var adapter: NotesAdapter

    var collectionReference: CollectionReference = db.collection("Note")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes)


        //Firebase Auth
        firebaseAuth = Firebase.auth
        user = firebaseAuth.currentUser!!

        //Recycler View
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.layoutManager = LinearLayoutManager(this)

        //Posts arrayList
        notesList = arrayListOf<Note>()

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        collectionReference.whereEqualTo("userId", user.uid )
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    Log.d("TAG", "Elements: ${it}")

                    for (document in it){
                        val note = Note(
                            document.data["title"].toString(),
                            document.data["noteBody"].toString(),
                            document.data["userId"].toString(),
                            document.data["timeAdded"] as Timestamp,
                            document.data["username"].toString()
                        )

                        notesList.add(note)
                    }

                    //Recyclerview
                    adapter = NotesAdapter(
                        this, notesList
                    )
                    binding.rvNotes.adapter = adapter
                    adapter.notifyDataSetChanged()
                } else {
                    binding.tvNoPosts.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Não foi possivel carregar", Toast.LENGTH_LONG).show()
            }
    }
}