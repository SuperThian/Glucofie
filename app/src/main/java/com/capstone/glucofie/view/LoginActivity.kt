package com.capstone.glucofie.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.capstone.glucofie.data.Result
import com.capstone.glucofie.data.user.UserModel
import com.capstone.glucofie.databinding.ActivityLoginBinding
import com.capstone.glucofie.view.model.LoginViewModel
import com.capstone.glucofie.view.model.ViewModelFactory
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
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
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty()) {
                binding.emailEditText.error = "Email tidak boleh kosong"
            } else if (password.isEmpty()) {
                binding.passwordEditText.error = "Password tidak boleh kosong"
            } else {
                lifecycleScope.launch {
                    viewModel.login(email, password).observe(this@LoginActivity) { result ->
                        when (result) {
                            is Result.Loading -> {
                                showLoading(true)
                            }
                            is Result.Success -> {
                                showLoading(false)
                                showToast("Login berhasil!")
                                val data2 = result.data.loginResult

                                if (data2 != null) {
                                    fetchUserAndSave(id = data2.id, token = "Bearer ${data2.token}", email = data2.email)
                                } else {
                                }

                                save(
                                    UserModel(
                                        id = result.data.loginResult?.id.toString(),
                                        username = result.data.loginResult?.username.toString(),
                                        email = email,
                                        type_diabetes = result.data.loginResult?.typeDiabetes.toString(),
                                        gender = result.data.loginResult?.gender.toString(),
                                        token = result.data.loginResult?.token.toString(),
                                        isLogin = true
                                    )
                                )
                            }
                            is Result.Error -> {
                                showLoading(false)
                                showToast(result.error)
                            }
                        }
                    }
                }
            }
        }
        binding.signupButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchUserAndSave(id: String, token: String, email: String) {
        lifecycleScope.launch {
            try {
                val user = viewModel.fetchCurrUser(id, token)
                if (user != null) {
                    save(
                        UserModel(
                            id = user.id,
                            username = user.username,
                            email = email,
                            type_diabetes = user.type_diabetes,
                            gender = user.gender,
                            token = user.token,
                            isLogin = true
                        )
                    )
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun save(session: UserModel) {
        lifecycleScope.launch {
            viewModel.saveSession(session)
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            ViewModelFactory.clearInstance()
            startActivity(intent)
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
