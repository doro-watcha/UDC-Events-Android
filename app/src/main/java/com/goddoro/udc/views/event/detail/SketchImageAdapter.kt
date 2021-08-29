package com.goddoro.udc.views.event.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.common.loadUrlAsync
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.SketchImage
import com.goddoro.udc.databinding.ItemGeneralImageBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 11/21/20
 */

class SketchImageAdapter: RecyclerView.Adapter<SketchImageAdapter.EventImageViewHolder>() {


    private val onClick: PublishSubject<DanceClass> = PublishSubject.create()
    val clickEvent: Observable<DanceClass> = onClick

    private val diff = object : DiffUtil.ItemCallback<SketchImage>() {
        override fun areItemsTheSame(oldItem: SketchImage, newItem: SketchImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SketchImage, newItem: SketchImage): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<SketchImage>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGeneralImageBinding.inflate(inflater, parent, false)

        return EventImageViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: EventImageViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class EventImageViewHolder(private val binding: ItemGeneralImageBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {


        }

        fun bind(item: SketchImage) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            binding.image.loadUrlAsync(item.sketchImage)

        }
    }

}

@BindingAdapter("app:recyclerview_sketch_images")
fun RecyclerView.setSketchImages(items: List<SketchImage>?) {
    (adapter as? SketchImageAdapter)?.run {
        this.submitItems(items)
    }
}
