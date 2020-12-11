package br.com.casalmoney.app.authenticated.domain

import com.google.android.gms.maps.model.LatLng

data class Location(var description: String? = "", var latLng: LatLng? = null)