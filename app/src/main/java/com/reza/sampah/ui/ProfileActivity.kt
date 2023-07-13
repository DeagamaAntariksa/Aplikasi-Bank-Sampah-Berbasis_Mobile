package com.reza.sampah.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.reza.sampah.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var username = intent.getStringExtra("username")
        binding.etUsername.setText(username)

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        if (user != null) {
            binding.etEmail.setText(user.email)

        }
    }
}