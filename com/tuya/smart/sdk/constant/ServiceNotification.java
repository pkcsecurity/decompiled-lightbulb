package com.tuya.smart.sdk.constant;

import android.app.Notification;
import com.tuya.smart.common.OOo0000;
import com.tuya.smart.common.o0o00000oo;

public class ServiceNotification {

   private static final int mGetNotificationId = 2018062716;
   private static final ServiceNotification mServiceNotication = new ServiceNotification();
   private Notification mNotification;


   private ServiceNotification() {
      o0o00000oo var1 = (o0o00000oo)OOo0000.O000000o(o0o00000oo.class);
      if(var1 != null) {
         this.mNotification = var1.O0000o00();
      }

   }

   public static ServiceNotification getInstance() {
      return mServiceNotication;
   }

   public Notification getNotification() {
      return this.mNotification;
   }

   public int getNotificationId() {
      return 2018062716;
   }

   public void setNotification(Notification var1) {
      this.mNotification = var1;
   }
}
