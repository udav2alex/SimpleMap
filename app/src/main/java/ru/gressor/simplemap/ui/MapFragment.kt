package ru.gressor.simplemap.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.gressor.simplemap.databinding.FragmentMapBinding
import ru.gressor.simplemap.entities.Point
import ru.gressor.simplemap.vm.MapVModel

class MapFragment : BaseFragment<FragmentMapBinding>(), OnMapReadyCallback {
    private var permissionsGranted: Boolean = false
    private lateinit var mapView: MapView

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MapVModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.arguments?.let { args ->
            permissionsGranted = args[KEY_PERMISSION_GRANTED] as? Boolean ?: false
        }

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)

        try {
            MapsInitializer.initialize(requireActivity().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (permissionsGranted) {
            @SuppressLint("MissingPermission")
            googleMap.isMyLocationEnabled = true;
        }

        val moscow = LatLng(55.755826, 37.6173)

        googleMap.setOnMapClickListener {
            val index = viewModel.pointsLiveData.value?.size ?: 0
            val point = Point(it, "title $index", "snippet $index")

            viewModel.newPoint(point)

            googleMap.addMarker(
                MarkerOptions().position(it).title(point.title).snippet(point.snippet)
            )
        }

        val cameraPosition = CameraPosition.Builder().target(moscow).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun getBindingObject(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMapBinding.inflate(inflater, container, false)

    companion object {

        fun getInstance(permissionsGranted: Boolean): MapFragment {
            val fragment = MapFragment()
            fragment.arguments = bundleOf(
                KEY_PERMISSION_GRANTED to permissionsGranted
            )
            return fragment
        }

        private const val KEY_PERMISSION_GRANTED = "KEY_PERMISSION_GRANTED"
    }
}