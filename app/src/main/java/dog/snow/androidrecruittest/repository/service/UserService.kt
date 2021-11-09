package dog.snow.androidrecruittest.repository.service

import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserService {

    //TODO tylko te {id} co są w albumach
    @Headers("User-agent: Aarekusandaa")
    @GET("users/{id}")
    suspend fun getUsers(@Path("id") id: Int): Response<RawUser>
}