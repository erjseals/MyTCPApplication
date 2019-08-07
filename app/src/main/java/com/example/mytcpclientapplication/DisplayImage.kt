package com.example.mytcpclientapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_display_image.*
import kotlinx.android.synthetic.main.activity_main.*

class DisplayImage : AppCompatActivity() {
    private val permissionCode = 1000
    private val imageCaptureCode = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_image)

//        val values = ContentValues()
//        values.put(MediaStore.Images.Media.TITLE, "New Picture")
//        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
//        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, imageCaptureCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            imageCaptureCode -> {
                if(resultCode == Activity.RESULT_OK && data != null){
                    imageView2.setImageBitmap(data.extras!!.get("data") as Bitmap)
                }
            }
            else -> {
                Toast.makeText(this, "Request not recognized", Toast.LENGTH_SHORT).show()
            }
        }



        //called when image is taken
//        if(resultCode == Activity.RESULT_OK && requestCode == imageCaptureCode){
//            var bmp = data!!.extras!!.get("data") as Bitmap
//            imageView.setImageBitmap(bmp)
        //put the image on the image view
//            imageView.setImageURI(imageUri)
    }
}