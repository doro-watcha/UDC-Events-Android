package com.goddoro.udc.views.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.goddoro.udc.databinding.FragmentFindEmailBinding
import com.goddoro.udc.databinding.FragmentFindPasswordBinding
import dagger.android.support.DaggerFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import androidx.fragment.app.Fragment

/**
 * created By DORO 2020/09/12
 */

class PasswordFindFragment : Fragment() {

    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentFindPasswordBinding

    /**
     * ViewModel Instance
     */
    private val mViewModel: AuthViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentFindPasswordBinding.inflate(inflater,container,false).also{mBinding=it}.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
    }

    companion object {
        fun newInstance() = PasswordFindFragment()
    }
}