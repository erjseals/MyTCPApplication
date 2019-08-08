package com.example.mytcpclientapplication

import android.content.Context
import android.os.AsyncTask
import android.widget.TextView
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket

class Utility() : AsyncTask<ByteArray, Void, Void>() {

    override fun doInBackground(vararg p0: ByteArray?): Void? {

        try {
            val socket = Socket("192.168.108.119", 8888)
            val out = socket.getOutputStream()
            val dos = DataOutputStream(out)

            dos.writeInt(p0[0]!!.size)
            dos.write(p0[0], 0, p0[0]!!.size)

            dos.close()
            out.close()
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