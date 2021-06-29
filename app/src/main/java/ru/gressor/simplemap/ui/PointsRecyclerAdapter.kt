package ru.gressor.simplemap.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.gressor.simplemap.databinding.ItemPointBinding
import ru.gressor.simplemap.entities.Point

class PointsRecyclerAdapter(
    pointsList: List<Point>
) : RecyclerView.Adapter<PointsRecyclerAdapter.PointViewHolder>() {
    private val points = pointsList.toMutableList()

    fun getPointsList(): List<Point> = points

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PointViewHolder(
            ItemPointBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(points[position])
    }

    override fun getItemCount() = points.size

    inner class PointViewHolder(private val binding: ItemPointBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(point: Point) {
            with(binding) {
                pointTitle.text = point.title
                pointSnippet.text = point.snippet
                pointLatLng.text = "latitude: ${point.latLng.latitude},\nlongitude: ${point.latLng.longitude}"

                deletePoint.setOnClickListener {
                    points.remove(point)
                    notifyDataSetChanged()
                }
            }
        }
    }
}