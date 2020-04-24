package com.mctech.library.core.networking

/**
 * @author MAYCON CARDOSO
 */
sealed class NetworkError(originalError : String? = null) : Exception(originalError) {
    data class ClientException(val code : Int, private val error: String?) : NetworkError(error)
    object RemoteException          : NetworkError()

    object HostUnreachable          : NetworkError()
    object OperationTimeout         : NetworkError()
    object ConnectionSpike          : NetworkError()
    object UnknownNetworkingError   : NetworkError()

    override fun toString() =
        when (this) {
            is ClientException          -> "Issue originated from client"
            RemoteException             -> "Issue incoming from server"
            HostUnreachable             -> "Cannot reach remote host"
            OperationTimeout            -> "Networking operation timed out"
            ConnectionSpike             -> "In-flight networking operation interrupted"
            UnknownNetworkingError      -> "Fatal networking exception"
        }
}