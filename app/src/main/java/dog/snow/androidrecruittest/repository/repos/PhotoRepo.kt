package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.PhotoDao
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.model.RawPhotoEntity
import dog.snow.androidrecruittest.repository.service.PhotoService
import retrofit2.Response
import retrofit2.Retrofit

class PhotoRepo(
    private val db: AppDatabase,
    private val retrofit: Retrofit,
    private val photoService: PhotoService,
    private val photoDao: PhotoDao){

    suspend fun getPhotos(photoService: PhotoService, limit: Int) : Response<List<RawPhoto>>{
        return photoService.getPhotos(limit)
    }

   suspend fun getPhotosId(photoDao: PhotoDao) : List<Int>{
       return photoDao.getPhotosId()
   }

    suspend fun getPhoto(photoDao: PhotoDao, id: Int) : RawPhotoEntity{
        return photoDao.getPhoto(id)
    }

    suspend fun pushPhotos(photosList: List<RawPhoto>){
        //photoDao.pushPhotos(mapPhotos(photosList))
    }

    suspend fun deletePhoto(photoDao: PhotoDao, photo: RawPhotoEntity){
        photoDao.deletePhotos(photo)
    }

    suspend fun getAlbumsId(photoDao: PhotoDao) : List<Int>{
        return photoDao.getAlbumsId()
    }

    /*fun mapPhoto (data: RawPhoto) : RawPhotoEntity{
        return RawPhotoEntity(data.id, data.albumId, data.title, data.url, data.thumbnailUrl)
    }*/

    fun mapPhotos (data: List<RawPhoto>) : List<RawPhotoEntity>{
        val photos: MutableList<RawPhotoEntity> = mutableListOf()
        data.forEach { data->
            photos.add(RawPhotoEntity(data.id, data.albumId, data.title, data.url, data.thumbnailUrl))
        }
        return photos
    }

    suspend fun cachePhotos(photoService: PhotoService, photoDao: PhotoDao, limit: Int): Boolean {
        val retrofitResponse = photoService.getPhotos(limit)
        if (retrofitResponse.isSuccessful) {
            retrofitResponse.body()?.let { data ->
                photoDao.pushPhotos(mapPhotos(data))
            }
            return true
        }
        return false
    }

}