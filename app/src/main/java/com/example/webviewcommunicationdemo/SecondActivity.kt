package com.example.webviewcommunicationdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.webviewcommunicationdemo.databinding.ActivitySecondBinding

const val FROM_USER_EXTRA = "userKey"
const val FROM_WEBVIEW_EXTRA = "webviewKey"

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webviewMessageTextView.text = intent.getStringExtra(FROM_WEBVIEW_EXTRA)
        binding.responseEditText.addTextChangedListener(onTextChanged = { text: CharSequence?, _, _, _ ->

            setResult(
                Activity.RESULT_OK,
                Intent().apply { putExtra(FROM_USER_EXTRA, text.toString()) })
        }
        )
        binding.buttonSecond.setOnClickListener {
            finish()
        }
    }
}