package ru.gressor.simplemap.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gressor.simplemap.entities.Point

class MapVModelFactory(
    private val pointsList: MutableList<Point>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            MapVModel::class.java -> MapVModel(pointsList)
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T
}