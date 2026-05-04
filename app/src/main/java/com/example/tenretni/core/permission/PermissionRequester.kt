package ca.qc.cstj.tenretni.core.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequester(permission: List<String>, featureWithPermission: @Composable () -> Unit) {

    val permissionsState = rememberMultiplePermissionsState(permission)

    if(permissionsState.permissions.all { p -> p.status.isGranted}) {
        featureWithPermission()
    } else {
        LaunchedEffect(true) {
            permissionsState.launchMultiplePermissionRequest()
        }
    }
}