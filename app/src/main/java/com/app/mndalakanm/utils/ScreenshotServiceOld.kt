package com.app.mndalakanm.utils

import android.app.*
import android.content.ContentValues.TAG
import android.content.Intent
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.AudioManager
import android.media.MediaScannerConnection
import android.media.ToneGenerator
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.*
import android.util.Log
import android.view.WindowManager
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import com.app.mndalakanm.MainActivity
import com.techno.mndalakanm.BuildConfig
import com.techno.mndalakanm.R
import java.io.File
import java.io.FileOutputStream


class ScreenshotServiceOld  : Service() {
    private val CHANNEL_WHATEVER = "1"
    private val NOTIFY_ID = 9906
    val EXTRA_RESULT_CODE = "resultCode"
    val EXTRA_RESULT_INTENT = "resultIntent"
    val ACTION_RECORD: String = BuildConfig.APPLICATION_ID + ".RECORD"
    val ACTION_SHUTDOWN: String = BuildConfig.APPLICATION_ID + ".SHUTDOWN"
    val VIRT_DISPLAY_FLAGS = DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or
            DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC
    private var projection: MediaProjection? = null
    private var vdisplay: VirtualDisplay? = null
    private val handlerThread = HandlerThread(
        javaClass.simpleName,
        Process.THREAD_PRIORITY_BACKGROUND
    )
    private var handler: Handler? = null
    private var mgr: MediaProjectionManager? = null
    private var wmgr: WindowManager? = null
    private var it: ImageTransmogrifier? = null
    private var resultCode = 0
    private var resultData: Intent? = null
    private val beeper = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)

    override fun onCreate() {
        super.onCreate()

        mgr = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        wmgr = getSystemService(WINDOW_SERVICE) as WindowManager
        handlerThread.start()
        handler = Handler(handlerThread.looper)

    }

    override fun onStartCommand(i: Intent, flags: Int, startId: Int): Int {
        if (i.action == null) {
            resultCode = i.getIntExtra(EXTRA_RESULT_CODE, 1337)
            resultData = i.getParcelableExtra(EXTRA_RESULT_INTENT)
            foregroundify()
        } else if (ACTION_RECORD == i.action) {
            if (resultData != null) {
                startCapture()
            } else {
                val ui = Intent(this, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(ui)
            }
        } else if (ACTION_SHUTDOWN == i.action) {
            beeper.startTone(ToneGenerator.TONE_PROP_NACK)
            stopForeground(true)
            stopSelf()
        }
        return START_NOT_STICKY
    }
    override fun onDestroy() {
        stopCapture()
        super.onDestroy()
    }
    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        throw IllegalStateException("Binding not supported. Go away.")
    }

    fun getWindowManager(): WindowManager? {
        return wmgr
    }

    fun getHandler(): Handler? {
        return handler
    }

    fun processImage(png: ByteArray?) {
        object : Thread() {
            override fun run() {
                val output = File(
                    getExternalFilesDir(null),
                    "screenshot.png"
                )
                try {
                    val fos = FileOutputStream(output)
                    fos.write(png)
                    fos.flush()
                    fos.fd.sync()
                    fos.close()
                    MediaScannerConnection.scanFile(
                        this@ScreenshotServiceOld, arrayOf(output.absolutePath), arrayOf("image/png"),
                        null
                    )
                } catch (e: Exception) {
                    Log.e(javaClass.simpleName, "Exception writing out screenshot", e)
                }
            }
        }.start()
        beeper.startTone(ToneGenerator.TONE_PROP_ACK)
        stopCapture()
    }

    private fun stopCapture() {
        if (projection != null) {
            projection!!.stop()
            vdisplay!!.release()
            projection = null
        }
    }

    private fun startCapture() {
        projection = mgr!!.getMediaProjection(resultCode, resultData!!)
        it = ImageTransmogrifier()
        val cb: MediaProjection.Callback = object : MediaProjection.Callback() {
            override fun onStop() {
                vdisplay!!.release()
            }
        }
        vdisplay = projection?.createVirtualDisplay(
            "andshooter",
            it!!.getWidth(), it!!.getHeight(),
            resources.displayMetrics.densityDpi,
            VIRT_DISPLAY_FLAGS, it!!.getSurface(), null, handler
        )
        projection?.registerCallback(cb, handler)
    }

    private fun foregroundify() {
        Log.e(TAG, "foregroundify: ", )
        val mgr = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            mgr.getNotificationChannel(CHANNEL_WHATEVER) == null
        ) {
            mgr.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_WHATEVER,
                    "Whatever", NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        val b: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_WHATEVER)
        b.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
        b.setContentTitle(getString(R.string.app_name))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker(getString(R.string.app_name))
        b.addAction(
            R.drawable.about,
            getString(R.string.notify_record),
            buildPendingIntent(ACTION_RECORD)
        )
        b.addAction(
            R.drawable.about,
            getString(R.string.notify_shutdown),
            buildPendingIntent(ACTION_SHUTDOWN)
        )
        startForeground(NOTIFY_ID, b.build())
    }

    private fun buildPendingIntent(action: String): PendingIntent? {
        val i = Intent(this, javaClass)
        i.action = action
        return PendingIntent.getService(this, 0, i, 0)
    }

}