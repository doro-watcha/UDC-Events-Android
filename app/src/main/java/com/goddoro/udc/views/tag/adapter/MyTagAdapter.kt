package com.goddoro.udc.views.tag.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.data.model.Tag
import com.goddoro.udc.databinding.ItemSettingTagBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 2020/11/14
 */

class MyTagAdapter: RecyclerView.Adapter<MyTagAdapter.MyTagViewHolder>() {


    private val onClick: PublishSubject<String> = PublishSubject.create()
    val clickEvent: Observable<String> = onClick

    private val diff = object : DiffUtil.ItemCallback<Tag>() {
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Tag>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTagViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSettingTagBinding.inflate(inflater, parent, false)

        return MyTagViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: MyTagViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class MyTagViewHolder(private val binding: ItemSettingTagBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {


        }

        fun bind(item: Tag) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_my_tag")
fun RecyclerView.setMyTagLists(items: List<Tag>?) {
    (adapter as? MyTagAdapter)?.run {
        this.submitItems(items)
    }
}