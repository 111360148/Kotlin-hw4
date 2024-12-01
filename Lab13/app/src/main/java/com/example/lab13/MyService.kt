package com.example.lab13

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.*

class MyService : Service() {
    private var channel = ""

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 解析 Intent 取得字串訊息
        channel = intent?.getStringExtra("channel") ?: ""

        // 發送初始廣播
        sendBroadcast(getWelcomeMessage(channel))

        // 使用協程延遲執行後續廣播
        CoroutineScope(Dispatchers.Default).launch {
            delay(3000) // 延遲三秒
            sendBroadcast(getUpcomingMessage(channel))
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    // 根據頻道取得對應歡迎訊息
    private fun getWelcomeMessage(channel: String) = when (channel) {
        "music" -> "歡迎來到音樂頻道"
        "new" -> "歡迎來到新聞頻道"
        "sport" -> "歡迎來到體育頻道"
        else -> "頻道錯誤"
    }

    // 根據頻道取得對應即將播放訊息
    private fun getUpcomingMessage(channel: String) = when (channel) {
        "music" -> "即將播放本月TOP10音樂"
        "new" -> "即將為您提供獨家新聞"
        "sport" -> "即將播報本週NBA賽事"
        else -> "頻道錯誤"
    }

    // 發送廣播訊息
    private fun sendBroadcast(msg: String) {
        sendBroadcast(Intent(channel).putExtra("msg", msg))
    }
}
