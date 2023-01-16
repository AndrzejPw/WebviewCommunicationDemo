package com.example.webviewcommunicationdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.webviewcommunicationdemo.databinding.ActivitySecondBinding

const val FROM_USER_EXTRA = "userKey"

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonSecond.setOnClickListener {
            setResult(
                Activity.RESULT_OK,
                Intent().apply { putExtra(FROM_USER_EXTRA, binding.editText.text.toString()) })
            finish()
        }

    }
}