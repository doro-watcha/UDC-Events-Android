package com.goddoro.udc.views.classShop

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
import com.goddoro.common.data.model.DanceClass
import com.goddoro.udc.databinding.ItemDayOfClassBinding
import com.goddoro.udc.databinding.ItemMainClassBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent

class DayOfClassAdapter: RecyclerView.Adapter<DayOfClassAdapter.ClassViewHolder>() {


    private val onClick: PublishSubject<Pair<DanceClass, ImageView>> = PublishSubject.create()
    val clickEvent: Observable<Pair<DanceClass, ImageView>> = onClick

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDayOfClassBinding.inflate(inflater, parent, false)

        return ClassViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
            return holder.bind(differ.currentList[position])
    }

    inner class ClassViewHolder(private val binding: ItemDayOfClassBinding) :
        RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.root.setOnDebounceClickListener {
                if (differ.currentList.size != 0)
                    onClick.onNext(
                        Pair(
                            differ.currentList[layoutPosition % differ.currentList.size],
                            binding.imgPoster
                        )
                    )
            }

        }

        fun bind(item: DanceClass) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_day_of_classes")
fun RecyclerView.setDayOfClasses(items: List<DanceClass>?) {


    (adapter as? DayOfClassAdapter)?.run {
        this.submitItems(items)
    }
}
