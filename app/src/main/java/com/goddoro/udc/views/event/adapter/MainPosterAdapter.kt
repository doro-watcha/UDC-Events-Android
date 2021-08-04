package com.goddoro.udc.views.event.adapter
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
import com.goddoro.common.data.model.Event
import com.goddoro.udc.databinding.ItemMainPosterBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 2020/07/28
 */

class MainPosterAdapter:
    RecyclerView.Adapter<MainPosterAdapter.MainPosterHolder>() {

    private val TAG = MainPosterAdapter::class.java.simpleName


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPosterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainPosterBinding.inflate(inflater, parent, false)

        return MainPosterHolder(binding)
    }

    override fun getItemCount(): Int = Integer.MAX_VALUE

    override fun onBindViewHolder(holder: MainPosterHolder, position: Int) = holder.bind(differ.currentList[position % differ.currentList.size ])

    inner class MainPosterHolder(private val binding: ItemMainPosterBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.posterCard.setOnDebounceClickListener {
                onClick.onNext(Pair(differ.currentList[layoutPosition % differ.currentList.size],binding.poster))
            }


        }

        fun bind(item: Event) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_main_posters")
fun ViewPager2.setMainPosters(items: List<Event>?) {
    (adapter as? MainPosterAdapter)?.run {
        this.submitItems(items)
    }
}

