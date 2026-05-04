package ca.qc.cstj.tenretni.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

import com.example.tenretni.R

@Composable
fun ErrorMessage(
    @StringRes errorMessageId: Int = R.string.error_message,
    ex: Exception? = null,
    onTryAgainClick: (() -> Unit)? = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(errorMessageId), color = Color.Red, fontSize = 16.sp)

        ex?.message?.let { Text(text = it, color = Color.Red, fontSize = 14.sp) }

        if(onTryAgainClick != null) {
            Button(onClick = { onTryAgainClick() }) {
                Text(text = "Try Again")
            }
        }

    }
}