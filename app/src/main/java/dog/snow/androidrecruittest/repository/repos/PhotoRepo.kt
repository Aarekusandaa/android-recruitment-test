package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.service.PhotoService

class PhotoRepo {

    private val photoService: PhotoService = TODO()

    suspend fun getPhotos(limit: Int){
        photoService.getPhotos(limit)
    }
}