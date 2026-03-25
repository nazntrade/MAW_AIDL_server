package com.example.aidl_server.ipc

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.text.TextUtils
import com.example.aidl_server.MAWAidlInterface

class IPCService : Service() {

    override fun onBind(p0: Intent?): IBinder {
        connectionCount++
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        connectionCount--
        return super.onUnbind(intent)
    }

    private val binder = object : MAWAidlInterface.Stub() {
        override fun getPID(): Int = Process.myPid()

        override fun getConnectionCount(): Int = IPCService.connectionCount

        override fun setDisplayedValue(
            packegeName: String?,
            pid: Int,
            data: String?
        ) {
            val clientData = if (data == null || TextUtils.isEmpty(data)) {
                NOT_SET
            } else {
                data
            }

            RecentClient.client = Client(
                clientPackageName = packegeName ?: NOT_SET,
                clientProcessId = pid.toString(),
                clientData = clientData,
                ipcMethod = "AIDL"
            )
        }
    }

    companion object {

        var connectionCount = 0
        const val NOT_SET = "Not set"
    }
}

