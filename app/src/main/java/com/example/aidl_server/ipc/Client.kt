package com.example.aidl_server.ipc

data class Client(
    val clientPackageName: String?,
    val clientProcessId: String,
    val clientData: String?,
    val ipcMethod: String
)

object RecentClient {
    var client: Client? = null
}