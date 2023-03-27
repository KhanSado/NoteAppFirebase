package io.github.bersoncrios.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.bersoncrios.notesapp.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        auth = Firebase.auth

        binding.btnSingup.setOnClickListener {
            createUser()
        }
    }

    private fun createUser(){
        val email = binding.etEmailNew.text.toString()
        val pass = binding.etPasswordNew.text.toString()

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful) {
                    val userCreated = auth.currentUser
                    Log.d("TAG", "createUserWithEmail:${userCreated}")
                    finish()
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}