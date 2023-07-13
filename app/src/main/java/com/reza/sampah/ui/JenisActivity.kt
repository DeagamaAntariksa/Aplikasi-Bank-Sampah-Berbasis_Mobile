package com.reza.sampah.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reza.sampah.databinding.ActivityJenisBinding

class JenisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJenisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJenisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { onBackPressed() }
    }
}