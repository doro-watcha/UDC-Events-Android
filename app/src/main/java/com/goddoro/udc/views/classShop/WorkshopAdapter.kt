package com.goddoro.udc.views.classShop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.Event
import com.goddoro.udc.databinding.ItemEventCollectionBinding
import com.goddoro.udc.databinding.ItemWorkshopBinding
import com.goddoro.udc.views.profile.EventCollectionAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 2020/10/24
 */

class WorkshopAdapter: RecyclerView.Adapter<WorkshopAdapter.WorkshopHolder>() {


    private val onClick: PublishSubject<String> = PublishSubject.create()
    val clickEvent: Observable<String> = onClick

    private val diff = object : DiffUtil.ItemCallback<DanceClass>() {
        override fun areItemsTheSame(oldItem: DanceClass, newItem: DanceClass): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DanceClass, newItem: DanceClass): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<DanceClass>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkshopHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWorkshopBinding.inflate(inflater, parent, false)

        return WorkshopHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: WorkshopHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class WorkshopHolder(private val binding: ItemWorkshopBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {


        }

        fun bind(item: DanceClass) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_event_collection")
fun RecyclerView.setWorkshopClass(items: List<DanceClass>?) {
    (adapter as? WorkshopAdapter)?.run {
        this.submitItems(items)
    }
}
