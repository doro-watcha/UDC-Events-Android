package com.goddoro.udc.views.search.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.common.debugE
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.data.model.Event
import com.goddoro.udc.databinding.ItemSearchEventBinding
import com.goddoro.udc.databinding.ItemSearchRecommendationBinding
import com.goddoro.udc.views.search.SearchRecommendAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 11/28/20
 */

class SearchEventAdapter:
    RecyclerView.Adapter<SearchEventAdapter.SearchEventHolder>() {

    private val TAG = SearchRecommendAdapter::class.java.simpleName


    private val onClick: PublishSubject<Pair<Event,ImageView>> = PublishSubject.create()
    val clickEvent: Observable<Pair<Event,ImageView>> = onClick

    private val diff = object : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
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
        val binding = ItemSearchEventBinding.inflate(inflater, parent, false)

        return SearchEventHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size



    override fun onBindViewHolder(holder: SearchEventHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class SearchEventHolder(private val binding: ItemSearchEventBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {


            binding.root.setOnDebounceClickListener {
                onClick.onNext(Pair(differ.currentList[layoutPosition],binding.imgThumbnail))
            }

        }

        fun bind(item: Event) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            debugE(TAG, "Fuck")
            debugE(TAG,differ.currentList)


        }
    }

}

@BindingAdapter("app:recyclerview_search_events")
fun RecyclerView.setSearchEvents(items: List<Event>?) {
    (adapter as? SearchEventAdapter)?.run {
        this.submitItems(items)
    }
}
