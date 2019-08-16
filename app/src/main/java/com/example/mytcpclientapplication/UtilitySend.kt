package com.example.mytcpclientapplication

import android.os.AsyncTask
import android.util.Log
import java.io.DataOutputStream
import java.net.Socket

class UtilitySend : AsyncTask<ByteArray, Void, Void>() {

    override fun doInBackground(vararg p0: ByteArray?): Void? {

        try {
            val socket = Socket("192.168.108.119", 8000)
            if (socket.isConnected) {
                Log.i(
                    "TRACING_CODE",
                    "Successfully connected"
                )
            }

            val dataOutputStream = DataOutputStream(socket.getOutputStream())

            dataOutputStream.writeInt(p0[0]!!.size)
            dataOutputStream.write(p0[0], 0, p0[0]!!.size)

            socket.getOutputStream().close()
            dataOutputStream.flush()
            dataOutputStream.close()
            socket.close()
        } catch (e: Exception) {
            Log.i(
                "TRACING_CODE",
                "Everything has gone wrong! Here's the error: $e"
            )
        }
        return null
    }
}