package mega.triple.aaa.data.network.api.ext

sealed class NetworkException(msg: String) : Exception(msg)

class UnknownException(msg: String?) : NetworkException("Unknown Exception: $msg")

class NoInternet(msg: String?) : NetworkException("No internet: $msg")

class RedirectError(msg: String?) : NetworkException("RedirectError: $msg")

class ClientError(msg: String?) : NetworkException("Client error: $msg")

class ServerError(msg: String?) : NetworkException("Server error: $msg")
