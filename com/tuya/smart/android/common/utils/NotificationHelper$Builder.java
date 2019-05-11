package com.tuya.smart.android.common.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Notification.Builder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import com.tuya.smart.android.common.utils.NotificationHelper.1;
import com.tuya.smart.android.common.utils.NotificationHelper.Builder.Params;

public class NotificationHelper$Builder {

   private final Params P = new Params(this, (1)null);
   @NonNull
   private Context context;


   public NotificationHelper$Builder(@NonNull Context var1) {
      this.context = var1;
   }

   public Notification build() {
      if(VERSION.SDK_INT >= 26) {
         NotificationManager var4 = (NotificationManager)this.context.getApplicationContext().getSystemService("notification");
         if(var4 == null) {
            return null;
         } else {
            var4.createNotificationChannelGroup(new NotificationChannelGroup(this.P.groupId, this.P.groupName));
            NotificationChannel var3 = new NotificationChannel(this.P.channelId, this.P.channelName, this.P.importance);
            var3.setShowBadge(this.P.showBadge);
            var3.enableLights(this.P.lights);
            var3.setGroup(this.P.groupId);
            var3.setLockscreenVisibility(this.P.lockScreenVisibility);
            var4.createNotificationChannel(var3);
            Builder var5 = (new Builder(this.context.getApplicationContext(), this.P.channelId)).setContentIntent(this.P.pendingIntent).setContentTitle(this.P.title).setContentText(this.P.text).setAutoCancel(this.P.autoCancel).setWhen(System.currentTimeMillis());
            if(this.P.smallIconRes != -1) {
               var5.setSmallIcon(this.P.smallIconRes);
            }

            if(this.P.largeIcon != null) {
               var5.setLargeIcon(this.P.largeIcon);
            } else if(this.P.largeIconRes != -1) {
               var5.setLargeIcon(BitmapFactory.decodeResource(this.context.getResources(), this.P.largeIconRes));
            }

            return var5.build();
         }
      } else {
         NotificationCompat.Builder var2 = (new NotificationCompat.Builder(this.context.getApplicationContext(), this.P.channelId)).setContentIntent(this.P.pendingIntent).setContentTitle(this.P.title).setContentText(this.P.text).setAutoCancel(this.P.autoCancel).setWhen(System.currentTimeMillis());
         byte var1;
         switch(this.P.importance) {
         case 1:
         default:
            var1 = -2;
            break;
         case 2:
            var1 = -1;
            break;
         case 3:
            var1 = 0;
            break;
         case 4:
            var1 = 1;
         }

         var2.setPriority(var1);
         if(this.P.smallIconRes != -1) {
            var2.setSmallIcon(this.P.smallIconRes);
         }

         if(this.P.largeIcon != null) {
            var2.setLargeIcon(this.P.largeIcon);
         } else if(this.P.largeIconRes != -1) {
            var2.setLargeIcon(BitmapFactory.decodeResource(this.context.getResources(), this.P.largeIconRes));
         }

         return var2.build();
      }
   }

   public NotificationHelper$Builder setAutoCancel(boolean var1) {
      this.P.autoCancel = var1;
      return this;
   }

   public NotificationHelper$Builder setChannelId(@NonNull String var1) {
      this.P.channelId = var1;
      return this;
   }

   public NotificationHelper$Builder setChannelName(@NonNull String var1) {
      this.P.channelName = var1;
      return this;
   }

   public NotificationHelper$Builder setGroupId(@NonNull String var1) {
      this.P.groupId = var1;
      return this;
   }

   public NotificationHelper$Builder setGroupName(@NonNull String var1) {
      this.P.groupName = var1;
      return this;
   }

   public NotificationHelper$Builder setImportance(int var1) {
      this.P.importance = var1;
      return this;
   }

   public NotificationHelper$Builder setLargeIcon(@Nullable Bitmap var1) {
      this.P.largeIcon = var1;
      return this;
   }

   public NotificationHelper$Builder setLargeIconRes(@DrawableRes int var1) {
      this.P.largeIconRes = var1;
      return this;
   }

   public NotificationHelper$Builder setLights(boolean var1) {
      this.P.lights = var1;
      return this;
   }

   public NotificationHelper$Builder setLockScreenVisibility(int var1) {
      this.P.lockScreenVisibility = var1;
      return this;
   }

   public NotificationHelper$Builder setPendingIntent(@Nullable PendingIntent var1) {
      this.P.pendingIntent = var1;
      return this;
   }

   public NotificationHelper$Builder setShowBadge(boolean var1) {
      this.P.showBadge = var1;
      return this;
   }

   public NotificationHelper$Builder setSmallIconRes(@DrawableRes int var1) {
      this.P.smallIconRes = var1;
      return this;
   }

   public NotificationHelper$Builder setText(@NonNull CharSequence var1) {
      this.P.text = var1;
      return this;
   }

   public NotificationHelper$Builder setTitle(@NonNull CharSequence var1) {
      this.P.title = var1;
      return this;
   }
}
