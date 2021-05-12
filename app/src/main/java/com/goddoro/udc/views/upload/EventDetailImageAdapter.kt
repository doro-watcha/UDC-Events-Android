package com.goddoro.udc.views.upload

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.common.debugE
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.udc.databinding.ItemEventDetailImageBinding
import com.goddoro.udc.databinding.ItemSearchRecommendationBinding
import com.goddoro.udc.views.search.SearchRecommendAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 11/28/20
 */

class EventDetailImageAdapter:
    RecyclerView.Adapter<EventDetailImageAdapter.ImageViewHolder>() {

    private val TAG = SearchRecommendAdapter::class.java.simpleName


    private val onClick: PublishSubject<Uri> = PublishSubject.create()
    val clickEvent: Observable<Uri> = onClick

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
        val binding = ItemEventDetailImageBinding.inflate(inflater, parent, false)

        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size



    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class ImageViewHolder(private val binding: ItemEventDetailImageBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {


            binding.root.setOnDebounceClickListener {
                onClick.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind(item: Uri) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_event_detail_images")
fun RecyclerView.setEventDetailImages(items: List<Uri>?) {
    (adapter as? EventDetailImageAdapter)?.run {
        this.submitItems(items)
    }
}
