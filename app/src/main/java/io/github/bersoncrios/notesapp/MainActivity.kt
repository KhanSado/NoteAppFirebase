package io.github.bersoncrios.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.bersoncrios.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    //firebase Auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnToSigunp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            LoginWithEmailPassword(
                binding.etEmail.text.toString().trim(),
                binding.etPasword.text.toString().trim()
            )
        }

        //Auth Ref
        auth = Firebase.auth
    }

    private fun LoginWithEmailPassword(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    val currentUser = auth.currentUser
                    val application: Application = Application.instance!!
                    application.userId = auth.currentUser!!.uid
                    application.username = auth.currentUser!!.displayName

                    gotoNotesList()
                } else {
                    Toast.makeText(this, "Login Failure", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun gotoNotesList() {
        val intent = Intent(this, NotesActivity::class.java)
        startActivity(intent)
    }
}