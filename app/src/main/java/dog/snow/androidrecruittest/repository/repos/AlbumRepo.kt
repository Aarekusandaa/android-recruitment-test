package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.daos.AlbumDao
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.service.AlbumService
import retrofit2.Response


class AlbumRepo (private val albumService: AlbumService, private val albumDao: AlbumDao) {

    suspend fun getAlbums(id: Int): Response<List<RawAlbum>>{
        return albumService.getAlbums(id)
    }

    suspend fun pushAlbums (albumList: List<RawAlbum>){
        albumDao.pushAlbums(albumList)
    }

    suspend fun getAlbum(id: Int) : RawAlbum{
        return albumDao.getAlbum(id)
    }
}