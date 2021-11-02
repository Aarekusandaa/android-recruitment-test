package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.service.AlbumService
import retrofit2.Response


class AlbumRepo (private val albumService: AlbumService){

    suspend fun getAlbums(id: Int){
        albumService.getAlbums(id)
    }
}