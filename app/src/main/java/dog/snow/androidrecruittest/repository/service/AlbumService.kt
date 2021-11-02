package dog.snow.androidrecruittest.repository.service

import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface AlbumService {

    //TODO tylko te {id} co są w zdjęciach
    @Headers("User-agent: Aarekusandaa")
    @GET("albums/{id}")
    suspend fun getAlbums(@Path("id") id: Int): Response<List<RawAlbum>>
}