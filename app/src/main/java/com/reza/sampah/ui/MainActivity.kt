package com.reza.sampah.ui

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reza.sampah.databinding.HomepelangganBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: AppCompatActivity() {
    private lateinit var binding: HomepelangganBinding

    private val viewModel : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomepelangganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra(LoginActivity.TAG_USERNAME)?.let { username ->
            binding.username.text = username
        }

        var username = binding.username.text.toString()

        binding.logoutBtn.setOnClickListener {
            viewModel.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        binding.btnJemput.setOnClickListener {
            startActivity(Intent(this, JemputActivity::class.java))
        }

        binding.btnRiwayat.setOnClickListener {
            startActivity(Intent(this, RiwayatActivity::class.java))
        }
        binding.btnJenis.setOnClickListener {
            startActivity(Intent(this, JenisActivity::class.java))
        }
        binding.username.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("username", username )
            startActivity(intent)
        }
    }
}