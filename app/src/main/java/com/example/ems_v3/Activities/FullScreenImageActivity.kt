package com.example.ems_v3.Activities

import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ems_v3.R

class FullScreenImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        val imageView = findViewById<ImageView>(R.id.fullScreenImageView)
        val closeButton = findViewById<ImageButton>(R.id.closeButton)

        val imageUri = intent.getStringExtra("imageUri")
        if (imageUri != null) {
            val uri = Uri.parse(imageUri)
            imageView.setImageURI(uri)
        }

        closeButton.setOnClickListener {
            finish()
        }
    }
}
