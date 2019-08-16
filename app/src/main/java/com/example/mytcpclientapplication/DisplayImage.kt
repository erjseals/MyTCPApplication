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
import android.view.View
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

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        startActivityForResult(cameraIntent, imageCaptureCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            imageCaptureCode -> {
                if (resultCode == Activity.RESULT_OK && data == null) {

                    imageView2.setImageURI(imageUri)

                    var bitmap = (imageView2.drawable as BitmapDrawable).bitmap

                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)

                    var byteArray = stream.toByteArray()

                    val myUtilitySend = UtilitySend()
                    myUtilitySend.execute(byteArray)

                    val myUtilityReceive = UtilityReceive(progressBar)
                    myUtilityReceive.execute(imageView2)
                }
            }
            else -> {
                Toast.makeText(this, "DisplayImage: Request not recognized", Toast.LENGTH_SHORT).show()
            }
        }
    }
}