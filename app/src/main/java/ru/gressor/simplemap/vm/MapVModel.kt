package ru.gressor.simplemap.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gressor.simplemap.entities.Point

class MapVModel: ViewModel() {
    private val pointsList = mutableListOf<Point>()
    private val _pointsLiveData = MutableLiveData<MutableList<Point>>(pointsList)
    val pointsLiveData: LiveData<out List<Point>> = _pointsLiveData

    fun newPoint(point: Point) {
        pointsList.add(point)
        _pointsLiveData.value = pointsList
    }
}