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
import com.capstone.roadcrackapp.databinding.ActivityLoginBinding
import com.capstone.roadcrackapp.ui.viewmodel.LoginViewModel
import com.capstone.roadcrackapp.ui.viewmodel.ViewModelFactory
import com.capstone.roadcrackapp.model.remote.Result
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var Models: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels {Models}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        binding.btnSign.isEnabled = false
        Models  = ViewModelFactory.getInstance(this)

        binding.emailET.addTextChangedListener(object : TextWatcher {
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
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnSign.setOnClickListener{
            val email = binding.emailET.text.toString()
            val password = binding.passwordEt.text.toString()
            val ValidPass = password.length >= 8
            val ValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            binding.progressBar.visibility = View.VISIBLE

            if(ValidPass && ValidEmail){
                lifecycleScope.launch {
                    try {
                        loginViewModel.login(email, password)
                        login()
                    }catch (e: Exception){
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                        Log.d("Login", "Login Failed")
                    }
                }
            }
            else{
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login() {
        loginViewModel.responseLogin.observe(this){login->
            when(login){
                is Result.Success->{
//                    val loginPref = LogPreferences(this)
//                    loginPref.setToken(login.data.token)
                    Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                is Result.Error->{
                    Toast.makeText(this, "Login ${login.errorMessage}" , Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                    Log.d("Login", "Login ${login.errorMessage}")
                }
                is Result.Loading->{
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkValid() {
        val email = binding.emailET.text?.trim().toString()
        val password = binding.passwordEt.text?.trim().toString()
        val ValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val ValidPass = password.length >= 8
        binding.apply { btnSign.isEnabled = ValidEmail && ValidPass }
    }
}