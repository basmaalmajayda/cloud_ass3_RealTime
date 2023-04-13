package com.example.cloud_ass3_realtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        var email = emailLogin.text
        var password = passwordLogin.text

        buttonLogin.setOnClickListener{
            if(email.toString().isEmpty()){
                Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.toString().isEmpty()){
                Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //login code
            login(email.toString(), password.toString())
            }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("basma", "loginWithEmail:success")
                    val user = auth.currentUser
                    val i = Intent(this, Chat::class.java)
                    i.putExtra("user", user!!.uid)
                    startActivity(i)
                } else {
                    Log.w("basma", "loginWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}