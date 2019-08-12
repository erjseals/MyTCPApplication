package com.example.mytcpclientapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket

class UtilityReceive : AsyncTask<Void, Void, Bitmap>() {

    override fun doInBackground(vararg p0: Void?): Bitmap? {

        try {
            val server = ServerSocket(8080)
            Log.i("TRACING_CODE", "Server Started")
            Log.i("TRACING_CODE", "Waiting for a client ...")

            val socket2 = server.accept()
            Log.i("TRACING_CODE", "Client Accepted")
            val dataInputStream = DataInputStream(socket2.getInputStream())

            val length = dataInputStream.readInt()
            Log.i("TRACING_CODE", "Length of Array: $length")


            val data = ByteArray(length)


            if(length > 0 ){
                dataInputStream.readFully(data, 0, data.size)
            }

            val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)

            socket2.getInputStream().close()
            dataInputStream.close()
            socket2.close()


            return bitmap

        }catch (e: Exception){
            Log.i("TRACING_CODE","Everything has gone wrong! Here's the error: $e")
            return null
        }
        return null
    }
}