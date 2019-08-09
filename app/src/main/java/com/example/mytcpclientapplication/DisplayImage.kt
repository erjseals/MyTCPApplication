package com.example.mytcpclientapplication

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_display_image.*
import java.io.ByteArrayOutputStream

class DisplayImage : AppCompatActivity() {
    private val imageCaptureCode = 1001
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_image)
//
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        startActivityForResult(cameraIntent, imageCaptureCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i("TRACING_CODE", "DisplayImage.kt line 39: bool1 is ${resultCode == Activity.RESULT_OK}")
        Log.i("TRACING_CODE", "DisplayImage.kt line 39: bool2 is ${data != null}")

        when(requestCode) {
            imageCaptureCode -> {
                if(resultCode == Activity.RESULT_OK && data == null){

                    Log.i("TRACING_CODE", "DisplayImage.kt line 34")

                    imageView2.setImageURI(imageUri)

                    val bitmap = (imageView2.drawable as BitmapDrawable).bitmap
                    imageView2.setImageBitmap(bitmap)

                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)

                    val byteArray = stream.toByteArray()

                    val myUtility = Utility()
                    myUtility.execute(byteArray)
                    Log.i("TRACING_CODE", "DisplayImage.kt line 46")
                }
            }
            else -> {
                Toast.makeText(this, "Request not recognized", Toast.LENGTH_SHORT).show()
            }
        }
    }
}