package com.example.mytcpclientapplication

import android.os.AsyncTask
import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.util.*

class Utility() : AsyncTask<ByteArray, Void, Void>() {

    override fun doInBackground(vararg p0: ByteArray?): Void? {

        try {
            val socket = Socket("192.168.0.16", 8080)
            if(socket.isConnected){
                Log.i("TRACING_CODE", "Successfully connected")
            }

            val dataOutputStream = DataOutputStream(socket.getOutputStream())
            val dataInputStream = DataInputStream(socket.getInputStream())

            dataOutputStream.writeInt(p0[0]!!.size)
            dataOutputStream.write(p0[0], 0, p0[0]!!.size)


//            val req = Arrays.toString(arr)
//            Log.i("TRACING_CODE", "$req")

            dataInputStream.close()
            dataOutputStream.close()
            socket.close()
        }catch (e: Exception){

        }
        return null
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
    }
}