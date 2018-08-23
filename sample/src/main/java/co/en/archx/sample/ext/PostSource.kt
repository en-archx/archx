package co.en.archx.sample.ext

sealed class PostSource {

    object Top : PostSource()

    object Hot : PostSource()

    object New : PostSource()

    object Controversial : PostSource()

    data class Search(val query: String) : PostSource()

}