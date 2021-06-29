package ru.gressor.simplemap.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import ru.gressor.simplemap.databinding.FragmentPointsBinding
import ru.gressor.simplemap.entities.Point

class PointsFragment : BaseFragment<FragmentPointsBinding>() {
    private val recyclerView by lazy {
        binding.pointsRecycler
    }
    private val points: MutableList<Point> by lazy {
        val pointsList = mutableListOf<Point>()
        (arguments?.getParcelableArray(KEY_POINTS_LIST)?.toMutableList() ?: mutableListOf())
            .forEach {
                pointsList.add(it as Point)
            }
        return@lazy pointsList
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = PointsRecyclerAdapter(points)
    }

    override fun getBindingObject(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPointsBinding.inflate(inflater, container, false)

    companion object {
        fun getInstance(points: List<Point>) = PointsFragment().apply {
            arguments = bundleOf(
                KEY_POINTS_LIST to points.toTypedArray()
            )
        }

        private const val KEY_POINTS_LIST = "KEY_POINTS_LIST"
    }
}