package com.practise.zenup.base

import android.view.View

interface AppBaseFrag {
    fun showToast(msg : String)
    fun showProgressBar(show : Boolean)
    fun hideKeyboard(view: View)
}