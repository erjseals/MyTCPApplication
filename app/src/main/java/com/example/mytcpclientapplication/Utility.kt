package com.example.mytcpclientapplication

import android.content.Context
import android.os.AsyncTask
import android.widget.TextView
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket

class Utility(
    val textView: TextView
) : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg p0: String?): String? {

        var serverMessage = ""
        val clientMessage = p0[0] ?: "Send a Message"

        try {
            val socket = Socket("192.168.108.119", 8888)
            DataInputStream(socket.getInputStream())
            BufferedReader(InputStreamReader(System.`in`))

            DataOutputStream(socket.getOutputStream()).writeUTF(clientMessage)
            DataOutputStream(socket.getOutputStream()).flush()
            serverMessage = DataInputStream(socket.getInputStream()).readUTF()


            DataOutputStream(socket.getOutputStream()).close()
            socket.close()
        }catch (e: Exception){

        }
        return serverMessage
    }

    override fun onPreExecute() {
        super.onPreExecute()
        textView.text = "Waiting for message..."
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        textView.text = result
    }
}