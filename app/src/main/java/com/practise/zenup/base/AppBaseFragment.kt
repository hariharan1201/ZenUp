package com.practise.zenup.base

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.practise.zenup.R

abstract class AppBaseFragment : Fragment(), AppBaseFrag {
    private var loadingView: View? = null

    override fun showToast(msg : String){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar(show: Boolean) {
        val rootView = requireView() as ViewGroup
        if (loadingView == null) {
            val inflater = LayoutInflater.from(requireContext())
            loadingView = inflater.inflate(R.layout.progress_loading, rootView, false)
            rootView.addView(loadingView)
        }
        loadingView?.visibility = if (show) View.VISIBLE else View.GONE
        Log.d("FIREBASE", "Progress bar visible: $show")
    }

    override fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}