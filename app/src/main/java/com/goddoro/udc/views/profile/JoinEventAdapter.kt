package com.goddoro.udc.views.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.data.data.Event
import com.goddoro.udc.databinding.ItemBlurredImageBinding
import com.goddoro.udc.databinding.ItemEventCollectionBinding
import com.goddoro.udc.databinding.ItemJoinEventBinding
import com.goddoro.udc.views.home.BlurredAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 2020/09/12
 */

class JoinEventAdapter: RecyclerView.Adapter<JoinEventAdapter.JoinEventHolder>() {


    private val onClick: PublishSubject<String> = PublishSubject.create()
    val clickEvent: Observable<String> = onClick

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoinEventHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemJoinEventBinding.inflate(inflater, parent, false)

        return JoinEventHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: JoinEventHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class JoinEventHolder(private val binding: ItemJoinEventBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {


        }

        fun bind(item: Event) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_join_event")
fun RecyclerView.setJoinEvents(items: List<Event>?) {
    (adapter as? JoinEventAdapter)?.run {
        this.submitItems(items)
    }
}
