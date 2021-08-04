package com.goddoro.udc.views.search

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.goddoro.common.Broadcast
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.debugE
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.ToastUtil
import com.goddoro.map.R
import com.goddoro.udc.databinding.DialogAcademyPickBinding
import com.goddoro.udc.databinding.DialogFilterBinding
import com.goddoro.udc.util.startActivity
import com.goddoro.udc.views.search.filter.FilterViewModel
import com.goddoro.udc.views.search.filter.GenreFilterAdapter
import com.goddoro.udc.views.search.filter.TargetFilterAdapter
import com.goddoro.udc.views.upload.academy.AcademyListAdapter
import com.goddoro.udc.views.upload.academy.AcademyPickDialog
import com.goddoro.udc.views.upload.academy.AcademyPickViewModel
import com.goddoro.udc.views.upload.academy.UploadAcademyActivity
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class FilterDialog : DialogFragment() {

    private val TAG = FilterDialog::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()


    private var mBinding : DialogFilterBinding by AutoClearedValue()
    private val viewModel : FilterViewModel by viewModel()

    private val toastUtil : ToastUtil by inject()

    override fun getTheme(): Int = R.style.BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return DialogFilterBinding.inflate(inflater, container, false).run {
            mBinding = this
            this.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = viewModel

        setupFilter()

    }

    private fun setupFilter() {

        mBinding.genreList.apply {

            adapter = GenreFilterAdapter()
        }

        mBinding.targetList.apply {
            adapter = TargetFilterAdapter()
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

    companion object {
        fun show(fm: FragmentManager) {
            val dialog = AcademyPickDialog()
            dialog.show(fm, dialog.tag)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }


}