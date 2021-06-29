package ru.gressor.simplemap.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.gressor.simplemap.databinding.FragmentMapBinding
import ru.gressor.simplemap.entities.Point
import ru.gressor.simplemap.vm.MapVModel
import ru.gressor.simplemap.R

class MapFragment : BaseFragment<FragmentMapBinding>(), OnMapReadyCallback {
    private var permissionsGranted: Boolean = false
    private lateinit var mapView: MapView

    private val viewModel: MapVModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = super.onCreateView(inflater, container, savedInstanceState).also {
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
            googleMap.isMyLocationEnabled = true
        }

        val moscow = LatLng(55.755826, 37.6173)

        viewModel.pointsLiveData.value?.let {
            it.forEach { point ->
                googleMap.addMarker(
                    MarkerOptions().position(point.latLng).title(point.title).snippet(point.snippet)
                )
            }
        }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.map_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.edit -> {
                if (context is PointsEditor) {
                    (context as PointsEditor).editPoints(viewModel.pointsLiveData.value ?: listOf())
                }
                return true
            }
        }

        return super.onOptionsItemSelected(item)
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

        fun getInstance(permissionsGranted: Boolean) = MapFragment().apply {
            arguments = bundleOf(
                KEY_PERMISSION_GRANTED to permissionsGranted
            )
        }

        private const val KEY_PERMISSION_GRANTED = "KEY_PERMISSION_GRANTED"
    }

    interface PointsEditor {
        fun editPoints(points: List<Point>)
    }
}