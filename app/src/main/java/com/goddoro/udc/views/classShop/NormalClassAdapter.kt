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
import com.goddoro.common.data.model.Event
import com.goddoro.udc.databinding.ItemEventCollectionBinding
import com.goddoro.udc.databinding.ItemNormalClassBinding
import com.goddoro.udc.views.profile.EventCollectionAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 2020/10/24
 */

class NormalClassAdapter: RecyclerView.Adapter<NormalClassAdapter.NormalClassViewHolder>() {


    private val onClick: PublishSubject<Pair<DanceClass, ImageView>> = PublishSubject.create()
    val clickEvent: Observable<Pair<DanceClass,ImageView>> = onClick

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalClassViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNormalClassBinding.inflate(inflater, parent, false)

        return NormalClassViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: NormalClassViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class NormalClassViewHolder(private val binding: ItemNormalClassBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.root.setOnDebounceClickListener{
                onClick.onNext(Pair(differ.currentList[layoutPosition],binding.videoThumbnail))
            }

        }

        fun bind(item: DanceClass) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_normal_class")
fun RecyclerView.setNormalClass(items: List<DanceClass>?) {
    (adapter as? NormalClassAdapter)?.run {
        this.submitItems(items)
    }
}
