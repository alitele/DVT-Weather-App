package com.ciklum.weatherapp.features.main.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class BaseFragment : Fragment() {

    inline fun <reified T : ViewDataBinding> getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?, @LayoutRes layoutId: Int
    ): T {
        return DataBindingUtil.inflate(inflater, layoutId, parent, false)
    }
}