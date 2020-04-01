package com.app.homeworkkotlin.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.app.homeworkkotlin.R
import com.app.homeworkkotlin.domain.entity.Image
import com.app.homeworkkotlin.domain.entity.MainActivityEvent
import com.app.homeworkkotlin.domain.entity.MainActivityEvent.HIDE_PROGRESS_DIALOG
import com.app.homeworkkotlin.domain.entity.MainActivityEvent.SHOW_PROGRESS_DIALOG
import com.app.homeworkkotlin.presentation.MainViewModel
import com.app.homeworkkotlin.ui.list.ImagesAdapter
import com.app.homeworkkotlin.utils.IMAGEURL
import com.app.homeworkkotlin.utils.IMAGE_LIST_COLUMN_COUNT
import com.app.homeworkkotlin.utils.LIST_STATE_KEY
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), MaterialSearchBar.OnSearchActionListener {
    private val mainViewModel: MainViewModel by inject()
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var images: ArrayList<Image>
    private lateinit var adapter: ImagesAdapter
    lateinit var progressDialog: ProgressDialog
    var listState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
        initListeners()
        progressDialog = ProgressDialog(this)
    }

    override fun onButtonClicked(buttonCode: Int) {}

    override fun onSearchStateChanged(enabled: Boolean) {}

    override fun onSearchConfirmed(text: CharSequence?) {
        hideDialog()
        mainViewModel.findImages(text.toString())
    }

    private fun showDialog(){
        progressDialog.setTitle(getString(R.string.searching_images))
        progressDialog.setMessage(getString(R.string.wait_till_finish_downloading_images))
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
    }

    private fun hideDialog(){
        println(progressDialog)
        progressDialog.hide()
    }
    private fun initRecycler(){
        gridLayoutManager = GridLayoutManager(applicationContext, IMAGE_LIST_COLUMN_COUNT)
        imagesRecycler.layoutManager = gridLayoutManager
        images = ArrayList()
        adapter = ImagesAdapter(
            images,
            listener = object : OnImageClickListener {
                override fun onClick(imageUrl: String) {
                    val imageIntent = Intent(
                        applicationContext,
                        ImageActivity::class.java
                    )
                    imageIntent.putExtra(IMAGEURL, imageUrl)
                    startActivity(imageIntent)
                }
            })
        imagesRecycler.adapter = adapter
    }
    private fun initListeners(){
        searchBar.setOnSearchActionListener(this)
        mainViewModel.images.observeForever {
            images.clear()
            images.addAll(it.take(20))
            adapter.notifyDataSetChanged()
        }

        mainViewModel.events.observeForever {
            when(it){
                SHOW_PROGRESS_DIALOG -> {
                    showDialog() }
                HIDE_PROGRESS_DIALOG -> {
                    hideDialog()
                }
            }
        }
    }

}

interface OnImageClickListener {
    fun onClick(imageUrl: String)
}