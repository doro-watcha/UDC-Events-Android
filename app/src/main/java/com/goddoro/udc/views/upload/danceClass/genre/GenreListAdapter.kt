package com.goddoro.udc.views.upload.danceClass.genre

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
import com.goddoro.common.data.model.Genre
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ItemAcademyBinding
import com.goddoro.udc.databinding.ItemGenreBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


class GenreListAdapter ( val context: Context): RecyclerView.Adapter<GenreListAdapter.GenreViewHolder>() {

    private val TAG = GenreListAdapter::class.java.simpleName

    var curAcademyIndex = -1


    private val onClick: PublishSubject<Genre> = PublishSubject.create()
    val clickEvent: Observable<Genre> = onClick

    private val diff = object : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Genre>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreBinding.inflate(inflater, parent, false)

        return GenreViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size



    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class GenreViewHolder(private val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {


            binding.root.setOnDebounceClickListener {
                curAcademyIndex = layoutPosition
                notifyDataSetChanged()
                onClick.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind(item: Genre) {
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

@BindingAdapter("app:recyclerview_genre_list")
fun RecyclerView.setAcademyList(items: List<Genre>?) {
    (adapter as? GenreListAdapter)?.run {
        this.submitItems(items)
    }
}
