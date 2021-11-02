package dog.snow.androidrecruittest.repository.service

import dog.snow.androidrecruittest.repository.model.RawPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PhotoService {

    @Headers("User-agent: Aarekusandaa") //inne niż domyślne (w każdym)
    @GET("photos")      //?_limit=100
    suspend fun getPhotos(@Query("_limit") limit: Int): Response<List<RawPhoto>>
}