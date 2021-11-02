package dog.snow.androidrecruittest.ui.viewmodels

import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.repository.repos.AlbumRepo
import dog.snow.androidrecruittest.repository.repos.PhotoRepo
import dog.snow.androidrecruittest.repository.repos.UserRepo

class SplashViewModel(private val albumRepo: AlbumRepo,
                      private val photoRepo: PhotoRepo,
                      private val userRepo: UserRepo) : ViewModel() {


}