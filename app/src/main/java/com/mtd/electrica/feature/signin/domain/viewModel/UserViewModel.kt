package com.mtd.electrica.feature.signin.domain.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtd.electrica.common.data.dataSource.local.LocalStorage
import com.mtd.electrica.feature.signin.domain.useCase.DeleteUserUseCase
import com.mtd.electrica.feature.signin.domain.useCase.RegisterUseCase
import com.mtd.electrica.feature.signin.domain.useCase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val registerUseCase: RegisterUseCase,
    private val localStorage: LocalStorage,
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState
    private val _typeState = MutableLiveData<String>()
    val typeState: LiveData<String> get() = _typeState

    @SuppressLint("CheckResult")
    fun signIn(username: String, password: String) {
        signInUseCase.execute(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _uiState.value = UiState.Success
                if (localStorage.getUserType() == "admin") {
                    _typeState.value = ("admin")
                } else {
                    _typeState.value = ("user")
                }
            }, { throwable ->
                _uiState.value = UiState.Error(throwable.message ?: "An error occurred")
            })
    }

    @SuppressLint("CheckResult")
    fun register(username: String, password: String) {
        registerUseCase.execute(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _uiState.value = UiState.Success
            }, { throwable ->
                _uiState.value = UiState.Error(throwable.message ?: "An error occurred")
            })
    }

    sealed class UiState {
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}
