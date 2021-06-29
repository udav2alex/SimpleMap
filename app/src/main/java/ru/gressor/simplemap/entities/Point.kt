package ru.gressor.simplemap.entities

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class Point(
    var latLng: LatLng,
    var title: String = "",
    var snippet: String = ""
): Parcelable