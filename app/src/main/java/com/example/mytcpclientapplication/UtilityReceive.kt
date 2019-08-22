package com.example.mytcpclientapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import java.io.DataInputStream
import java.net.ServerSocket

class UtilityReceive(progressBar: ProgressBar) : AsyncTask<ImageView, Void, Bitmap>() {

    var imageView: ImageView? = null
    var mProgressBar = progressBar

    override fun doInBackground(vararg p0: ImageView?): Bitmap? {

        Log.i(
            "TRACING_CODE",
            "UtilityReceive: doInBackground(...)"
        )

        try {
            imageView = p0[0]

            val server = ServerSocket(8080)
            Log.i("TRACING_CODE", "Server Started")
            Log.i("TRACING_CODE", "Waiting for a client ...")

            val socket2 = server.accept()
            Log.i("TRACING_CODE", "Client Accepted")
            val dataInputStream = DataInputStream(socket2.getInputStream())

            val length = dataInputStream.readInt()
            Log.i("TRACING_CODE", "Length of Array: $length")


            val data = ByteArray(length)


            if (length > 0) {
                dataInputStream.readFully(data, 0, data.size)
            }

            val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)

            socket2.getInputStream().close()
            dataInputStream.close()
            socket2.close()


            return bitmap

        } catch (e: Exception) {
            Log.i("TRACING_CODE", "Everything has gone wrong! Here's the error: $e")
            return null
        }
        return null
    }

    override fun onPreExecute() {
        super.onPreExecute()
        mProgressBar.visibility = View.VISIBLE
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        imageView?.setImageBitmap(result)
        mProgressBar.visibility = View.GONE

    }
}