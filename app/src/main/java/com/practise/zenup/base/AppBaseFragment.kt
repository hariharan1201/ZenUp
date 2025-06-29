package com.practise.zenup.base

import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class AppBaseFragment : Fragment(), AppBaseFrag {
    override fun showToast(msg : String){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}