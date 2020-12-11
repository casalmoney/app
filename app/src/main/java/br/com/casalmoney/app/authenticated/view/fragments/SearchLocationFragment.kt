package br.com.casalmoney.app.authenticated.view.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.view.activities.MainActivity
import br.com.casalmoney.app.authenticated.viewModel.SearchLocationViewModel
import br.com.casalmoney.app.databinding.FragmentSearchLocationBinding
import br.com.casalmoney.app.utils.NotificationCenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class SearchLocationFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentSearchLocationBinding

    private val viewModel: SearchLocationViewModel by lazy {
        ViewModelProvider(this).get(SearchLocationViewModel::class.java)
    }

    private var googleMap: GoogleMap? = null

    private var locationManager: LocationManager? = null
    private val LOCATION_PERMISSION_CODE = 6789

    var selectedLocation: br.com.casalmoney.app.authenticated.domain.Location? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchLocationBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        startLocationManager()
        (activity as AppCompatActivity).supportActionBar?.title = (activity as? MainActivity)?.selectedTransaction?.title
        (activity as? MainActivity)?.iSearchView = this
        showSnackBarWithInstructions()
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {

            val here = LatLng(location.latitude, location.longitude)
            val description = getAddress(here)
            googleMap?.addMarker(
                MarkerOptions()
                    .position(here).title(description)
            )?.showInfoWindow()
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 15f))

            selectedLocation = br.com.casalmoney.app.authenticated.domain.Location(
                description = description,
                latLng = here
            )

            locationManager?.removeUpdates(this)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap

        this.googleMap?.let {
            it.setOnMapClickListener { latlng ->
                // Clears the previously touched position
                it.clear();
                // Animating to the touched position
                it.animateCamera(CameraUpdateFactory.newLatLng(latlng));

                val description = getAddress(latlng)
                val location = LatLng(latlng.latitude, latlng.longitude)
                it.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title(description)
                )?.showInfoWindow()
                selectedLocation = br.com.casalmoney.app.authenticated.domain.Location(
                    description = description, latLng = latlng)
            }
        }
    }

    private fun getAddress(latLong: LatLng): String {
        activity?.let {
            Geocoder(it).let { geocoder ->
                val list = geocoder.getFromLocation(latLong.latitude, latLong.longitude, 1)
                return list[0].getAddressLine(0)
            }
        }
        return ""
    }

    private fun startLocationManager() {
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as? LocationManager

        if (ActivityCompat.checkSelfPermission(
                (activity as Context),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                (activity as Context),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
                val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                activity?.let { if (activity is MainActivity) {
                    ActivityCompat.requestPermissions(it, permissions, LOCATION_PERMISSION_CODE)
                }}
            return
        }
        locationManager?.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            0L,
            0f,
            locationListener
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_CODE) {
            startLocationManager()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.searchPlaceUsing(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    fun choosePlace(view: View) {
        selectedLocation?.let { selectedLocation?.let {
            (activity as MainActivity).setLocationInSelectedTransaction(it)
        }
            NotificationCenter.defaultCenter()?.postNotification("UpdateTransaction")
            findNavController().popBackStack(R.id.transactionDetailFragment, false)
            return
        }
        Toast.makeText(activity, getString(R.string.select_place_error_message), Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("ResourceAsColor")
    private fun showSnackBarWithInstructions() {
        val snackBar = view?.let {
            Snackbar.make(
                it, getString(R.string.select_location_instructions),
                Snackbar.LENGTH_LONG).setAction(R.string.ok, null)
        }
        snackBar?.setActionTextColor(R.color.brand_primary_color)
        val snackBarView = snackBar?.view
        snackBarView?.setBackgroundColor(Color.WHITE)
        val textView =
            snackBarView?.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(R.color.brand_primary_color)
        snackBar.show()
    }
}