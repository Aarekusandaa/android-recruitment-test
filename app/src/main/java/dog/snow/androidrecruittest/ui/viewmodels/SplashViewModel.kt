package dog.snow.androidrecruittest.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.model.RawUser
import dog.snow.androidrecruittest.repository.repos.*
import dog.snow.androidrecruittest.ui.model.Detail
import dog.snow.androidrecruittest.ui.model.ListItem

class SplashViewModel(
    private val albumRepo: AlbumRepo,
    private val photoRepo: PhotoRepo,
    private val userRepo: UserRepo,
    private val listRepo: ListRepo,
    private val detailRepo: DetailRepo
    ) : ViewModel() {

    var photoList : MutableLiveData<List<RawPhoto>> = MutableLiveData()
    var albumList : MutableLiveData<List<RawAlbum>> = MutableLiveData()
    var userList : MutableLiveData<List<RawUser>> = MutableLiveData()

    suspend fun getPhotos (limit: Int) {
        photoRepo.cachePhotos(limit)
    }

    suspend fun getAlbums () {
        albumRepo.cacheAlbums(photoRepo.getAlbumsId())
    }

    suspend fun getUsers(){
        userRepo.cacheAlbums(albumRepo.getUsersId())
    }

    suspend fun setListItemTable(){
        photoRepo.getPhotosId().forEach { id->
            val photo = photoRepo.getPhoto(id)
            val album = albumRepo.getAlbum(photo.albumId)
            val data = ListItem(photo.id, photo.title, album.title, photo.thumbnailUrl)
            listRepo.pushList(data)
        }
    }

    suspend fun setDetailTable(){
        photoRepo.getPhotosId().forEach { id->
            val photo = photoRepo.getPhoto(id)
            val album = albumRepo.getAlbum(photo.albumId)
            val user = userRepo.getUser(album.userId)
            val data = Detail(photo.id, photo.title, album.title, user.username, user.email, user.phone, photo.url)
            detailRepo.pushDetails(data)
            photoRepo.deletePhoto(photo)
        }
        albumRepo.getAlbums().forEach { album->
            albumRepo.deleteAlbum(album)
        }
        userRepo.getUsers().forEach { user ->
            userRepo.deleteUser(user)
        }
    }


}