package dog.snow.androidrecruittest.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dog.snow.androidrecruittest.repository.daos.*
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.model.RawUser
import dog.snow.androidrecruittest.repository.repos.*
import dog.snow.androidrecruittest.repository.service.AlbumService
import dog.snow.androidrecruittest.repository.service.PhotoService
import dog.snow.androidrecruittest.repository.service.UserService
import dog.snow.androidrecruittest.ui.model.Detail
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(
    private val albumRepo: AlbumRepo,
    private val photoRepo: PhotoRepo,
    private val userRepo: UserRepo,
    private val listRepo: ListRepo,
    private val detailRepo: DetailRepo
    ) : ViewModel() {

    var userList : MutableLiveData<List<RawUser>> = MutableLiveData()

    val success: MutableLiveData<Boolean> = MutableLiveData()

    suspend fun getPhotos (photoService: PhotoService, photoDao: PhotoDao, limit: Int) : Boolean{
        return photoRepo.cachePhotos(photoService, photoDao, limit)
    }

    suspend fun getAlbums (albumDao: AlbumDao, albumService: AlbumService, photoDao: PhotoDao) : Boolean {
        return albumRepo.cacheAlbums(albumDao, albumService, photoRepo.getAlbumsId(photoDao))
    }

    suspend fun getUsers(userDao: UserDao, albumDao: AlbumDao) : Boolean{
        return userRepo.cacheUsers(userDao, albumRepo.getUsersId(albumDao))
    }

    suspend fun setListItemTable(listDao: ListDao, albumDao: AlbumDao, photoDao: PhotoDao) : Boolean{
        photoRepo.getPhotosId(photoDao).forEach { id->
            val photo = photoRepo.getPhoto(photoDao, id)
            val album = albumRepo.getAlbum(albumDao, photo.albumId)
            val data = ListItem(photo.id, photo.title, album.title, photo.thumbnailUrl)
            listRepo.pushList(listDao, data)
            return false
        }
        return true
    }

    suspend fun setDetailTable(detailsDao: DetailsDao, userDao: UserDao, albumDao: AlbumDao, photoDao: PhotoDao) : Boolean{
        photoRepo.getPhotosId(photoDao).forEach { id->
            val photo = photoRepo.getPhoto(photoDao, id)
            val album = albumRepo.getAlbum(albumDao, photo.albumId)
            val user = userRepo.getUser(userDao, album.userId)
            val data = Detail(photo.id, photo.title, album.title, user.username, user.email, user.phone, photo.url)
            detailRepo.pushDetails(detailsDao, data)
            photoRepo.deletePhoto(photoDao, photo)
            return false
        }
        albumRepo.getAlbums(albumDao).forEach { album->
            albumRepo.deleteAlbum(albumDao, album)
            return false
        }
        userRepo.getUsers(userDao).forEach { user ->
            userRepo.deleteUser(userDao, user)
            return false
        }
        return true
    }

    fun cacheData(photoService: PhotoService, photoDao: PhotoDao, limit: Int, albumDao: AlbumDao,
                    albumService: AlbumService, userDao: UserDao, listDao: ListDao, detailsDao: DetailsDao){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                val res1 = getPhotos(photoService, photoDao,limit)
                val res2 = getAlbums(albumDao, albumService, photoDao)
                val res3 = getUsers(userDao, albumDao)
                if (res1 && res2 && res3){
                    val res4 = setListItemTable(listDao, albumDao, photoDao)
                    val res5 = setDetailTable(detailsDao, userDao, albumDao, photoDao)
                    success.value = res4 && res5
                }
                else{
                    success.value = false
                }
            }
        }
    }

}