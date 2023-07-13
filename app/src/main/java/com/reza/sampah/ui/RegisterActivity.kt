package com.reza.sampah.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.core.data.Resource
import com.example.core.domain.model.User
import com.reza.sampah.databinding.ActivityRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val viewModel : RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.statusSignUp.observe(this, this::signupObserver)

        binding.btnRegister.setOnClickListener {

            val email = binding.etRegisterEmail.text.toString()
            val password = binding.etRegisterPassword.text.toString()
            val username = binding.etRegisterUsername.text.toString()

            val user = User(email = email, username = username)

            viewModel.signUp(user,password)
        }

        binding.btnGoToLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java ))
        }

    }
    private fun signupObserver(result: Resource<Boolean>) {
        when(result) {
            is Resource.Error -> {
                binding.loading.isVisible = false
                Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> binding.loading.isVisible = true
            is Resource.Success -> {
                binding.loading.isVisible = false
                Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }
}