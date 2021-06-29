package ru.gressor.simplemap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<Binding : ViewBinding> : Fragment() {

    private var _binding: Binding? = null
    protected val binding: Binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = getBindingObject(inflater, container)
        .also { _binding = it }
        .root

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    abstract fun getBindingObject(inflater: LayoutInflater, container: ViewGroup?): Binding
}