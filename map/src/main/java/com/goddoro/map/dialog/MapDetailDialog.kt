package com.goddoro.map.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.loadUrlAsync
import com.goddoro.common.common.widget.GridSpacingItemDecoration
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.model.NaverItem
import com.goddoro.common.util.Navigator
import com.goddoro.map.R
import com.goddoro.map.databinding.DialogMapDetailBinding
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt


/**
 * created By DORO 2020/09/14
 */

class MapDetailDialog ( private val event : Event) : DialogFragment(){

    companion object {
        fun show(fm: FragmentManager , item : Event) {
            val dialog = MapDetailDialog(item)
            dialog.show(fm, dialog.tag)
        }
    }

    private var mBinding : DialogMapDetailBinding by AutoClearedValue()

    override fun getTheme(): Int = R.style.Theme_UDC_MapDetailDialog


    private val navigator : Navigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return DialogMapDetailBinding.inflate(inflater, container, false).run {
            mBinding = this
            this.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.dialog = this
        mBinding.item = event

        initView()
        setupRecyclerView()

    }

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {
            val mVideoGridLayoutManager: LinearLayoutManager = GridLayoutManager(context, 2)
            val spacingTop = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()
            val spacingLeft = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()

            val mVideoGridSpacing =
                GridSpacingItemDecoration(2, spacingLeft, spacingTop, 0)

            layoutManager = mVideoGridLayoutManager
            addItemDecoration(mVideoGridSpacing)
            setHasFixedSize(true)

            adapter = EventDialogImageAdapter()

        }
    }

    private fun initView() {

        mBinding.btnDetailEvent.setOnDebounceClickListener {
            navigator.startEventDetailActivity(requireActivity(),event,mBinding.image)
        }
    }

    /**
     * For Size
     */
    override fun onResume() {
        super.onResume()

        val wm = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        val width = (point.x * 0.8f).roundToInt()
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }
}