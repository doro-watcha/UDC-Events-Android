package com.goddoro.udc.views.classShop.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.udc.R
import com.goddoro.udc.databinding.DialogClassAskBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * created By DORO 2020/11/14
 */

class ClassAskBottomSheet : BottomSheetDialogFragment() {

    private var mBinding: DialogClassAskBinding by AutoClearedValue()

    private val mViewModel : ClassDetailViewModel by sharedViewModel()

    /**
     * 현재 BottomSheet(Fragment)의 Theme를 얻어오는 메서드를 오버라이딩 해서 우리가 커스텀하게 정의한
     *
     * RoundBottomSheetDialog 라는 Theme.Design.Light.BottomSheetDialog 스타일을 상속한 스타일을 반환하게 해준다.
     */
    override fun getTheme(): Int = R.style.BottomSheetDialog

    /**
     * 커스텀한 Dialog 객체를 반환해주기 위해 BottomSheetDialog 대화상자를 반환해준다.
     *
     * 여기서 중요한 것은 우리가 [getTheme] 메서드를 오버라이딩 해서 반환해주는 Theme 를 이용해서 [BottomSheetDialog]를 생성해주어서
     *
     * 우리가 원하는 Style 대로 BottomSheet 를 동작시킬 수 있다는 것이다.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireActivity(), theme)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogClassAskBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        initView()
    }

    private fun initView() {

        mBinding.apply {

            btnAskSend.setOnDebounceClickListener{
                dismiss()
            }
        }
    }
}