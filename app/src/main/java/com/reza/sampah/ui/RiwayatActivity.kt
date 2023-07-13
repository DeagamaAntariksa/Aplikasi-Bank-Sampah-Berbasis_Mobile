package com.reza.sampah.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.domain.model.Trash
import com.reza.sampah.ListSampahUserAdapter
import com.reza.sampah.databinding.RiwayatBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RiwayatActivity : AppCompatActivity(), ListSampahUserAdapter.RiwayatUserOnClickListener {
    private lateinit var binding: RiwayatBinding
    private val mAdapter = ListSampahUserAdapter(this)
    private val viewModel: RiwayatViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.listTrash.observe(this, this::listTrashObserve)
        viewModel.statusRemove.observe(this, this::removeStatusObserve)

        binding.btnBack.setOnClickListener { onBackPressed() }

        setupRecylerView()
    }

    private fun setupRecylerView() {
        with(binding.rvRiwayat){
            layoutManager = LinearLayoutManager(this@RiwayatActivity)
            adapter = mAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHistory()
    }

    private fun listTrashObserve(result: Resource<List<Trash>>) {
        when (result) {
            is Resource.Error -> {
                binding.loading.isVisible = false
                Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> binding.loading.isVisible = true
            is Resource.Success -> {
                binding.loading.isVisible = false
                result.data?.let { list -> mAdapter.setData(list) }
            }
        }
    }

    private fun removeStatusObserve(result: Resource<Boolean>) {
        when (result) {
            is Resource.Error -> {
                binding.loading.isVisible = false
                Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> binding.loading.isVisible = true
            is Resource.Success -> {
                binding.loading.isVisible = false
                if (result.data == true ) {
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun delete(trash: Trash) {
        viewModel.remove(trash.id)
    }
}