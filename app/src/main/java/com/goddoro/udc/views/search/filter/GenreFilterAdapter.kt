package com.goddoro.udc.views.search.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.common.debugE
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.data.model.Genre
import com.goddoro.udc.databinding.ItemFilterGenreBinding
import com.goddoro.udc.databinding.ItemHistoryBinding
import com.goddoro.udc.views.search.SearchHistoryAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent

class GenreFilterAdapter:
    RecyclerView.Adapter<GenreFilterAdapter.GenreViewHolder>() {

    private val TAG = GenreFilterAdapter::class.java.simpleName


    private val onClick: PublishSubject<Genre> = PublishSubject.create()
    val clickEvent: Observable<Genre> = onClick

    private val diff = object : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Genre>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterGenreBinding.inflate(inflater, parent, false)

        return GenreViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size



    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class GenreViewHolder(private val binding: ItemFilterGenreBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.root.setOnDebounceClickListener {
                onClick.onNext(differ.currentList[layoutPosition])
            }



        }

        fun bind(item: Genre) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            debugE(TAG, "Fuck")
            debugE(TAG,differ.currentList)


        }
    }

}

@BindingAdapter("app:filter_genre_list")
fun RecyclerView.setGenreFilters(items: List<Genre>?) {
    (adapter as? GenreFilterAdapter)?.run {
        this.submitItems(items)
    }
}
