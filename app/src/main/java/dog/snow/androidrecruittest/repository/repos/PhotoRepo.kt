package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.service.PhotoService
import retrofit2.Response

class PhotoRepo(private val photoService: PhotoService){

    suspend fun getPhotos(limit: Int) = photoService.getPhotos(limit)

}