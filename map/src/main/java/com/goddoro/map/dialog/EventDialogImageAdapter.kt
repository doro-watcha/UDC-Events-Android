package com.goddoro.map.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.data.model.DanceClass
import com.goddoro.map.databinding.ItemImageBinding
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


class EventDialogImageAdapter: RecyclerView.Adapter<EventDialogImageAdapter.EventImageViewHolder>() {



    private val diff = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Int>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)

        return EventImageViewHolder(binding)
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: EventImageViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class EventImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {


        }

        fun bind(item: Int) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            binding.image.setImageResource(item)

        }
    }

}

@BindingAdapter("app:recyclerview_event_dialog_images")
fun RecyclerView.setEventDialogImages(items: List<Int>?) {
    (adapter as? EventDialogImageAdapter)?.run {
        this.submitItems(items)
    }
}
