package az.zero.animeaz.domain.util

import az.zero.animeaz.data.util.DateTimeUtil
import kotlinx.datetime.Clock

object DateTimeUtilImpl : DateTimeUtil {

    private const val TAG = "JDateTimeUtil"

    override fun now(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }

}