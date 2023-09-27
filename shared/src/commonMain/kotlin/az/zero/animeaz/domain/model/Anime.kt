package az.zero.animeaz.domain.model

data class Anime(
    val id:Long,
    val englishName: String,
//    val japaneseName: String,
    val image: String,
    val score: Float,
    val airingStatus: Boolean,
)