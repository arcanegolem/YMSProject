package arcanegolem.yms.project.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object NetworkMonitor {
  private val mIsNetworkAvailable = MutableStateFlow(false)
  val networkAvailable get() = mIsNetworkAvailable.asStateFlow()

  private val networkCallback = object : ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
      super.onAvailable(network)
      mIsNetworkAvailable.update { true }
    }

    override fun onLost(network: Network) {
      super.onLost(network)
      mIsNetworkAvailable.update { false }
    }
  }

  private val networkRequest = NetworkRequest.Builder()
    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
    .build()

  fun collectChanges(context : Context) {
    val connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    connectivityManager.requestNetwork(networkRequest, networkCallback)
  }
}