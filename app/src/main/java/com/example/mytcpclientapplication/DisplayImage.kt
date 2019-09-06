/*
*   AUTHOR: Eric Seals
*   DATE:   2019 09 06
*   FILE:   MainActivity.kt
*/

package com.example.mytcpclientapplication

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
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

        Log.i(
            "TRACING_CODE",
            "DisplayImage: finishProcess()"
        )

        val bitmap = (imageView2.drawable as BitmapDrawable).bitmap

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream)

        val byteArray = stream.toByteArray()

        val myUtilitySend = UtilitySend()
        myUtilitySend.execute(byteArray)

        val myUtilityReceive = UtilityReceive(progressBar)
        myUtilityReceive.execute(imageView2)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (
                // View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }


    private fun startImageCapture(){

        Log.i(
            "TRACING_CODE",
            "DisplayImage: startImageCapture()"
        )

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

        Log.i(
            "TRACING_CODE",
            "DisplayImage: startGallery()"
        )

        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, IMAGE_GALLERY_CODE)
        Log.i(
            "TRACING_CODE",
            "in startGallery"
            )
    }
}