package com.app.homeworkkotlin.ui.list

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.homeworkkotlin.R
import com.app.homeworkkotlin.domain.entity.Image
import com.app.homeworkkotlin.utils.downloadImage
import com.app.homeworkkotlin.utils.inflate
import com.app.homeworkkotlin.ui.OnImageClickListener
import com.app.homeworkkotlin.utils.IMAGE_LIST_COLUMN_COUNT
import com.app.homeworkkotlin.utils.IMAGE_LIST_ROW_COUNT

class ImagesAdapter (private val images: ArrayList<Image>, val listener: OnImageClickListener) :
    RecyclerView.Adapter<ImagesAdapter.VisitsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitsHolder {
        val inflatedView = parent.inflate(R.layout.card_image, false)
        val metrics = inflatedView.context.resources.displayMetrics
        val layoutParams = inflatedView.layoutParams
        layoutParams.height = metrics.heightPixels / IMAGE_LIST_ROW_COUNT
        layoutParams.width = metrics.widthPixels / IMAGE_LIST_COLUMN_COUNT
        inflatedView.layoutParams = layoutParams
        return VisitsHolder(inflatedView)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: VisitsHolder, position: Int) {
        holder.image.downloadImage(images[position].src.tiny)
    }

    inner class VisitsHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val image: ImageView = itemView.findViewById(R.id.image)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onClick(images[layoutPosition].src.original)
        }
    }
}