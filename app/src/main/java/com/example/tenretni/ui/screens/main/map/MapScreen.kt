package com.example.tenretni.ui.screens.main.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tenretni.core.permission.PermissionRequester
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
fun MapScreen(latLng: LatLng = LatLng(0.0, 0.0)) {
    val permissions = listOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,

        )
    PermissionRequester(permissions) {

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(latLng, 7f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isTrafficEnabled = true, isMyLocationEnabled = true)
        ) {
            Marker(
                state = rememberUpdatedMarkerState(position = latLng),
                title = " ",
                snippet = "${latLng.latitude} - ${latLng.longitude}"

            )
        }
    }


}