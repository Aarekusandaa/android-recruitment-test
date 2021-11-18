package dog.snow.androidrecruittest

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.ui.viewmodels.SplashViewModel

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.gson.Gson
import dog.snow.androidrecruittest.repository.daos.*
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
    /*private lateinit var userDao: UserDao
    private lateinit var albumDao: AlbumDao
    private lateinit var photoDao: PhotoDao
    private lateinit var listDao: ListDao
    private lateinit var detailsDao: DetailsDao
    private lateinit var albumService: AlbumService
    private lateinit var photoService: PhotoService
    private lateinit var userService: UserService*/

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

        if (!NetworkTools.isInternetAvailable(this)){
            NetworkTools.networkState.value = Connection.NOT_CONNECTED
        }

        NetworkTools.registerNetworkCallbacks(this)
        NetworkTools.networkState.observe(this, Observer { isConnected ->
            if (isConnected == Connection.CONNECTED){

                viewModel.cacheData(this, photoService, photoDao,
                    100, albumDao, albumService, userDao, userService, listDao, detailsDao)

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

        viewModel.failure.observe(this@SplashActivity, Observer { failure ->
            if (failure){
               showError("Retrofit & Room error: ${viewModel.errorMessage.value}")
            }
        })
    }

    private fun showError(errorMessage: String?) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ -> if (!NetworkTools.isInternetAvailable(this)){
                NetworkTools.networkState.value = Connection.NOT_CONNECTED
            }}
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> finish() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

}
