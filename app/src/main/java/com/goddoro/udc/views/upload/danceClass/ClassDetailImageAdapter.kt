package com.goddoro.udc.views.upload.danceClass

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.common.debugE
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ItemClassDetailImageBinding
import com.goddoro.udc.databinding.ItemSearchRecommendationBinding
import com.goddoro.udc.views.search.SearchRecommendAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 11/28/20
 */

class ClassDetailImageAdapter ( private val context : Context):
    RecyclerView.Adapter<ClassDetailImageAdapter.ImageViewHolder>() {

    private val TAG = ClassDetailImageAdapter::class.java.simpleName

    var mainIndex = 0


    private val onClick: PublishSubject<Int> = PublishSubject.create()
    val clickEvent: Observable<Int> = onClick

    private val diff = object : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Uri>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemClassDetailImageBinding.inflate(inflater, parent, false)

        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size



    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class ImageViewHolder(private val binding: ItemClassDetailImageBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {


            binding.root.setOnDebounceClickListener {

                onClick.onNext(layoutPosition)
                mainIndex = layoutPosition
                notifyDataSetChanged()
            }

        }

        fun bind(item: Uri) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


            if ( layoutPosition == mainIndex) {
                binding.imgMain.visibility = View.VISIBLE
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.border_green_background)

            } else {
                binding.imgMain.visibility = View.GONE
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.border_white_background)

            }


        }
    }

}

@BindingAdapter("app:recyclerview_class_detail_images")
fun RecyclerView.setClassDetailImages(items: List<Uri>?) {
    (adapter as? ClassDetailImageAdapter)?.run {
        this.submitItems(items)
    }
}
