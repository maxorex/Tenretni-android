package com.example.tenretni.ui.screens.main.title

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tenretni.R


@Composable
fun TitleScreen(
    viewModel: TitleViewModel = viewModel(),
    navigateToMain: () -> Unit
) {
    val orientation = LocalConfiguration.current.orientation
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitMode(viewModel, navigateToMain)
    } else {
        LandscapeMode(viewModel, navigateToMain)
    }

}

@Composable
private fun LandscapeMode(
    viewModel: TitleViewModel,
    navigateToMain: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(modifier = Modifier.fillMaxSize(0.5f)) {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(uiState.isFinished) {
                if (uiState.isFinished) {
                    navigateToMain()
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.tenretni),
                    //Todo: change the dexcription
                    contentDescription = (R.drawable.tenretni.toString()),
                    modifier = Modifier.size(200.dp)
                )

                Text(
                    text = stringResource(R.string.loading) + uiState.progression.toString() + stringResource(
                        R.string._10
                    ) + "\nFernando Garcia, \nMathias Godbout Ouellette,\nCharles Boudreault," + stringResource(
                        R.string.tenretni
                    ) + " - 2026"
                )

                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun PortraitMode(
    viewModel: TitleViewModel, navigateToMain: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(uiState.isFinished) {
            if (uiState.isFinished) {
                navigateToMain()
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.tenretni),
                //Todo: change the dexcription
                contentDescription = (R.drawable.tenretni.toString()),
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = stringResource(R.string.loading) + uiState.progression.toString() + stringResource(
                    R.string._10
                ) + "\nFernando Garcia, \nMathias Godbout Ouellette,\nCharles Boudreault," + stringResource(
                    R.string.tenretni
                ) + " - 2026"
            )

            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}