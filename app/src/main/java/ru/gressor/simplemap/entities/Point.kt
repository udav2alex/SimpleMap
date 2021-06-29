package ru.gressor.simplemap.entities

import com.google.android.gms.maps.model.LatLng

data class Point(
    var latLng: LatLng,
    var title: String = "",
    var snippet: String = ""
)