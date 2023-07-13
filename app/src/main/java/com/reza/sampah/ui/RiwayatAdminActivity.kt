package com.reza.sampah.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.domain.model.Trash
import com.reza.sampah.ListSampahAdminAdapter
import com.reza.sampah.R
import com.reza.sampah.databinding.ActivityRiwayatAdminBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RiwayatAdminActivity : AppCompatActivity(), ListSampahAdminAdapter.RiwayatAdminListener {

    private lateinit var binding: ActivityRiwayatAdminBinding
    private val viewModel: RiwayatAdminViewModel by viewModel()
    private val mAdapter = ListSampahAdminAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.listTrash.observe(this, this::listTrashObserve)
        viewModel.statusSwitch.observe(this, this::updateStatusObserve)

        binding.btnBack.setOnClickListener { onBackPressed() }
        setupRecylerView()
    }

    private fun setupRecylerView() {
        with(binding.rvRiwayat){
            layoutManager = LinearLayoutManager(this@RiwayatAdminActivity)
            adapter = mAdapter
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getListTrash()
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

    private fun updateStatusObserve(result: Resource<Boolean>) {
        when (result) {
            is Resource.Error -> {
                binding.loading.isVisible = false
                Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> binding.loading.isVisible = true
            is Resource.Success -> {
                binding.loading.isVisible = false
                if (result.data == true ) {
                    Toast.makeText(this, "Status berhasil diubah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSwitch(trash: Trash) {
        viewModel.updateTrash(trash)
    }
}