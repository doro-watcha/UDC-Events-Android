package com.goddoro.udc.views.upload.academy

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.data.model.Academy
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ItemAcademyBinding
import com.goddoro.udc.databinding.ItemEventDetailImageBinding
import com.goddoro.udc.views.upload.EventDetailImageAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


class AcademyListAdapter ( val context: Context ): RecyclerView.Adapter<AcademyListAdapter.AcademyViewHolder>() {

    private val TAG = AcademyListAdapter::class.java.simpleName

    var curAcademyIndex = -1


    private val onClick: PublishSubject<Academy> = PublishSubject.create()
    val clickEvent: Observable<Academy> = onClick

    private val diff = object : DiffUtil.ItemCallback<Academy>() {
        override fun areItemsTheSame(oldItem: Academy, newItem: Academy): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Academy, newItem: Academy): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Academy>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcademyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAcademyBinding.inflate(inflater, parent, false)

        return AcademyViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size



    override fun onBindViewHolder(holder: AcademyViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class AcademyViewHolder(private val binding: ItemAcademyBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {


            binding.root.setOnDebounceClickListener {
                curAcademyIndex = layoutPosition
                notifyDataSetChanged()
                onClick.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind(item: Academy) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            if ( curAcademyIndex == layoutPosition) {
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.border_black_background)
                binding.txtName.setTextColor(context.getColor(R.color.white))



            } else {
                binding.root.background = ContextCompat.getDrawable(context, R.drawable.border_white_background)
                binding.txtName.setTextColor(context.getColor(R.color.black))

            }


        }
    }

}

@BindingAdapter("app:recyclerview_academy_list")
fun RecyclerView.setAcademyList(items: List<Academy>?) {
    (adapter as? AcademyListAdapter)?.run {
        this.submitItems(items)
    }
}
