package com.capstone.roadcrackapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.capstone.roadcrackapp.R
import com.capstone.roadcrackapp.databinding.ActivityUploadBinding
import com.capstone.roadcrackapp.model.adapter.createCustomTempFile
import com.capstone.roadcrackapp.model.adapter.reduceFileImage
import com.capstone.roadcrackapp.model.adapter.uriToFile
import com.capstone.roadcrackapp.ui.viewmodel.UploadViewModel
import com.capstone.roadcrackapp.ui.viewmodel.ViewModelFactory
import com.capstone.roadcrackapp.model.remote.Result
import com.capstone.roadcrackapp.model.sharedpreferences.LogPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var factory: ViewModelFactory
    private val uploadViewModel: UploadViewModel by viewModels { factory }

    private lateinit var currentPhotoPath: String
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)


        factory = ViewModelFactory.getInstances(this)

        supportActionBar?.show()
        supportActionBar?.title = "Upload Report"
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.color_primary))

        setupBottomNav()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnUpload.isEnabled = false


        binding.edLokasi.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.edTittle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkValid()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnCamera.setOnClickListener(){
            startTakePhoto()
        }
        binding.btnGallery.setOnClickListener(){
            startGallery()
        }
//        binding.btnMediaother.setOnClickListener(){
//            startGallery()
//        }
        binding.btnUpload.setOnClickListener(){
            uploadImage()
            uploadViewModel.responseUpload.observe(this){result->
                when(result){
                    is Result.Success->{
                        val intent = Intent(this@UploadActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                        binding.progressBar.visibility = View.GONE
                        Log.d("UploadActivity", "onCreate: ${result.data}")
                    }
                    is Result.Error->{
                        binding.progressBar.visibility = View.GONE
                        Log.d("Login", "Login ${result.errorMessage}")
                    }
                    is Result.Loading->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    private fun uploadImage() {
        val loc = binding.edLokasi.text.toString()
        val title = binding.edTittle.text.toString()

        val lokasi = loc.toRequestBody("text/plain".toMediaType())
        val judul = title.toRequestBody("text/plain".toMediaType())
        val reduceimg = reduceFileImage(getFile as File)
        val requestImageFile = reduceimg.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagemultipart : MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            reduceimg.name,
            requestImageFile
        )
        val prefLogin = LogPreferences(this)
        var token = prefLogin.getToken().toString()
        token = "Bearer $token"

        lifecycleScope.launch{

            withContext(Dispatchers.Main){
                binding.progressBar.visibility = View.VISIBLE
                binding.btnUpload.isEnabled  = false
                Toast.makeText(this@UploadActivity, "Uploading Story...", Toast.LENGTH_SHORT).show()
            }
            uploadViewModel.uploadStory(token,judul, imagemultipart,lokasi)

        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@UploadActivity,
                "com.capstone.roadcrackapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri

            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@UploadActivity)
                getFile = myFile
                binding.ivPreview.setImageURI(uri)
                checkValid()
            }
        }
    }

    private fun checkValid() {
        val lokasi = binding.edLokasi.text?.trim().toString()
        val judul = binding.edTittle.text?.trim().toString()
        val file = getFile

        if (lokasi.isNotEmpty() && file != null && judul.isNotEmpty()) {
            binding.btnUpload.isEnabled = true
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            myFile.let { file ->
                getFile = file
                binding.ivPreview.setImageBitmap(BitmapFactory.decodeFile(file.path))
                checkValid()
            }
        }
    }

    private fun setupBottomNav() {

        val bottomNav = binding.bottomBar
        bottomNav.selectedItemId = R.id.add


        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }

                R.id.info -> {
                    startActivity(Intent(this, InfoActivity::class.java))
                    true
                }

                R.id.alert -> {
                    startActivity(Intent(this, AlertActivity::class.java))
                    true
                }

                R.id.add -> {
                    startActivity(Intent(this, UploadActivity::class.java))
                    true
                }


                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10

    }
}