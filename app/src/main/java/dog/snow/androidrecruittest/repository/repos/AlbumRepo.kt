package dog.snow.androidrecruittest.repository.repos

import dog.snow.androidrecruittest.repository.service.AlbumService

class AlbumRepo {

    private val albumService: AlbumService = TODO()

    suspend fun getAlbums(id: Int){
        albumService.getAlbums(id)
    }
}