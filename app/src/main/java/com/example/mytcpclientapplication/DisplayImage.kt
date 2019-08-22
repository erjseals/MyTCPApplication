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
    companion object{
        private const val IMAGE_CAPTURE_CODE = 1001
        private const val IMAGE_GALLERY_CODE = 1002
    }
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_image)

        val actionbar = supportActionBar

        actionbar!!.title = "Processed Image"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val mIntentCode = intent.getIntExtra("requestCode", 0)
        Log.i(
            "TRACING_CODE",
            "mIntentCode: $mIntentCode"
        )

        when (mIntentCode) {
            1 -> {startImageCapture()}
            2 -> {startGallery()}
            else -> {Toast.makeText(this, "error code", Toast.LENGTH_SHORT).show()}
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i(
            "TRACING_CODE",
            "in onActivityResult\nrequestCode: $requestCode" +
                    "\nShould match: $IMAGE_CAPTURE_CODE" +
                    "\nData should be null: ${data==null}"

        )
        if(resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_CAPTURE_CODE -> {
                        Log.i(
                            "TRACING_CODE",
                            "in IMAGE_CAPTURE_CODE"
                        )
                        imageView2.setImageURI(imageUri)
                        finishProcess()
                }
                IMAGE_GALLERY_CODE -> {
                        Log.i(
                            "TRACING_CODE",
                            "in IMAGE_GALLERY_CODE"
                        )
                        imageView2.setImageURI(data?.data)
                        finishProcess()
                    }
                else -> {
                    Toast.makeText(this, "DisplayImage: Request not recognized", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            finish()
        }
    }

    private fun finishProcess(){
        val bitmap = (imageView2.drawable as BitmapDrawable).bitmap

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream)

        val byteArray = stream.toByteArray()

        val myUtilitySend = UtilitySend()
        myUtilitySend.execute(byteArray)

        val myUtilityReceive = UtilityReceive(progressBar)
        myUtilityReceive.execute(imageView2)
    }

    private fun startImageCapture(){
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
        Log.i(
            "TRACING_CODE",
            "in startImageCapture"
        )
    }

    private fun startGallery(){
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, IMAGE_GALLERY_CODE)
        Log.i(
            "TRACING_CODE",
            "in startGallery"
            )
    }
}