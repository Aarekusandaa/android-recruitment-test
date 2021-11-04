package dog.snow.androidrecruittest

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.ui.viewmodels.SplashViewModel

import android.os.Bundle
import androidx.lifecycle.Observer
import dog.snow.androidrecruittest.ui.NetworkTools


class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
