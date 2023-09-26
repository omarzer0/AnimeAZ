package az.zero.animeaz.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDto(
    @SerialName("aired")
    val aired: AiredDto?,
    @SerialName("airing")
    val airing: Boolean?,
    @SerialName("approved")
    val approved: Boolean?,
    @SerialName("background")
    val background: String?,
    @SerialName("broadcast")
    val broadcast: BroadcastDto?,
    @SerialName("demographics")
    val demographics: List<DemographicDto?>?,
    @SerialName("duration")
    val duration: String?,
    @SerialName("episodes")
    val episodes: Int?,
//    @SerialName("explicit_genres")
//    val explicitGenres: List<Any>?,
    @SerialName("favorites")
    val favorites: Int?,
    @SerialName("genres")
    val genres: List<GenreDto?>?,
    @SerialName("images")
    val images: ImagesDto?,
    @SerialName("licensors")
    val licensors: List<LicensorDto?>?,
    @SerialName("mal_id")
    val malId: Int?,
    @SerialName("members")
    val members: Int?,
    @SerialName("popularity")
    val popularity: Int?,
    @SerialName("producers")
    val producers: List<ProducerDto?>?,
    @SerialName("rank")
    val rank: Int?,
    @SerialName("rating")
    val rating: String?,
    @SerialName("score")
    val score: Double?,
    @SerialName("scored_by")
    val scoredBy: Int?,
    @SerialName("season")
    val season: String?,
    @SerialName("source")
    val source: String?,
    @SerialName("status")
    val status: String?,
    @SerialName("studios")
    val studios: List<StudioDto?>?,
    @SerialName("synopsis")
    val synopsis: String?,
    @SerialName("themes")
    val themes: List<ThemeDto?>?,
    @SerialName("title")
    val title: String?,
    @SerialName("title_english")
    val titleEnglish: String?,
    @SerialName("title_japanese")
    val titleJapanese: String?,
    @SerialName("title_synonyms")
    val titleSynonyms: List<String?>?,
    @SerialName("titles")
    val titles: List<TitleDto?>?,
    @SerialName("trailer")
    val trailer: TrailerDto?,
    @SerialName("type")
    val type: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("year")
    val year: Int?
)