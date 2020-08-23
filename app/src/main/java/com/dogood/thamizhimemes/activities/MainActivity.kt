package com.dogood.thamizhimemes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dogood.thamizhimemes.R
import com.dogood.thamizhimemes.activities.imageEdit.ImageEditActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this,ImageEditActivity::class.java))
    }
}