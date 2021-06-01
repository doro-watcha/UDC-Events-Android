package com.goddoro.udc.views.classShop.detail

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
import com.goddoro.udc.databinding.ItemGeneralImageBinding
import com.goddoro.udc.databinding.ItemMainClassBinding
import com.goddoro.udc.views.classShop.MainClassAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 2020/10/31
 */

class ArtistProfileAdapter: RecyclerView.Adapter<ArtistProfileAdapter.ProfileViewHolder>() {


    private val onClick: PublishSubject<DanceClass> = PublishSubject.create()
    val clickEvent: Observable<DanceClass> = onClick

    private val diff = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<Int>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGeneralImageBinding.inflate(inflater, parent, false)

        return ProfileViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class ProfileViewHolder(private val binding: ItemGeneralImageBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {


        }

        fun bind(item: Int) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


            binding.image.setImageResource(item)
        }
    }

}

@BindingAdapter("app:recyclerview_artist_profiles")
fun RecyclerView.setArtistProfiles(items: List<Int>?) {
    (adapter as? ArtistProfileAdapter)?.run {
        this.submitItems(items)
    }
}
