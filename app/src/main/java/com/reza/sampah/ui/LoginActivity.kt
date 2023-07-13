package com.reza.sampah.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.core.data.Resource
import com.example.core.domain.model.Session
import com.example.core.domain.model.User
import com.reza.sampah.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel : LoginViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.statusLogin.observe(this, this::loginObserve)
        viewModel.isLoggined()


        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        binding.btnGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    private fun loginObserve(result: Resource<Boolean>) {
        when (result) {
            is Resource.Error -> {
                binding.loading.isVisible = false
                Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> binding.loading.isVisible = true
            is Resource.Success -> {
                binding.loading.isVisible = false
                if (result.data == true ) {
                    viewModel.getSession()?.let { session: Session ->
                        if (session.role == "admin") {
                            startActivity(Intent(this, AdminActivity::class.java))
                        }else {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra(TAG_USERNAME, session.username)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val TAG_USERNAME = "TAG_USERNAME"
    }
}