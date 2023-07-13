package com.reza.sampah.ui

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.core.data.Resource
import com.example.core.domain.model.Trash
import com.reza.sampah.databinding.ActivityJemputBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class JemputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJemputBinding
    private val viewModel: JemputViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJemputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.statusUpload.observe(this, this::uploadObserver)

        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.btnJemput.setOnClickListener {
            val trash = getForm()
            viewModel.inputJemput(trash)
        }

        binding.etWeight.addTextChangedListener(onTextChanged = { s, _, _, _ ->
            if (s.toString().isNotEmpty()){
                val idCategorySelected = binding.rgCategory.checkedRadioButtonId
                val category = binding.root.findViewById<RadioButton>(idCategorySelected).text.toString()
                val weight = s.toString().toDouble()

                binding.etPrice.setText(calculatePrice(category, weight))
            }
        })
    }

    private fun getForm(): Trash {
        val name = binding.useEt.text.toString()
        val idCategorySelected = binding.rgCategory.checkedRadioButtonId
        val category = binding.root.findViewById<RadioButton>(idCategorySelected).text.toString()
        val weight = binding.etWeight.text.toString().toDouble()
        val price = binding.etPrice.text.toString()
        val date = binding.etDate.text.toString()
        val address = binding.etAddress.text.toString()

        return Trash(category = category, name = name, weight = weight, price = price, datePickUp = date, address = address)
    }

    private fun calculatePrice(category: String, weight: Double): String{
        var price = 0
        if (category.equals(Category.PLASTIK.name, true)){
            price = (weight * 1000).toInt()
        } else if(category.equals(Category.KALENG.name, true)){
            price = (weight * 2000).toInt()
        } else if (category.equals(Category.KERTAS.name, true)){
            price = (weight * 500).toInt()
        }
        return price.toString()
    }

    private fun uploadObserver(result: Resource<Boolean>) {
        when (result) {
            is Resource.Error -> {
                binding.loading.isVisible = false
                Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> binding.loading.isVisible = true
            is Resource.Success -> {
                binding.loading.isVisible = false
                if (result.data == true ) {
                    Toast.makeText(this, "Berhasil membuat request jemput", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }
        }
    }

    enum class Category{
        PLASTIK,
        KALENG,
        KERTAS
    }
}