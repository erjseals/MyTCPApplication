package com.example.mytcpclientapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        private const val CAMERA_PERMISSION_CODE = 1000
        private const val GALLERY_PERMISSION_CODE = 1002
        private val captureImage = 1
        private val galleryImage = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(
            "TRACING_CODE",
            "MainActivity: Starting"
        )

        //We are setting up the button
        //Need to check permissions

        floatingActionButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    //Permissions are not correct, need to request the user to set up
                    val permission = arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //show popup to user to request
                    requestPermissions(permission, CAMERA_PERMISSION_CODE)
                } else {
                    //permission already granted
                    goToANewActivity(captureImage)
                }
            } else {
                //system version too old
                Toast.makeText(this, "System Version too old", Toast.LENGTH_SHORT).show()
            }
        }

        floatingActionButton2.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, GALLERY_PERMISSION_CODE)
                }
                else {
                    goToANewActivity(galleryImage)
                }
            }
            else {
                Toast.makeText(this, "System Version too old", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted from popup
                    goToANewActivity(captureImage)
                } else {
                    //permission denied
                    Toast.makeText(this, "Camera Permission Not Granted", Toast.LENGTH_SHORT).show()
                }
            }
            GALLERY_PERMISSION_CODE ->
                if( grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goToANewActivity(galleryImage)
                } else {
                    Toast.makeText(this, "Gallery Permission Not Granted", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun goToANewActivity(requestCode: Int) {
        Log.i(
            "TRACING_CODE",
            "MainActivity: Heading to DisplayImage"
        )
        val displayImageIntent = Intent(this, DisplayImage::class.java)
        displayImageIntent.putExtra("requestCode", requestCode)
        startActivity(displayImageIntent)
    }
}
