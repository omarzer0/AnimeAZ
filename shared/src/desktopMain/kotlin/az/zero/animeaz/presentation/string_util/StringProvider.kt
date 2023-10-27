package az.zero.animeaz.presentation.string_util

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format
import java.util.Locale

actual class StringProvider {
    actual fun get(id: StringResource, args :List<Any>): String {
        return id.localized(locale = Locale.getDefault(),args)
    }

}