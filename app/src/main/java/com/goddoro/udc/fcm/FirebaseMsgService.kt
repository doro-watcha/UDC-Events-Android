package com.goddoro.udc.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.goddoro.common.Broadcast
import com.goddoro.common.CommonConst
import com.goddoro.common.CommonConst.PUSH_NOTI_TARGET
import com.goddoro.common.CommonConst.PUSH_NOTI_TYPE
import com.goddoro.common.CommonConst.UDC_FCM_TOKEN
import com.goddoro.common.CommonConst.UPDATE_FCM_TOKEN
import com.goddoro.common.common.debugE
import com.goddoro.common.util.AppPreference
import com.goddoro.udc.MainActivity
import com.goddoro.udc.R
import com.google.android.exoplayer2.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject
import java.util.*


/**
 * created By DORO 1/7/21
 */

class FirebaseMsgService : FirebaseMessagingService() {

    private val TAG = FirebaseMsgService::class.java.simpleName

    private var _notificationManager: NotificationManager? = null
    private val mContext: Context? = null

    private var is_old = true
    internal var noti_badge_count = 0

    private val appPreference: AppPreference by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        updateTokenToServer(token)
    }

    private fun updateTokenToServer(_token: String) {
        try {
            val savedFcmToken = appPreference.curFCMToken
            if (_token != savedFcmToken) {
                // 서버로 token update
                val lbm = LocalBroadcastManager.getInstance(mContext!!)
                val i = Intent(UPDATE_FCM_TOKEN)
                i.putExtra(UDC_FCM_TOKEN, _token)
                lbm.sendBroadcast(i)
            } else {
                // 서버로 token update
                val lbm = LocalBroadcastManager.getInstance(mContext!!)
                val i = Intent(UPDATE_FCM_TOKEN)
                i.putExtra(UDC_FCM_TOKEN, _token)
                lbm.sendBroadcast(i)
            }
        } catch (e: Exception) {
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used addSchedulers GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        // Check if message contains a data payload.
        var dataMap: Map<*, *>? = null
        if (remoteMessage.data.isNotEmpty()) {

            //            if (/* Check if data needs to be processed by long running job */ true) {
            //                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
            //
            //            } else {
            // Handle message within 10 seconds

            dataMap = remoteMessage.data
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            sendNotification(remoteMessage.notification!!.body, remoteMessage.notification!!.title, dataMap)
        } else {
            try {
                var title: String? = null
                var body: String = ""
                var type = dataMap!!["type"].toString()
                when (Locale.getDefault().displayLanguage) {
                    "한국어" -> {
                        body = dataMap["body"].toString()
                        title = dataMap["title"].toString()
                    }
                    else -> {
                        body = dataMap["body"].toString()
                        title = dataMap["title"].toString()
                    }
                }
                sendNotification(body, title, dataMap)
            } catch (e: Exception) {
                e.printStackTrace()
                return
            }
        }

        //        sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(),dataMap);
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String?, _title: String?, dataMap: Map<*, *>?) {
        val mContext = applicationContext

        // main Activity로 진입해서 나머지것 처리한다.
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        if (dataMap != null) {
            try {

                debugE(TAG, PUSH_NOTI_TYPE + "에 담아버려!")
                intent.putExtra(PUSH_NOTI_TYPE, dataMap["type"].toString())
                debugE(TAG, intent.getStringExtra(PUSH_NOTI_TYPE))
                when (dataMap["type"].toString()) {
                    CommonConst.NotiType.NOTICE.name -> {

                    }
                    CommonConst.NotiType.WOW.name,
                    CommonConst.NotiType.COMMENT.name,
                    CommonConst.NotiType.CREATE_VIDEO.name,
                    CommonConst.NotiType.CREATED_VERSUS.name,
                    CommonConst.NotiType.CREATED_TEAM_UP.name -> {
                        // 클릭 시: 해당 엔트리
                        // videoId: ${videoId}
                        intent.putExtra(PUSH_NOTI_TARGET, dataMap["groupId"].toString())
                    }

                }
                // 알림 목록 업데이트
                debugE(TAG, "Push 보냈어!")
                //Broadcast.notificationUpdateBroadcast.onNext(mContext)

//                when (dataMap["type"].toString()) {
//
//                    CommonConst.NotiType.CREATE_VIDEO.name,
//                    CommonConst.NotiType.CREATED_VERSUS.name,
//                    CommonConst.NotiType.CREATED_TEAM_UP.name -> {
//                        get<AppPreference>().endUploading()
//
//                        Broadcast.uploadSuccessBroadcast.onNext(Unit)
//                        Broadcast.videoCreatedBroadcast.onNext(Unit)
//
//                    }
//
//
//                }
            } catch (e: Exception) {
                Log.d(TAG, e.message ?: "")
            }
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 1 /* Request code */, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        var noti_default_val = 0
        noti_default_val = Notification.DEFAULT_SOUND or Notification.DEFAULT_LIGHTS

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        _notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationCompat.Builder(this, mContext.getString(R.string.fcm_channel_id))
            .setSmallIcon(R.drawable.ic_camera)
            .setContentTitle(_title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification_channel_id = mContext.getString(R.string.fcm_channel_id)
            val notifiaction_channel_name = mContext.getString(R.string.fcm_channel_name)
            val notificationChannel = NotificationChannel(
                notification_channel_id, notifiaction_channel_name, NotificationManager.IMPORTANCE_HIGH
            )

            // Configure the notification channel, NO SOUND
            val notification_channel_desc = mContext.getString(R.string.fcm_channel_desc)
            notificationChannel.description = notification_channel_desc

            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            notificationChannel.setSound(defaultSoundUri, attributes)
            notificationChannel.enableVibration(false)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            _notificationManager!!.createNotificationChannel(notificationChannel)

            notificationBuilder.setChannelId(notification_channel_id)
        }
        notificationBuilder.setDefaults(noti_default_val)
        // headup display
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setPriority(NotificationCompat.VISIBILITY_PUBLIC)
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}