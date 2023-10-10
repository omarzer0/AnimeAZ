package az.zero.animeaz.presentation.string_util

import android.content.Context
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format

actual class StringProvider(
    private val context: Context
) {
    actual fun get(id: StringResource, args :List<Any>): String {
        return if (args.isEmpty()) {
            StringDesc.Resource(id).toString(context = context)
        } else {
            id.format(args).toString(context)
        }
    }

}