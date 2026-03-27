package com.example.aidl_server

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aidl_server.ipc.Client
import com.example.aidl_server.ipc.IPCService
import com.example.aidl_server.ipc.RecentClient
import com.example.aidl_server.ui.theme.AIDL_serverTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {

    private val _clientInfo = MutableStateFlow<Client?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val serviceIntent = Intent(this, IPCService::class.java)
        startService(serviceIntent)

        enableEdgeToEdge()
        setContent {
            AIDL_serverTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(top = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        val recentClient by _clientInfo.collectAsState(null)

                        if (recentClient == null) {
                            Text(text = "Not connected", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Text(text = "Last Connected Client Info", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(32.dp))

                            Text(text = "Package Name", fontSize = 20.sp)
                            Text(recentClient?.clientPackageName.orEmpty(), fontWeight = FontWeight.Medium, fontSize = 20.sp)

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Process ID", fontSize = 20.sp)
                            Text(recentClient?.clientProcessId.orEmpty(), fontWeight = FontWeight.Medium, fontSize = 20.sp)

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Clients Connected", fontSize = 20.sp)
                            Text(IPCService.connectionCount.toString(), fontWeight = FontWeight.Medium, fontSize = 20.sp)

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Data", fontSize = 20.sp)
                            Text(recentClient?.clientData.orEmpty(), fontWeight = FontWeight.Medium, fontSize = 20.sp)

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "IPC Method", fontSize = 20.sp)
                            Text(recentClient?.ipcMethod.orEmpty(), fontWeight = FontWeight.Medium, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val recentClient = RecentClient.client
        _clientInfo.value = recentClient
    }
}
