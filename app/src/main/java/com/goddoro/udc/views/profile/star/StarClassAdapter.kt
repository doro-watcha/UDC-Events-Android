package com.goddoro.udc.views.profile.star

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.data.model.DanceClass
import com.goddoro.udc.databinding.ItemNormalClassBinding
import com.goddoro.udc.databinding.ItemStarClassBinding
import com.goddoro.udc.views.classShop.NormalClassAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


class StarClassAdapter: RecyclerView.Adapter<StarClassAdapter.StarClassViewHolder>() {


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarClassViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStarClassBinding.inflate(inflater, parent, false)

        return StarClassViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: StarClassViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class StarClassViewHolder(private val binding: ItemStarClassBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.root.setOnDebounceClickListener{
                onClick.onNext(Pair(differ.currentList[layoutPosition],binding.imgClass))
            }

        }

        fun bind(item: DanceClass) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_my_star_classes")
fun RecyclerView.setMyStarClasses(items: List<DanceClass>?) {
    (adapter as? StarClassAdapter)?.run {
        this.submitItems(items)
    }
}
