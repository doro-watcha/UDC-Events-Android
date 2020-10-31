package com.goddoro.udc.views.classShop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.data.model.DanceClass
import com.goddoro.udc.databinding.ItemEventCollectionBinding
import com.goddoro.udc.databinding.ItemMainClassBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 2020/10/24
 */

class MainClassAdapter: RecyclerView.Adapter<MainClassAdapter.MainClassViewHolder>() {


    private val onClick: PublishSubject<DanceClass> = PublishSubject.create()
    val clickEvent: Observable<DanceClass> = onClick

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainClassViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainClassBinding.inflate(inflater, parent, false)

        return MainClassViewHolder(binding)
    }

    override fun getItemCount(): Int = Integer.MAX_VALUE

    fun getCenterPage(position: Int = 0) = Integer.MAX_VALUE / 2 + position

    override fun onBindViewHolder(holder: MainClassViewHolder, position: Int) = holder.bind(differ.currentList[position % differ.currentList.size ])

    inner class MainClassViewHolder(private val binding: ItemMainClassBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.imgPoster.setOnDebounceClickListener {
                onClick.onNext(differ.currentList[layoutPosition % differ.currentList.size])
            }

        }

        fun bind(item: DanceClass) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_main_class")
fun ViewPager2.setMainClassEvents(items: List<DanceClass>?) {
    (adapter as? MainClassAdapter)?.run {
        this.submitItems(items)
    }
}
