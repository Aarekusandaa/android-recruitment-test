package dog.snow.androidrecruittest

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.ui.viewmodels.SplashViewModel

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.room.Room
import com.google.gson.Gson
import dog.snow.androidrecruittest.repository.database.AppDatabase
import dog.snow.androidrecruittest.ui.NetworkTools
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
            this,
            AppDatabase::class.java, "users_db"
        ).build()

        NetworkTools.registerNetworkCallbacks(this)
        NetworkTools.networkState.observe(this, Observer { isConnected ->
            if (isConnected){
                //pobranie danych do cache'a
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                showError("internet disconnection")
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
