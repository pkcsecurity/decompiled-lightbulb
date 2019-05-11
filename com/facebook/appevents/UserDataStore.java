package com.facebook.appevents;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.AppEventUtility;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserDataStore {

   public static final String CITY = "ct";
   public static final String COUNTRY = "country";
   public static final String DATE_OF_BIRTH = "db";
   public static final String EMAIL = "em";
   public static final String FIRST_NAME = "fn";
   public static final String GENDER = "ge";
   public static final String LAST_NAME = "ln";
   public static final String PHONE = "ph";
   public static final String STATE = "st";
   private static final String TAG = "UserDataStore";
   private static final String USER_DATA_KEY = "com.facebook.appevents.UserDataStore.userData";
   public static final String ZIP = "zp";
   private static String hashedUserData;
   private static volatile boolean initialized;
   private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


   private static String encryptData(String var0) {
      if(var0 != null) {
         if(var0.isEmpty()) {
            return null;
         } else {
            MessageDigest var1;
            try {
               var1 = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException var2) {
               return null;
            }

            var1.update(var0.getBytes());
            return AppEventUtility.bytesToHex(var1.digest());
         }
      } else {
         return null;
      }
   }

   public static String getHashedUserData() {
      if(!initialized) {
         Log.w(TAG, "initStore should have been called before calling setUserID");
         initAndWait();
      }

      lock.readLock().lock();

      String var0;
      try {
         var0 = hashedUserData;
      } finally {
         lock.readLock().unlock();
      }

      return var0;
   }

   private static String hashUserData(Bundle param0) {
      // $FF: Couldn't be decompiled
   }

   private static void initAndWait() {
      // $FF: Couldn't be decompiled
   }

   public static void initStore() {
      if(!initialized) {
         AppEventsLogger.getAnalyticsExecutor().execute(new Runnable() {
            public void run() {
               UserDataStore.initAndWait();
            }
         });
      }
   }

   private static boolean maybeSHA256Hashed(String var0) {
      return var0.matches("[A-Fa-f0-9]{64}");
   }

   private static String normalizeData(String var0, String var1) {
      byte var3;
      label58: {
         int var2 = var0.hashCode();
         if(var2 != 3185) {
            if(var2 != 3240) {
               if(var2 != 3272) {
                  if(var2 != 3294) {
                     if(var2 != 3458) {
                        if(var2 != 3576) {
                           if(var2 != 3681) {
                              if(var2 == 957831062 && var0.equals("country")) {
                                 var3 = 5;
                                 break label58;
                              }
                           } else if(var0.equals("st")) {
                              var3 = 4;
                              break label58;
                           }
                        } else if(var0.equals("ph")) {
                           var3 = 6;
                           break label58;
                        }
                     } else if(var0.equals("ln")) {
                        var3 = 2;
                        break label58;
                     }
                  } else if(var0.equals("ge")) {
                     var3 = 7;
                     break label58;
                  }
               } else if(var0.equals("fn")) {
                  var3 = 1;
                  break label58;
               }
            } else if(var0.equals("em")) {
               var3 = 0;
               break label58;
            }
         } else if(var0.equals("ct")) {
            var3 = 3;
            break label58;
         }

         var3 = -1;
      }

      switch(var3) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
         return var1.trim().toLowerCase();
      case 6:
         return var1.trim().replaceAll("[^0-9]", "");
      case 7:
         var0 = var1.trim().toLowerCase();
         if(var0.length() > 0) {
            var0 = var0.substring(0, 1);
         } else {
            var0 = "";
         }

         return var0;
      default:
         return "";
      }
   }

   public static void setUserDataAndHash(final Bundle var0) {
      if(!initialized) {
         Log.w(TAG, "initStore should have been called before calling setUserData");
         initAndWait();
      }

      AppEventsLogger.getAnalyticsExecutor().execute(new Runnable() {
         public void run() {
            UserDataStore.lock.writeLock().lock();

            try {
               UserDataStore.hashedUserData = UserDataStore.hashUserData(var0);
               Editor var1 = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
               var1.putString("com.facebook.appevents.UserDataStore.userData", UserDataStore.hashedUserData);
               var1.apply();
            } finally {
               UserDataStore.lock.writeLock().unlock();
            }

         }
      });
   }

   public static void setUserDataAndHash(@Nullable String var0, @Nullable String var1, @Nullable String var2, @Nullable String var3, @Nullable String var4, @Nullable String var5, @Nullable String var6, @Nullable String var7, @Nullable String var8, @Nullable String var9) {
      Bundle var10 = new Bundle();
      if(var0 != null) {
         var10.putString("em", var0);
      }

      if(var1 != null) {
         var10.putString("fn", var1);
      }

      if(var2 != null) {
         var10.putString("ln", var2);
      }

      if(var3 != null) {
         var10.putString("ph", var3);
      }

      if(var4 != null) {
         var10.putString("db", var4);
      }

      if(var5 != null) {
         var10.putString("ge", var5);
      }

      if(var6 != null) {
         var10.putString("ct", var6);
      }

      if(var7 != null) {
         var10.putString("st", var7);
      }

      if(var8 != null) {
         var10.putString("zp", var8);
      }

      if(var9 != null) {
         var10.putString("country", var9);
      }

      setUserDataAndHash(var10);
   }
}
