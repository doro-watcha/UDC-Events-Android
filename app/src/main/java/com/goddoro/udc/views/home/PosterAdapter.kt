package com.goddoro.udc.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.data.model.Event
import com.goddoro.udc.databinding.ItemPosterBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 2020/08/03
 */

class PosterAdapter:
    RecyclerView.Adapter<PosterAdapter.PosterHolder>() {


    private val onClick: PublishSubject<Pair<Int,ImageView>> = PublishSubject.create()
    val clickEvent: Observable<Pair<Int,ImageView>> = onClick

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPosterBinding.inflate(inflater, parent, false)

        return PosterHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: PosterHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class PosterHolder(private val binding: ItemPosterBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.imgPoster.setOnDebounceClickListener {
                onClick.onNext(Pair(differ.currentList[layoutPosition].id,binding.poster))
            }

        }

        fun bind(item: Event) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_posters")
fun RecyclerView.setPosters(items: List<Event>?) {
    (adapter as? PosterAdapter)?.run {
        this.submitItems(items)
    }
}
