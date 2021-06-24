package com.goddoro.map.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.Event
import com.goddoro.map.databinding.ItemSearchMapBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


class EventSearchAdapter:
    RecyclerView.Adapter<EventSearchAdapter.SearchEventHolder>() {

    private val TAG = EventSearchAdapter::class.java.simpleName


    private val onClick: PublishSubject<Pair<Event, ImageView>> = PublishSubject.create()
    val clickEvent: Observable<Pair<Event, ImageView>> = onClick

    private val diff = object : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Event>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchEventHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchMapBinding.inflate(inflater, parent, false)

        return SearchEventHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: SearchEventHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class SearchEventHolder(private val binding: ItemSearchMapBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.root.setOnDebounceClickListener {
                onClick.onNext(Pair(differ.currentList[layoutPosition], binding.imgPoster))
            }


        }

        fun bind(item: Event) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_search_events")
fun RecyclerView.setSearchEvents(items: List<Event>?) {
    (adapter as? EventSearchAdapter)?.run {
        this.submitItems(items)
    }
}