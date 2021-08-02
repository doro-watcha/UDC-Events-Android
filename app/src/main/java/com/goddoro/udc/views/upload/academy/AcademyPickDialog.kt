package com.goddoro.udc.views.upload.academy

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
import com.goddoro.common.Broadcast
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.debugE
import com.goddoro.common.common.widget.GridSpacingItemDecoration
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.Navigator
import com.goddoro.map.R
import com.goddoro.map.databinding.DialogMapDetailBinding
import com.goddoro.udc.databinding.DialogAcademyPickBinding
import com.goddoro.udc.util.startActivity
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class AcademyPickDialog  : DialogFragment(){

    private val TAG = AcademyPickDialog::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()


    private var mBinding : DialogAcademyPickBinding by AutoClearedValue()
    private val viewModel : AcademyPickViewModel by viewModel()

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

        return DialogAcademyPickBinding.inflate(inflater, container, false).run {
            mBinding = this
            this.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = viewModel

        initView()
        setupRecyclerView()
        observeViewModel()
        setupBroadcast()

        viewModel.listAcademies()

    }

    private fun setupRecyclerView() {

        mBinding.recyclerView.apply {

            adapter = AcademyListAdapter(context).apply {

                clickEvent.subscribe{
                    viewModel.selectedAcademy.value = it
                }.disposedBy(compositeDisposable)

            }

        }
    }

    private fun initView() {

        mBinding.btnConfirm.setOnDebounceClickListener {
            Broadcast.pickAcademyBroadcast.onNext(viewModel.selectedAcademy.value!!)
            dismiss()
        }

        mBinding.btnAddAcademy.setOnDebounceClickListener {
            startActivity(UploadAcademyActivity::class)
        }

    }

    private fun observeViewModel() {

        viewModel.apply {

            errorInvoked.observe(this@AcademyPickDialog){
                debugE(TAG, it.message)
            }
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

    private fun setupBroadcast() {

        Broadcast.apply {

            registerAcademyCompleteBroadcast.subscribe{
                viewModel.listAcademies()
            }.disposedBy(compositeDisposable)
        }
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