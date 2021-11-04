package dog.snow.androidrecruittest.ui

import android.content.Context
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.os.Build
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext


class NetworkTools {
    companion object{

        var networkState = MutableLiveData<Boolean>(false)

        //region ASYNC
        private var networkCallback: NetworkCallback? = null

        fun registerNetworkCallbacks(context: Context){
            try {
                networkCallback = object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        GlobalScope.launch {
                            withContext(Dispatchers.Main){
                                networkState.value = true
                            }
                        }
                    }

                    override fun onLost(network: Network) {
                        GlobalScope.launch {
                            withContext(Dispatchers.Main){
                                networkState.value = false
                            }
                        }
                    }
                }

                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                connectivityManager.registerNetworkCallback(
                    NetworkRequest.Builder().build(),
                    networkCallback as NetworkCallback
                )
            } catch (e: Exception) {
                networkState.value = false
            }
        }

        fun unregisterNetworkCallbacks(context: Context){
            val networkCallback = this.networkCallback ?: return
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.unregisterNetworkCallback(networkCallback)
            this.networkCallback = null
        }

        //endregion

        //region SYNC

        fun isInternetAvailable(context: Context) : Boolean{
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val nw      = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                return when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                    else -> false
                }
            } else {
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                return nwInfo.isConnected
            }
        }

        //endregion
    }
}