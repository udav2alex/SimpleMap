package ru.gressor.simplemap.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.gressor.simplemap.databinding.FragmentStartBinding

class StartFragment : BaseFragment<FragmentStartBinding>() {

    override fun getBindingObject(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentStartBinding.inflate(inflater, container, false)
}