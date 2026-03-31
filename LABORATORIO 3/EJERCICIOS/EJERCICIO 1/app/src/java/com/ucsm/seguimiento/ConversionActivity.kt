package com.ucsm.seguimiento

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ucsm.seguimiento.databinding.ActivityConversionBinding

class ConversionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConversionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConvertir.setOnClickListener {
            val soles = binding.etMonto.text.toString().toDoubleOrNull()

            if (soles != null) {
                val dolares = soles / 3.7
                binding.txtResultado.text = "USD: $dolares"
            } else {
                binding.txtResultado.text = "Ingrese un valor válido"
            }
        }
    }
}