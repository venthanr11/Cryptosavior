package com.example.cryptosavior;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotifyService {
	
	//final Context context = this;
	
public void showNotification(Context context, int flag) {
		
    	NotificationManager mNM = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
	
    	Intent intent = new Intent(context, MainActivity.class);
    	PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
    	
    	String body;
    	String title = "CryptoSavior";
    	
    	if(flag == 1) {
    		body = "The Encryption Process is over";
    	}
    	else
    		body = "The Decryption Process is over";
    	
    	
    	Notification n = new Notification.Builder(context)
    		.setContentTitle(title)
    		.setContentText(body)
    		.setSmallIcon(R.drawable.ic_launcher)
    		.setContentIntent(contentIntent).getNotification();
    	
    	n.flags |=  Notification.FLAG_AUTO_CANCEL;
    	
    	mNM.notify(0, n);
	}

}
