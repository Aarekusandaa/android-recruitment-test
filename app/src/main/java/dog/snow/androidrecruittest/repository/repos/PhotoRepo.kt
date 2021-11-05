package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.PhotoDao
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.service.PhotoService
import retrofit2.Response

class PhotoRepo(private val photoService: PhotoService, private val photoDao: PhotoDao){

    suspend fun getPhotos(limit: Int) : Response<List<RawPhoto>>{
        return photoService.getPhotos(limit)
    }

   suspend fun getPhotos() : List<RawPhoto>{
       return photoDao.getPhotos()
   }

    suspend fun getPhoto(id: Int) : RawPhoto{
        return photoDao.getPhoto(id)
    }

    suspend fun pushPhotos(photosList: List<RawPhoto>){
        photoDao.pushPhotos(photosList)
    }

}