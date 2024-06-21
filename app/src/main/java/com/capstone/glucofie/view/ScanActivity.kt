package com.capstone.glucofie.view

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.SparseArray
import android.widget.Toast
import androidx.activity.viewModels
import com.capstone.glucofie.R
import com.capstone.glucofie.databinding.ActivityScanBinding
import com.capstone.glucofie.view.model.MainViewModel
import com.capstone.glucofie.view.model.ProfileViewModel
import com.capstone.glucofie.view.model.ScanViewModel
import com.capstone.glucofie.view.model.ViewModelFactory
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.io.IOException
import java.lang.StringBuilder

class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    private var currentImageUri: Uri? = null
    private val scanViewModel by viewModels<ScanViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener {
            currentImageUri?.let {
                analyzeImage()
            } ?: run {
                showToast(getString(R.string.no_image))
            }
        }
    }

    private fun startGallery() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this)
    }
    var bitmap: Bitmap? =null
    private fun showImage() {
        currentImageUri?.let {
            Log.d(TAG, "showImage: $it")
            Picasso.with(this).load(it).into(binding.previewImageView)
        }
    }
    private fun getTextFromImage(bitmap: Bitmap){
        val recog = TextRecognizer.Builder(this).build()
        if(!recog.isOperational){
            Toast.makeText(this, "Error !!", Toast.LENGTH_SHORT)
        }else {
            val frame = Frame.Builder().setBitmap(bitmap).build()
            val textBlockSParseArray: SparseArray<TextBlock> = recog.detect(frame)
            val stringBuilder = StringBuilder()
            for (i in 0..textBlockSParseArray.size()){
                val textBlock = textBlockSParseArray.valueAt(i)
                stringBuilder.append(textBlock.value)
                stringBuilder.append("\n")
            }

        }
    }
    private fun analyzeImage() {
        val resultUri = currentImageUri ?: return
        val file = File(resultUri.path ?: return)

        try {
            scanViewModel.sendImage(file, token)
            Log.e(TAG, "analyzeImage: 1", )
            val intent = Intent(this@ScanActivity, ResultActivity::class.java)
            intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, resultUri.toString()) // Pass Uri as string
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            Log.e(TAG, "analyzeImage: 4", )
        } catch (e: IOException) {
            showToast("Error uploading image: ${e.message}")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "ImagePicker"
    }
    var token = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.getSession().observe(this){ user ->
            if(!user.isLogin){
                val intent = Intent(this, BaseActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()

            }else {
                token = user.token
            }

        }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                currentImageUri = resultUri
//                saveImageToGallery(resultUri)
//                scanViewModel.sendImage(uri = resultUri, token = token)
                Log.e(TAG, "onActivityResult: ${resultUri.toString()}", )
                Toast.makeText(this, "Successfully Saved Image", Toast.LENGTH_SHORT).show()
                showImage()
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, resultUri)
                }catch(e: IOException){
                    e.printStackTrace()
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                showToast("Error cropping image: ${error.message}")
            }
        }
    }
    private fun saveImageToGallery(imageUri: Uri) {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        val savedImageUri = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            "Glucofie_${System.currentTimeMillis()}",
            "Cropped image"
        )

        if (savedImageUri != null) {
            Toast.makeText(this, "Image saved to Gallery", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }

}
