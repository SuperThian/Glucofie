package com.capstone.glucofie.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.capstone.glucofie.R
import com.capstone.glucofie.data.user.UserModel
import com.capstone.glucofie.data.api.ApiService
import com.capstone.glucofie.data.api.LoginRequest
import com.capstone.glucofie.databinding.ActivityProfileBinding
import com.capstone.glucofie.view.model.LoginViewModel
import com.capstone.glucofie.view.model.MainViewModel
import com.capstone.glucofie.view.model.ProfileViewModel
import com.capstone.glucofie.view.model.ViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var username: String
    private lateinit var email: String
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val usernameTextView = findViewById<TextInputEditText>(R.id.profile_username)
        val emailTextView = findViewById<TextInputEditText>(R.id.profile_email)

        username = intent.getStringExtra("username") ?: ""
        email = intent.getStringExtra("email") ?: ""
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            mutableListOf<String>().apply { add("") }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                val intent = Intent(this, BaseActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                lifecycleScope.launch {
                    val userX = profileViewModel.fetchCurrUser(user.id, "Bearer ${user.token}")
                    if (userX != null) {
                        usernameTextView.setText(user.username)
                        emailTextView.setText(user.email)
                        setupTypeDiabetesSpinner(userX.type_diabetes)
                        setupTypeGenderSpinner(userX.gender)
                    } else {
                        setupTypeDiabetesSpinner(null)
                        setupTypeGenderSpinner(null)
                    }
                }
                emailTextView.setText(email)

            }

        }

        usernameTextView.setText(username)
        emailTextView.setText(email)
        usernameTextView.isEnabled = false
        emailTextView.isEnabled = false

        binding.saveButton.setOnClickListener {
            saveProfileData()
        }
    }
    private fun setupTypeGenderSpinner(gender: String?) {
        val genderx = resources.getStringArray(R.array.gender)

        val items = mutableListOf<String>().apply {
            addAll(genderx)
        }

        val genderSpinner = findViewById<Spinner>(R.id.profile_gender)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        genderSpinner.adapter = adapter


        gender?.let { selectedType ->
            if (gender.contains(selectedType)) {
                val selectedIndex = items.indexOf(selectedType)
                genderSpinner.setSelection(selectedIndex)
            }
        }
    }
    private fun setupTypeDiabetesSpinner(diabetesType: String?) {
        val diabetesTypes = resources.getStringArray(R.array.type_diabetes)

        val items = mutableListOf<String>().apply {
            addAll(diabetesTypes)
        }

        val diabetesSpinner = findViewById<Spinner>(R.id.profile_type_diabetes)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        diabetesSpinner.adapter = adapter

        diabetesSpinner.prompt = "Select diabetes type"

        diabetesType?.let { selectedType ->
            if (diabetesTypes.contains(selectedType)) {
                val selectedIndex = items.indexOf(selectedType)
                diabetesSpinner.setSelection(selectedIndex)
            }
        }
    }
    private fun saveProfileData() {
        val typeDiabetes = binding.profileTypeDiabetes.selectedItem.toString()
        val gender = binding.profileGender.selectedItem.toString()
        if (typeDiabetes.isEmpty() || gender.isEmpty()) {

            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                val intent = Intent(this, BaseActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        profileViewModel.updateUser(user.id, user.username, user.email, typeDiabetes, gender, user.token)

                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ProfileActivity, "Profile saved successfully", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: HttpException) {

                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ProfileActivity, "Failed to save profile: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }

        }

        Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT).show()
    }
}