//package az.zero.animeaz.presentation.screens.auth
//
//import az.zero.animeaz.SharedRes
//import az.zero.animeaz.presentation.string_util.StringHelper
//import com.arkivanov.essenty.instancekeeper.InstanceKeeper
//import dev.icerock.moko.biometry.BiometryAuthenticator
//import dev.icerock.moko.mvvm.viewmodel.ViewModel
//import dev.icerock.moko.resources.desc.desc
//import io.github.xxfast.decompose.router.SavedStateHandle
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import org.koin.core.component.KoinComponent
//
//class GalleryAuthViewModel(
//    private val savedStateHandle: SavedStateHandle,
//    val biometryAuthenticator: BiometryAuthenticator
//) : ViewModel(), KoinComponent, InstanceKeeper.Instance {
//
//    private val _authState = MutableStateFlow(AuthResult(
//        isSuccess = false,
//        isInitial = true,
//        throwable = null
//    ))
//    val authState = _authState.asStateFlow()
//
//    init {
//        onAuthClick()
//    }
//
//    override fun onDestroy() {
//        onCleared()
//    }
//
//    fun onAuthClick() {
//        viewModelScope.launch {
//            try {
//                val isSuccess = biometryAuthenticator.checkBiometryAuthentication(
//                    requestTitle = stringResource(SharedRes.strings.bio_request_title).desc(),
//                    requestReason =  stringResource(SharedRes.strings.bio_request_reason).desc(),
//                    failureButtonText =  stringResource(SharedRes.strings.bio_failure_button_text).desc(),
//                    allowDeviceCredentials = true // true - if biometric permission is not granted user can authorise by device creds
//                )
//
//                if (isSuccess) {
//                    _authState.value = AuthResult(true, isInitial = false, throwable = null)
//                    println("Biometry: success")
//                }
//            } catch (throwable: Throwable) {
//                _authState.value = AuthResult(false, isInitial = false, throwable = throwable)
//                println("Biometry: failed $throwable")
//            }
//        }
//    }
//
//}