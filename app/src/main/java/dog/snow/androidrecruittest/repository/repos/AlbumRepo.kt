package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.AlbumDao
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.service.AlbumService
import retrofit2.Response
import retrofit2.Retrofit


class AlbumRepo (
    private val db: AppDatabase,
    private val retrofit: Retrofit,
    private val albumService: AlbumService,
    private val albumDao: AlbumDao) {

    suspend fun getAlbums(): List<RawAlbum>{
        return albumDao.getAlbums()
    }

    suspend fun pushAlbums (albumList: List<RawAlbum>){
        albumDao.pushAlbums(albumList)
    }

    suspend fun getAlbum(id: Int) : RawAlbum{
        return albumDao.getAlbum(id)
    }

    suspend fun getUsersId() : List<Int>{
        return albumDao.getUsersId()
    }

    suspend fun deleteAlbum(album: RawAlbum){
        albumDao.deleteAlbum(album)
    }

    suspend fun cacheAlbums(albumsIds: List<Int>) {     //: Boolean
        albumsIds.forEach {id ->
            val retrofitResponse = albumService.getAlbums(id)
            if (retrofitResponse.isSuccessful) {
                retrofitResponse.body()?.let { data ->
                    albumDao.pushAlbums(data)
                }
                //return true
            }
            //return false
        }
    }
}