package com.app.homeworkkotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.homeworkkotlin.R
import com.app.homeworkkotlin.utils.IMAGEURL
import com.app.homeworkkotlin.utils.downloadImage
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        updateImage()
        initListeners()
    }

    private fun updateImage(){
        val imageUrl = intent.getStringExtra(IMAGEURL)
        image.downloadImage(imageUrl)

    }
    private fun initListeners(){
        back_button.setOnClickListener {
            finish()
        }
    }
}