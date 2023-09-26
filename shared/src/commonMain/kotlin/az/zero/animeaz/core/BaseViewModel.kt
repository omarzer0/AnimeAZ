package az.zero.animeaz.core

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.koin.core.component.KoinComponent

open class BaseViewModel : ViewModel(), KoinComponent, InstanceKeeper.Instance {

    override fun onDestroy() {
        onCleared()
    }
}