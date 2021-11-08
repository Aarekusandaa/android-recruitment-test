package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.AlbumDao
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawAlbumEntity
import dog.snow.androidrecruittest.repository.service.AlbumService
import retrofit2.Retrofit


class AlbumRepo (
    private val db: AppDatabase,
    private val retrofit: Retrofit,
    private val albumService: AlbumService,
    private val albumDao: AlbumDao) {

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
        albumDao.deleteAlbum(album)
    }

    fun mapAlbum (data: List<RawAlbum>) : List<RawAlbumEntity>{
        var albums: MutableList<RawAlbumEntity> = mutableListOf()
        data.forEach { data->
            albums.add(RawAlbumEntity(data.id, data.userId, data.title))
        }
        return albums
    }

    suspend fun cacheAlbums(albumDao: AlbumDao, albumService: AlbumService, albumsIds: List<Int>) : Boolean{     //: Boolean
        albumsIds.forEach {id ->
            val retrofitResponse = albumService.getAlbums(id)
            if (retrofitResponse.isSuccessful) {
                retrofitResponse.body()?.let { data ->
                    albumDao.pushAlbums(mapAlbum(data))
                }
            }
            return true
        }
        return false
    }
}