package com.example.tenretni.ui.screens.main.title

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.example.tenretni.core.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TitleViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(TitleUiState())
    val uiState = _uiState.asStateFlow()

    private val timer = object: CountDownTimer(Constants.LoadingTimer.LOADING_TIMER, Constants.LoadingTimer.LOADING_INTERVAL) {
        override fun onFinish() {
            _uiState.update {
                it.copy(isFinished = true)
            }
        }

        override fun onTick(p0: Long) {
            _uiState.update {
                it.copy(progression = it.progression + 1)
            }
        }

    }

    init {
        timer.start()
    }
}