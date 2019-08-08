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

//            dos.writeInt(p0[0]!!.size)
//            dos.write(p0[0], 0, p0[0]!!.size)


            val arr = byteArrayOf(
                0x00.toByte(),
                0x01.toByte(),
                0x00.toByte(),
                0x10.toByte(),
                0x00.toByte(),
                0x01.toByte(),
                0x00.toByte(),
                0x1F.toByte(),
                0x60.toByte(),
                0x1D.toByte(),
                0xA1.toByte(),
                0x09.toByte(),
                0x06.toByte(),
                0x07.toByte(),
                0x60.toByte(),
                0x85.toByte(),
                0x74.toByte(),
                0x05.toByte(),
                0x08.toByte(),
                0x01.toByte(),
                0x01.toByte(),
                0xBE.toByte(),
                0x10.toByte(),
                0x04.toByte(),
                0x0E.toByte(),
                0x01.toByte(),
                0x00.toByte(),
                0x00.toByte(),
                0x00.toByte(),
                0x06.toByte(),
                0x5F.toByte(),
                0x1F.toByte(),
                0x04.toByte(),
                0x00.toByte(),
                0x00.toByte(),
                0x18.toByte(),
                0x1D.toByte(),
                0xFF.toByte(),
                0xFF.toByte()
            )
            dataOutputStream.write(arr)
            val req = Arrays.toString(arr)
            Log.i("TRACING_CODE", "$req")

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