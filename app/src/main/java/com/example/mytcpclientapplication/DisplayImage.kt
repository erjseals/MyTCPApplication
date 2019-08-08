package com.example.mytcpclientapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_display_image.*
import java.io.ByteArrayOutputStream

class DisplayImage : AppCompatActivity() {
    private val permissionCode = 1000
    private val imageCaptureCode = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_image)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, imageCaptureCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            imageCaptureCode -> {
                if(resultCode == Activity.RESULT_OK && data != null){
                    var bitmap = data.extras!!.get("data") as Bitmap
                    imageView2.setImageBitmap(bitmap)

                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

                    val byteArray = stream.toByteArray()

                    val myUtility = Utility()
                    myUtility.execute(byteArray)
                }
            }
            else -> {
                Toast.makeText(this, "Request not recognized", Toast.LENGTH_SHORT).show()
            }
        }
    }
}