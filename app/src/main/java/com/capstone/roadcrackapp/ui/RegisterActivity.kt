package com.capstone.roadcrackapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.roadcrackapp.databinding.ActivityRegisterBinding
import com.capstone.roadcrackapp.ui.viewmodel.RegisterViewModel
import com.capstone.roadcrackapp.ui.viewmodel.ViewModelFactory
import com.capstone.roadcrackapp.model.remote.Result
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var models : ViewModelFactory
    private val registerViewModel : RegisterViewModel by viewModels {models}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        binding.btnSignup.isEnabled = false

        models = ViewModelFactory.getInstance(this)

        binding.usernameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnSignup.setOnClickListener {
            val name = binding.usernameEt.text?.trim().toString()
            val email = binding.emailEt.text?.trim().toString()
            val password = binding.passwordEt.text?.trim().toString()
            val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val isValidPassword = password.length >= 8
            val isValidName = name.isNotEmpty()

            binding.progressBar.visibility = View.VISIBLE
            if(isValidEmail&&isValidPassword&&isValidName) {
                lifecycleScope.launch {
                    try {
                        registerViewModel.register(name, email, password)
                        register()

                    } catch (e: Exception) {
                        Toast.makeText(this@RegisterActivity, "Register failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            else{
                Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun register() {
        registerViewModel.responseRegister.observe(this) { register ->
            when (register) {
                is Result.Success -> {
                    Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

                is Result.Error -> {
                    Toast.makeText(this, "Register failed ${register.errorMessage}", Toast.LENGTH_SHORT).show()
                    Log.d("Register", "Register failed ${register.errorMessage}")
                    binding.progressBar.visibility = View.GONE
                }

                is Result.Loading -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkValid() {
        val email = binding.emailEt.text?.trim().toString()
        val password = binding.passwordEt.text?.trim().toString()
        val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isValidPassword = password.length >= 8
        binding.apply {
            btnSignup.isEnabled = isValidPassword && isValidEmail
        }
    }
}