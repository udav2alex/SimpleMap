package ru.gressor.simplemap

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.gressor.simplemap.databinding.ActivityMainBinding
import ru.gressor.simplemap.ui.MapFragment
import ru.gressor.simplemap.ui.StartFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, StartFragment())
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        if (checkPermissions()) startMapFragment(true)
    }

    private fun startMapFragment(permissionsGranted: Boolean) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, MapFragment.getInstance(permissionsGranted))
            .commit()
    }

    private fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Permissions already granted", Toast.LENGTH_SHORT).show()
            return true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE_PERMISSIONS
            )
            return false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != REQUEST_CODE_PERMISSIONS) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

        if (grantResults.any { result ->
                result != PackageManager.PERMISSION_GRANTED
            }) {
            Toast.makeText(this, "Positioning won't work without permissions!", Toast.LENGTH_LONG)
                .run {
                    setGravity(Gravity.CENTER, 0, 0)
                    show()
                }
            startMapFragment(false)
        } else {
            startMapFragment(true)
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 1
    }
}