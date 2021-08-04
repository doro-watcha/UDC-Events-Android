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
import com.goddoro.common.data.model.Target
import com.goddoro.udc.databinding.ItemFilterTargetBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


class TargetFilterAdapter:
    RecyclerView.Adapter<TargetFilterAdapter.TargetViewHolder>() {

    private val TAG = TargetFilterAdapter::class.java.simpleName


    private val onClick: PublishSubject<Target> = PublishSubject.create()
    val clickEvent: Observable<Target> = onClick

    private val diff = object : DiffUtil.ItemCallback<Target>() {
        override fun areItemsTheSame(oldItem: Target, newItem: Target): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Target, newItem: Target): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Target>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterTargetBinding.inflate(inflater, parent, false)

        return TargetViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size



    override fun onBindViewHolder(holder: TargetViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class TargetViewHolder(private val binding: ItemFilterTargetBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.root.setOnDebounceClickListener {
                onClick.onNext(differ.currentList[layoutPosition])
            }



        }

        fun bind(item: Target) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            debugE(TAG, "Fuck")
            debugE(TAG,differ.currentList)


        }
    }

}

@BindingAdapter("app:filter_target_list")
fun RecyclerView.setTargetFilters(items: List<Target>?) {
    (adapter as? TargetFilterAdapter)?.run {
        this.submitItems(items)
    }
}
