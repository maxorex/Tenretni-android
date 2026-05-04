package ca.qc.cstj.tenretni.core.extensions

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@SuppressLint("DiscouragedApi", "LocalContextResourcesRead")
@Composable
fun painterResourceFromString(resourceName:String) : Painter {
    val context = LocalContext.current
    val drawableId = remember(resourceName) {
        context.resources.getIdentifier(resourceName.lowercase(), "drawable", context.packageName)
    }

    return painterResource(id = drawableId)
}

@SuppressLint("DiscouragedApi", "LocalContextResourcesRead")
@Composable
fun stringFromIdentifier(stringIdentifier: String) : String {
    val context = LocalContext.current
    val resources = context.resources

    return resources.getString(
        resources.getIdentifier(stringIdentifier.lowercase(), "string", context.packageName)
    )
}

@Composable
fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(onEvent)
        }
    }
}

@Composable
fun OnResume(lifecycleEvent: () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                lifecycleEvent()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}