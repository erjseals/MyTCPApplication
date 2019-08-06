package com.example.mytcpclientapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test_connection.*
import kotlinx.android.synthetic.main.activity_test_connection_reply.*

class TestConnectionSend : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_connection)

        button2.setOnClickListener{
            val intent = Intent(this, TestConnectionReply::class.java)
            intent.putExtra("message", editText.text.toString())
            startActivity(intent)
        }



        }
    }

