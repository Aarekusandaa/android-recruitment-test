package dog.snow.androidrecruittest

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.ui.viewmodels.SplashViewModel

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.gson.Gson
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.repository.service.AlbumService
import dog.snow.androidrecruittest.repository.service.PhotoService
import dog.snow.androidrecruittest.repository.service.UserService
import dog.snow.androidrecruittest.ui.Connection
import dog.snow.androidrecruittest.ui.NetworkTools
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gson = Gson()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()

        val db = Room.databaseBuilder(
            this@SplashActivity,
            AppDatabase::class.java, "users_db"
        ).build()

        val userDao = db.userDao()
        val albumDao = db.albumDao()
        val photoDao = db.photoDao()
        val listDao = db.listDao()
        val detailsDao = db.detailsDao()
        val albumService: AlbumService = retrofit.create(AlbumService::class.java)
        val photoService: PhotoService = retrofit.create(PhotoService::class.java)
        val userService: UserService = retrofit.create(UserService::class.java)

        NetworkTools.registerNetworkCallbacks(this)
        NetworkTools.networkState.observe(this, Observer { isConnected ->
            if (isConnected == Connection.CONNECTED){
                viewModel.cacheData(photoService, photoDao, 100, albumDao, albumService,
                    userDao, userService, listDao, detailsDao)



                /*viewModel.userList.observe(this,{user->
                    if (user == null){
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                if (viewModel.getPhotos(photoService, photoDao, 100)) {
                                    withContext(Dispatchers.Main) {
                                        viewModel.getAlbums(albumDao, albumService, photoDao)
                                        viewModel.getUsers(userDao, albumDao)
                                        //viewModel.userList.value = viewModel.getUsers()
                                        viewModel.setListItemTable(listDao, albumDao, photoDao)
                                        viewModel.setDetailTable(detailsDao, userDao, albumDao, photoDao)
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        showError("problem with photos")
                                    }
                                }
                            }
                        }
                    }
                })
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)*/
            }
            else if (isConnected == Connection.NOT_CONNECTED){
                showError("internet disconnection")
            }
        })

        viewModel.success.observe(this@SplashActivity, Observer { success ->
            if (success){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun showError(errorMessage: String?) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ -> NetworkTools.registerNetworkCallbacks(this) }
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> finish() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

}
