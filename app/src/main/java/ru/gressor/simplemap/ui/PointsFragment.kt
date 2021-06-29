package ru.gressor.simplemap.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.gressor.simplemap.databinding.FragmentPointsBinding

class PointsFragment : BaseFragment<FragmentPointsBinding>() {

    override fun getBindingObject(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPointsBinding.inflate(inflater, container, false)
}