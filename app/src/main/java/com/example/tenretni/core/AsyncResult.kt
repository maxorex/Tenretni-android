package ca.qc.cstj.tenretni.core

import androidx.annotation.StringRes

sealed interface AsyncResult<out T> {
    data object Loading : AsyncResult<Nothing>
    data class Success<T>(val data: T) : AsyncResult<T>
    data class Error(@field:StringRes val messageResId: Int) : AsyncResult<Nothing>
}

sealed interface AsyncAction<out T> {
    data object Idle: AsyncAction<Nothing>
    data object Loading : AsyncAction<Nothing>
    data class Success<T>(val data: T) : AsyncAction<T>
    data class Error(@field:StringRes val messageResId: Int) : AsyncAction<Nothing>
}