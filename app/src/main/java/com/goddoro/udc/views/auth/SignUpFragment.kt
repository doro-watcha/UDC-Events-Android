package com.goddoro.udc.views.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goddoro.udc.R
import com.goddoro.udc.databinding.FragmentSignUpBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import androidx.fragment.app.Fragment


/**
 * created By DORO 2020/09/12
 */

class SignUpFragment : Fragment() {

    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentSignUpBinding

    /**
     * ViewModel Instance
     */
    private val mViewModel: AuthViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSignUpBinding.inflate(inflater,container,false).also{mBinding=it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        initView()
        observeViewModel()
    }

    private fun initView() {

        mBinding.apply {

            txtId.text = resources.getString(R.string.common_email) + " *"
            txtPassword.text = resources.getString(R.string.common_password) + " *"
        }
    }

    private fun observeViewModel() {



    }


    companion object {
        fun newInstance() = SignUpFragment()
    }

}