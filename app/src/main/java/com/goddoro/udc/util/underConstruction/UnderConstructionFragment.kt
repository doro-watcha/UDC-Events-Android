package com.goddoro.udc.util.underConstruction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.udc.databinding.FragmentUnderConstructionBinding

class UnderConstructionFragment : Fragment() {

    private lateinit var mBinding : FragmentUnderConstructionBinding

    private lateinit var message : String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUnderConstructionBinding.inflate(inflater,container,false).also { mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner

        message = arguments?.getString(ARG_MESSAGE) ?: ""

        mBinding.txtMessage.text = message
    }

    companion object {

        const val ARG_MESSAGE = "ARG_MESSAGE"
        fun newInstance( message : String ) : UnderConstructionFragment {
            val fragment = UnderConstructionFragment()
            val bundle = Bundle()
            bundle.putString(ARG_MESSAGE,message)
            fragment.arguments = bundle
            return fragment
        }
    }
}
