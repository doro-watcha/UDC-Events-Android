package com.goddoro.udc.views.classShop

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.Date
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ItemDateBinding
import com.goddoro.udc.databinding.ItemMainClassBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


class DateListAdapter( private val context : Context): RecyclerView.Adapter<DateListAdapter.DateViewHolder>() {

    var currentIndex = 0

    private val onClick: PublishSubject<Date> = PublishSubject.create()
    val clickEvent: Observable<Date> = onClick

    private val diff = object : DiffUtil.ItemCallback<Date>() {
        override fun areItemsTheSame(oldItem: Date, newItem: Date): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(oldItem: Date, newItem: Date): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Date>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDateBinding.inflate(inflater, parent, false)

        return DateViewHolder(binding)
    }

    override fun getItemCount(): Int = 7


    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        return holder.bind(differ.currentList[position])
    }

    inner class DateViewHolder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.root.setOnClickListener {

                currentIndex = layoutPosition
                notifyDataSetChanged()
                onClick.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind(item: Date) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            if ( currentIndex == layoutPosition) {
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.border_black_background)
                binding.txtDate.setTextColor(context.getColor(R.color.white))
                binding.txtDay.setTextColor(context.getColor(R.color.white))



            } else {
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.border_white_background)
                binding.txtDay.setTextColor(context.getColor(R.color.black))
                binding.txtDate.setTextColor(context.getColor(R.color.black))

            }


        }
    }

}

@BindingAdapter("app:recycler_view_date_list")
fun RecyclerView.setDateList(items: List<Date>?) {

    (adapter as? DateListAdapter)?.run {
        this.submitItems(items)
    }
}
