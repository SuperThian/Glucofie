package com.capstone.glucofie.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.glucofie.databinding.ActivityScanTutorialBinding

class ScanTutorialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scanButton.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }
    }
}