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
import com.goddoro.udc.databinding.ItemGridPosterBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 2020/08/03
 */

class GridPosterAdapter:
    RecyclerView.Adapter<GridPosterAdapter.GridPosterHolder>() {


    private val onClick: PublishSubject<Pair<Event, ImageView>> = PublishSubject.create()
    val clickEvent: Observable<Pair<Event,ImageView>> = onClick

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridPosterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGridPosterBinding.inflate(inflater, parent, false)

        return GridPosterHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: GridPosterHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class GridPosterHolder(private val binding: ItemGridPosterBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.root.setOnDebounceClickListener {
                onClick.onNext(Pair(differ.currentList[layoutPosition], binding.gridPoster))
            }

        }

        fun bind(item: Event) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_grid_posters")
fun RecyclerView.setGridPosters(items: List<Event>?) {
    (adapter as? GridPosterAdapter)?.run {
        this.submitItems(items)
    }
}
