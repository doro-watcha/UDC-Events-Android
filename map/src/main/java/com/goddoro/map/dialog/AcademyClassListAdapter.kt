package com.goddoro.map.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.data.model.Academy
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.Date
import com.goddoro.common.data.model.SketchImage
import com.goddoro.map.databinding.ItemAcademyClassBinding
import com.goddoro.map.databinding.ItemImageBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


class AcademyClassListAdapter: RecyclerView.Adapter<AcademyClassListAdapter.AcademyClassViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcademyClassViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAcademyClassBinding.inflate(inflater, parent, false)

        return AcademyClassViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: AcademyClassViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class AcademyClassViewHolder(private val binding: ItemAcademyClassBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.root.setOnDebounceClickListener {
                onClick.onNext(Pair(differ.currentList[layoutPosition],binding.imgPoster))
            }
        }

        fun bind(item: DanceClass) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

        }
    }

}

@BindingAdapter("app:recyclerview_academy_class_list")
fun RecyclerView.setAcademyClassList(items: List<DanceClass>?) {
    (adapter as? AcademyClassListAdapter)?.run {
        this.submitItems(items)
    }
}
