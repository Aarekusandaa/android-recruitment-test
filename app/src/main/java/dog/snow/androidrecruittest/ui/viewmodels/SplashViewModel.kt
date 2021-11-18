package dog.snow.androidrecruittest.ui.viewmodels

import android.content.Context
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
import dog.snow.androidrecruittest.ui.NetworkTools
import dog.snow.androidrecruittest.ui.model.Detail
import dog.snow.androidrecruittest.ui.model.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel() : ViewModel() {

    val photoRepo: PhotoRepo = PhotoRepo()
    val albumRepo: AlbumRepo = AlbumRepo()
    val userRepo: UserRepo = UserRepo()
    val listRepo: ListRepo = ListRepo()
    val detailRepo: DetailRepo = DetailRepo()

    var userList : MutableLiveData<List<RawUser>> = MutableLiveData()

    var success: MutableLiveData<Boolean> = MutableLiveData(false)
    var failure: MutableLiveData<Boolean> = MutableLiveData(false)

    suspend fun getPhotos (photoService: PhotoService, photoDao: PhotoDao, limit: Int) : Boolean{
        return photoRepo.cachePhotos(photoService, photoDao, limit)
    }

    suspend fun getAlbums (albumDao: AlbumDao, albumService: AlbumService, photoDao: PhotoDao) : Boolean {
        return albumRepo.cacheAlbums(albumDao, albumService, photoRepo.getAlbumsId(photoDao))
    }

    suspend fun getUsers(userDao: UserDao, userService: UserService, albumDao: AlbumDao) : Boolean{
        return userRepo.cacheUsers(userDao, userService, albumRepo.getUsersId(albumDao))
    }

    suspend fun setListItemTable(listDao: ListDao, albumDao: AlbumDao, photoDao: PhotoDao) : Boolean{
        photoRepo.getPhotosId(photoDao).forEach { id->
            val photo = photoRepo.getPhoto(photoDao, id)
            val album = albumRepo.getAlbum(albumDao, photo.albumId)
            val data = ListItem(photo.id, photo.title, album.title, photo.thumbnailUrl)
            listRepo.pushList(listDao, data)
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
        }
        photoRepo.getPhotosId(photoDao).forEach { id->
            val photo = photoRepo.getPhoto(photoDao, id)
            photoRepo.deletePhoto(photoDao, photo)
        }
        albumRepo.getAlbums(albumDao).forEach { album->
            albumRepo.deleteAlbum(albumDao, album)
        }
        userRepo.getUsers(userDao).forEach { user ->
            userRepo.deleteUser(userDao, user)
        }
        return true
    }

    fun cacheData(context: Context, photoService: PhotoService, photoDao: PhotoDao, limit: Int, albumDao: AlbumDao,
                    albumService: AlbumService, userDao: UserDao, userService: UserService, listDao: ListDao, detailsDao: DetailsDao){//: Boolean
        //if (NetworkTools.isInternetAvailable(context)){
            viewModelScope.launch{
                withContext(Dispatchers.IO){
                    val res1 = getPhotos(photoService, photoDao,limit)
                    val res2 = getAlbums(albumDao, albumService, photoDao)
                    val res3 = getUsers(userDao, userService, albumDao)
                    if (res1 && res2 && res3){
                        val res4 = setListItemTable(listDao, albumDao, photoDao)
                        val res5 = setDetailTable(detailsDao, userDao, albumDao, photoDao)
                        withContext(Dispatchers.Main){
                            success.value = res4 && res5
                        }
                    }
                    else{
                        withContext(Dispatchers.Main){
                            success.value = false
                            failure.value = true
                        }
                    }
                }
            }
        /*}else{
            return false
        }
        return true*/
    }

}