package com.example.mytcpclientapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_test_connection_reply.*

class TestConnectionReply : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_connection_reply)

        button3.setOnClickListener{
            val intent = Intent(this, TestConnectionSend::class.java)
            startActivity(intent)
        }
        val incomingIntent = intent
        val message = incomingIntent.extras?.getString("message")

        val myUtility = Utility(this, textView)
        myUtility.execute(message)

    }
}
