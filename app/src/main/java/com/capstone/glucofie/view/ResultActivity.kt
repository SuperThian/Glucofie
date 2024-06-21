package com.capstone.glucofie.view

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.widget.TextView
import androidx.activity.R
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.glucofie.data.AuthRepository
import com.capstone.glucofie.databinding.ActivityResultBinding
import com.capstone.glucofie.view.model.LoginViewModel
import com.capstone.glucofie.view.model.MainViewModel
import com.capstone.glucofie.view.model.ResultViewModel
import com.capstone.glucofie.view.model.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val viewModel by viewModels<ResultViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val karbo = findViewById<TextView>(com.capstone.glucofie.R.id.carbo_text)
        val suggest = findViewById<TextView>(com.capstone.glucofie.R.id.saran_text)
        val gula = findViewById<TextView>(com.capstone.glucofie.R.id.gula_text)
        val lemaktotal = findViewById<TextView>(com.capstone.glucofie.R.id.lemak_total_text)
        val protein= findViewById<TextView>(com.capstone.glucofie.R.id.protein_text)
        mainViewModel.getSession().observe(this){user->
            if(!user.isLogin){

            }else{
                lifecycleScope.launch {
                    val results = viewModel.getResultAnalyze(user.token)

                    val karboVal = results.result["Karbohidrat Total"]?.toString() ?: "0"
                    karbo.text = "Karbohidrat Total: $karboVal"
                    suggest.text = "Saran: "+results.suggestion.toString()
                    val gulaVal = results.result["Gula"]?.toString() ?: "0"
                    gula.text = "Gula: $gulaVal"
                    val lemakValue = results.result["Lemak Total"]?.toString() ?: "0"
                    lemaktotal.text = "Lemak Total: $lemakValue"
                    val proteinValue = results.result["Protein"]?.toString() ?: "0"
                    protein.text = "Protein: $proteinValue"
                }
            }
        }
        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            Log.d(TAG, "ShowImage: $it")
            binding.resultImage.setImageURI(it)
        }

    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_img_uri"
        const val TAG = "imagePicker"
    }
}