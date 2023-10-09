package az.zero.animeaz.domain.util

import kotlinx.datetime.Clock

object DateTimeUtilImpl : DateTimeUtil {

    private const val TAG = "JDateTimeUtil"

    override fun now(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }

}

interface DateTimeUtil {
    fun now(): Long
}