package com.goddoro.udc.views.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.common.common.debugE
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.udc.databinding.ItemHistoryBinding
import com.goddoro.udc.databinding.ItemSearchRecommendationBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


/**
 * created By DORO 11/21/20
 */


class SearchRecommendAdapter:
    RecyclerView.Adapter<SearchRecommendAdapter.RecommendationViewHolder>() {

    private val TAG = SearchRecommendAdapter::class.java.simpleName


    private val onClick: PublishSubject<String> = PublishSubject.create()
    val clickEvent: Observable<String> = onClick

    private val diff = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<String>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchRecommendationBinding.inflate(inflater, parent, false)

        return RecommendationViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size



    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class RecommendationViewHolder(private val binding: ItemSearchRecommendationBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {


            binding.root.setOnDebounceClickListener {
                onClick.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind(item: String) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            debugE(TAG, "Fuck")
            debugE(TAG,differ.currentList)


        }
    }

}

@BindingAdapter("app:recyclerview_search_recommendations")
fun RecyclerView.setRecommendationsAdapter(items: List<String>?) {
    (adapter as? SearchRecommendAdapter)?.run {
        this.submitItems(items)
    }
}
