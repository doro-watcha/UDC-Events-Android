package com.goddoro.udc.views

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
import com.goddoro.common.common.observeOnce
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.ToastUtil
import com.goddoro.map.R
import com.goddoro.udc.databinding.DialogGenrePickBinding
import com.goddoro.udc.databinding.DialogPopUpBinding
import com.goddoro.udc.views.upload.danceClass.genre.GenreListAdapter
import com.goddoro.udc.views.upload.danceClass.genre.GenrePickViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt


class PopupDialog  : DialogFragment(){

    private val TAG = PopupDialog::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()


    private var mBinding : DialogPopUpBinding by AutoClearedValue()
    private val viewModel : PopupDialogViewModel by viewModel()

    private val toastUtil : ToastUtil by inject()

    override fun getTheme(): Int = R.style.Theme_UDC_MapDetailDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return DialogPopUpBinding.inflate(inflater, container, false).run {
            mBinding = this
            this.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = viewModel

        initView()
        observeViewModel()
        setupBroadcast()

    }


    private fun initView() {

//        mBinding.btnConfirm.setOnDebounceClickListener {
//            Broadcast.pickGenreBroadcast.onNext(viewModel.selectedGenre.value!!)
//            dismiss()
//        }

    }

    private fun observeViewModel() {

        viewModel.apply {

            clickDismissButton.observeOnce(this@PopupDialog){
                dismiss()
            }

//            errorInvoked.observe(this@GenrePickDialog){
//                debugE(TAG, it.message)
//            }
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
        val height = (point.y * 0.5f).roundToInt()
        dialog?.window?.setLayout(width, height)
    }

    private fun setupBroadcast() {

    }
    companion object {
        fun show(fm: FragmentManager) {
            val dialog = PopupDialog()
            dialog.show(fm, dialog.tag)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

}