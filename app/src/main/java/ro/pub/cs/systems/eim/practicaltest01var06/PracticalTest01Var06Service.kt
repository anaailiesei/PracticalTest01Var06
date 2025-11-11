package ro.pub.cs.systems.eim.practicaltest01var06

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PracticalTest01Var06Service : Service() {
    private val TAG = "ForegroundService"
    private val CHANNEL_ID = "11"
    private val CHANNEL_NAME = "ForegroundServiceChannel"

    private lateinit var processingThread: ProcessingThread

    private fun dummyNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }

        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(applicationContext, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Service is running...")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .build()
        } else {
            Notification.Builder(applicationContext)
                .setContentTitle("Foreground Service")
                .setContentText("Service is running...")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .build()
        }

        startForeground(1, notification)
    }

    class ProcessingThread(private val context: Context, var message: String) : Thread() {

        override fun run() {
            Log.d(
                Constants.TAG,
                "Thread.run() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid()
            );
            sleepThread()
            sendMessage()
        }

        private fun sleepThread() {
            try {
                sleep(Constants.SLEEP_TIME)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        // putem primi date prin constructor dacă e nevoie
        private fun sendMessage() {
            val date = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            val broadcastIntent = Intent().apply {
                this.action = Constants.ACTION_STRING
                putExtra(Constants.DATA, "Time is $date and scor: $message")
            }

            val packageName = "ro.pub.cs.systems.eim.practicaltest01var06"
            broadcastIntent.setPackage(packageName)
            context.sendBroadcast(broadcastIntent)
        }
    }

    override fun onCreate() {
        super.onCreate()

        Log.d(Constants.TAG, "onCreate() method was invoked")
        dummyNotification()
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(Constants.TAG, "onBind() method was invoked")
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d(Constants.TAG, "onUnbind() method was invoked")
        return true
    }

    override fun onRebind(intent: Intent) {
        Log.d(Constants.TAG, "onRebind() method was invoked")
    }

    override fun onDestroy() {
        Log.d(Constants.TAG, "onDestroy() method was invoked")
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) return START_NOT_STICKY
        Log.d(Constants.TAG, "onStartCommand() method was invoked")
        // TODO: exercise 5 - implement and start the ProcessingThread
        val msg = intent.getIntExtra("message", 0)


        if ((!::processingThread.isInitialized || !processingThread.isAlive) && msg > 10) {
            processingThread = ProcessingThread(this, msg.toString())
            processingThread.start()
        }

        return START_REDELIVER_INTENT
    }
}