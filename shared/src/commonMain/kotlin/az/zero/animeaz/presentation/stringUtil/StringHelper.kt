package az.zero.animeaz.presentation.stringUtil

import dev.icerock.moko.resources.StringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object StringHelper : KoinComponent {
    private val stringProvider by inject<StringProvider>()
    fun getStringRes(id: StringResource, vararg args: Any): String {
        return stringProvider.get(id, args.toList())
    }
}