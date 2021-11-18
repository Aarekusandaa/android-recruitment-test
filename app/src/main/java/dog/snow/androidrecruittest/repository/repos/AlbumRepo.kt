package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.AlbumDao
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawAlbumEntity
import dog.snow.androidrecruittest.repository.service.AlbumService
import retrofit2.Retrofit
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.Exception


class AlbumRepo () {

    suspend fun getAlbums(albumDao: AlbumDao): List<RawAlbumEntity>{
        return albumDao.getAlbums()
    }

    suspend fun pushAlbums (albumDao: AlbumDao, albumList: List<RawAlbumEntity>){
        albumDao.pushAlbums(albumList)
    }

    suspend fun getAlbum(albumDao: AlbumDao, id: Int) : RawAlbumEntity{
        return albumDao.getAlbum(id)
    }

    suspend fun getUsersId(albumDao: AlbumDao) : List<Int>{
        return albumDao.getUsersId()
    }

    suspend fun deleteAlbum(albumDao: AlbumDao, album: RawAlbumEntity){
        try {
            val delete = albumDao.deleteAlbum(album)
            if (delete != 1){
                throw Exception()
            }
        }catch (e: Exception){
            println("Error delete DAO-> album")
        }
    }

    fun mapAlbum (data: List<RawAlbum>) : List<RawAlbumEntity>{
        var albums: MutableList<RawAlbumEntity> = mutableListOf()
        data.forEach { data->
            albums.add(RawAlbumEntity(data.id, data.userId, data.title))
        }
        return albums
    }

    fun mapAlbum (data: RawAlbum) : RawAlbumEntity{
        var albums: RawAlbumEntity = RawAlbumEntity(data.id, data.userId, data.title)
        return albums
    }

    suspend fun cacheAlbums(albumDao: AlbumDao, albumService: AlbumService, albumsIds: List<Int>) : String{     //: Boolean
        albumsIds.forEach {id ->
            try {
                val retrofitResponse = albumService.getAlbums(id)
                if (retrofitResponse.isSuccessful) {
                    retrofitResponse.body()?.let { data ->
                        val listAlbum = listOf<RawAlbumEntity>(mapAlbum(data))
                        try {
                            val insert = albumDao.pushAlbums(listAlbum)
                            if (insert.isEmpty()){
                                throw Exception()
                            }
                        }catch (e: Exception){
                            println("Error DAO-> album id $id")
                            return "Error DAO-> album id $id"
                        }
                    }
                }else{
                    when(retrofitResponse.code()){
                        in 400..499 -> {
                            println("Error SERVICE: Client-> album id $id")
                            return "Error SERVICE: Client-> album id $id"
                        }
                        in 500..599 -> {
                            println("Error SERVICE: Server-> album id $id")
                            return "Error SERVICE: Server-> album id $id"
                        }
                        else -> {
                            println("Error SERVICE-> album id $id")
                            return "Error SERVICE-> album id $id"
                        }
                    }
                }
            }catch (e: Exception){
                when (e){
                    is SocketTimeoutException ->{
                        println("Error SERVICE: SocketTimeoutException-> album id $id")
                        return "Error SERVICE: SocketTimeoutException-> album id $id"
                    }
                    is UnknownHostException -> {
                        println("Error SERVICE: UnknownHostException-> album id $id")
                        return "Error SERVICE: UnknownHostException-> album id $id"
                    }
                    else -> {
                        println("Error SERVICE: Exception-> album id $id")
                        return "Error SERVICE: Exception-> album id $id"
                    }
                }
            }
        }
        return ""
    }
}