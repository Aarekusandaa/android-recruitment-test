package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.PhotoDao
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.model.RawPhotoEntity
import dog.snow.androidrecruittest.repository.service.PhotoService
import retrofit2.Response
import retrofit2.Retrofit
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class PhotoRepo(
    /*private val db: AppDatabase,
    private val retrofit: Retrofit,
    private val photoService: PhotoService,
    private val photoDao: PhotoDao*/){

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
        try {
            val delete = photoDao.deletePhotos(photo)
            if (delete != 1){
                throw Exception()
            }
        }catch (e: Exception){
            println("Error delete DAO-> photo")
        }

    }

    suspend fun getAlbumsId(photoDao: PhotoDao) : List<Int>{
        val albums = photoDao.getAlbumsId()
        return albums.distinct()    //wywala powtórzenai
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

    suspend fun cachePhotos(photoService: PhotoService, photoDao: PhotoDao, limit: Int): String {
        try {
            val retrofitResponse = photoService.getPhotos(limit)
            if (retrofitResponse.isSuccessful) {
                retrofitResponse.body()?.let { data ->
                    try {
                        val insert = photoDao.pushPhotos(mapPhotos(data))
                        if (insert.isEmpty()){
                            throw Exception()
                        }
                    }catch (e: Exception){
                        println("Error DAO-> photo")
                        return "Error DAO-> photo"
                    }
                }
                return ""
            }else{
                when(retrofitResponse.code()){
                    in 400..499 -> {
                        println("Error SERVICE: Client-> photo")
                        return "Error SERVICE: Client-> photo"
                    }
                    in 500..599 -> {
                        println("Error SERVICE: Server-> photo")
                        return "Error SERVICE: Server-> photo"
                    }
                    else -> {
                        println("Error SERVICE-> photo")
                        return "Error SERVICE-> photo"
                    }
                }
            }
        }catch (e: Exception){
            when (e){
                is SocketTimeoutException ->{
                    println("Error SERVICE: SocketTimeoutException-> photo")
                    return "Error SERVICE: SocketTimeoutException-> photo"
                }
                is UnknownHostException -> {
                    println("Error SERVICE: UnknownHostException-> photo")
                    return "Error SERVICE: UnknownHostException-> photo"
                }
                else -> {
                    println("Error SERVICE: Exception-> photo")
                    return "Error SERVICE: Exception-> photo"
                }
            }
        }
    }

}