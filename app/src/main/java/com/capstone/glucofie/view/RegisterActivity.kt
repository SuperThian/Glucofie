package com.capstone.glucofie.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.capstone.glucofie.data.Result
import com.capstone.glucofie.data.respon.RegisterResponse
import com.capstone.glucofie.databinding.ActivityRegisterBinding
import com.capstone.glucofie.view.model.RegisterViewModel
import com.capstone.glucofie.view.model.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            showLoading(true)
            val username = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (username.isEmpty()) {
                binding.nameEditTextLayout.error = "Nama tidak boleh kosong"
            } else if (email.isEmpty()) {
                binding.emailEditTextLayout.error = "Email tidak boleh kosong"
            } else if (password.isEmpty()) {
                binding.passwordEditTextLayout.error = "Password tidak boleh kosong"
            } else if (confirmPassword.isEmpty()) {
                binding.confirmPasswordEditTextLayout.error = "Konfirmasi password tidak boleh kosong"
            } else if (password != confirmPassword) {
                binding.confirmPasswordEditTextLayout.error = "Password dan konfirmasi password harus sama"
            } else {
                lifecycleScope.launch {
                    try {
                        viewModel.register(username, email, password, confirmPassword).observe(this@RegisterActivity) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    showLoading(true)
                                }
                                is Result.Success -> {
                                    showLoading(false)
                                    showToast("Register berhasil!")
                                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                is Result.Error -> {
                                    showLoading(false)
                                    showToast(result.error)
                                }
                            }
                        }
                    } catch (e: HttpException) {
                        showLoading(false)
                        val errorBody = e.response()?.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                        showToast(errorResponse.message)
                    }
                }
            }
        }
        binding.loginButton.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        binding.signupButton.isEnabled = !isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
