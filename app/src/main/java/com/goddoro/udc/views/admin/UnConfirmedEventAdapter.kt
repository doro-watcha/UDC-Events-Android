package com.goddoro.udc.views.admin

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
import com.goddoro.udc.databinding.ItemUnconfirmedEventBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 5/2/21
 */



class UnConfirmedEventAdapter:
    RecyclerView.Adapter<UnConfirmedEventAdapter.UnConfirmedViewHolder>() {

    private val onClickConfirm: PublishSubject<Event> = PublishSubject.create()
    val clickConfirm: Observable<Event> = onClickConfirm

    private val onClick : PublishSubject<Pair<Event,ImageView>> = PublishSubject.create()
    val clickEvent : Observable<Pair<Event,ImageView>> = onClick


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnConfirmedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUnconfirmedEventBinding.inflate(inflater, parent, false)

        return UnConfirmedViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: UnConfirmedViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class UnConfirmedViewHolder(private val binding: ItemUnconfirmedEventBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.btnConfirm.setOnDebounceClickListener {
                onClickConfirm.onNext(differ.currentList[layoutPosition])
            }

            binding.root.setOnDebounceClickListener{
                onClick.onNext(Pair(differ.currentList[layoutPosition],binding.imgPoster))
            }

        }

        fun bind(item: Event) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_unconfirmed_event")
fun RecyclerView.setUnConfirmedEvents(items: List<Event>?) {
    (adapter as? UnConfirmedEventAdapter)?.run {
        this.submitItems(items)
    }
}
