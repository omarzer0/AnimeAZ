package az.zero.animeaz.presentation.stringUtil

import dev.icerock.moko.resources.StringResource

expect class StringProvider {

    fun get(id: StringResource, args :List<Any>): String

}