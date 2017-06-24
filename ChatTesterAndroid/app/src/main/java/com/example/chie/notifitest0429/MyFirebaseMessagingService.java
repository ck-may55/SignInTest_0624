package com.example.chie.notifitest0429;

import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.TextView;
//
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//import static com.example.chie.notifitest0429.MainActivity.flag;
// add by ishii
import com.example.chie.notifitest0429.MainActivity;

/*
 * Created by chie on 2017/04/29.
 */

//Firebaseからの通知の受け取りを担う部分

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMessService";
    //
    private LocalBroadcastManager broadcaster;

    // add by ishii
    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "onMessageReceived");

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.


        // TODO
        // activity_mainのflag_viewの表示を変化させる
        // トークンメッセージを受け取った事をLocalBroadcasterを使って発信する
        Intent intent = new Intent("ReceivedMessage");
        broadcaster.sendBroadcast(intent);
    }
}
