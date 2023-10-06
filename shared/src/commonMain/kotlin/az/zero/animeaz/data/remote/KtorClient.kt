package az.zero.animeaz.data.remote
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val animeKtorHttpClient by lazy {
    HttpClient {
        customConfigs(
            baseUrl = "https://api.jikan.moe/v4/",
        )
    }
}

//val imageDownloaderClient by lazy {
//    HttpClient {
//        customConfigs(
//            baseUrl = "https://api.jikan.moe/v4/",
//        )
//    }
//}


/**
 * @param baseUrl baseUrl for your endpoints ex: https://xyz.com
 * @param customParameters Custom params to be added to each request
 * @param customHeaders Custom headers to be added to each request
 * */
fun HttpClientConfig<*>.customConfigs(
    baseUrl: String,
    customParameters: List<Parameter> = emptyList(),
    customHeaders: List<Header> = listOf(
        Header(HttpHeaders.ContentType, ContentType.Application.Json)
    ),
) {
    install(Logging) {
        logger = CustomHttpLogger
        level = LogLevel.ALL
    }

    install(ContentNegotiation) {
        json(json = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    install(HttpTimeout) {
        val time = 10000L
        requestTimeoutMillis = time
        socketTimeoutMillis = time
        connectTimeoutMillis = time
    }

    install(DefaultRequest) {
        url(baseUrl)
        url {
            customParameters.forEach {
                parameters.append(it.key, it.value)
            }
        }

        customHeaders.forEach {
            header(it.key, it.value)
        }
    }
}

object CustomHttpLogger : Logger {

    private const val TAG = "CustomHttpLogger"
    override fun log(message: String) {
        println("$TAG $message")
    }

}

typealias Parameter = KeyValue<String, String>
typealias Header = KeyValue<String, Any?>
data class KeyValue<out Key, out Value>(
    val key: Key,
    val value: Value
)