package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.PhotoDao
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.service.PhotoService
import retrofit2.Response
import retrofit2.Retrofit

class PhotoRepo(
    private val db: AppDatabase,
    private val retrofit: Retrofit,
    private val photoService: PhotoService,
    private val photoDao: PhotoDao){

    suspend fun getPhotos(limit: Int) : Response<List<RawPhoto>>{
        return photoService.getPhotos(limit)
    }

   suspend fun getPhotosId() : List<Int>{
       return photoDao.getPhotosId()
   }

    suspend fun getPhoto(id: Int) : RawPhoto{
        return photoDao.getPhoto(id)
    }

    suspend fun pushPhotos(photosList: List<RawPhoto>){
        photoDao.pushPhotos(photosList)
    }

    suspend fun deletePhoto(photo: RawPhoto){
        photoDao.deletePhotos(photo)
    }

    suspend fun getAlbumsId() : List<Int>{
        return photoDao.getAlbumsId()
    }

    suspend fun cachePhotos(limit: Int): Boolean {
        val retrofitResponse = photoService.getPhotos(limit)
        if (retrofitResponse.isSuccessful) {
            retrofitResponse.body()?.let { data ->
                photoDao.pushPhotos(data)
            }
            return true
        }
        return false
    }

}