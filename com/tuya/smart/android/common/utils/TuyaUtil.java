package com.tuya.smart.android.common.utils;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.tuya.smart.android.common.utils.TyCommonUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class TuyaUtil {

   private static final String TAG = "TuyaUtil";


   public static long absoluteValue(long var0) {
      return var0 > 0L?var0:-var0;
   }

   public static boolean checkBvVersion(String var0, float var1) {
      return !TextUtils.isEmpty(var0) && Float.valueOf(var0).floatValue() >= var1;
   }

   public static boolean checkHgwLastVersion(String var0, float var1) {
      boolean var3 = TextUtils.isEmpty(var0);
      boolean var2 = false;
      if(var3) {
         return false;
      } else {
         if(Float.valueOf(var0.replace("v", "")).floatValue() > var1) {
            var2 = true;
         }

         return var2;
      }
   }

   public static boolean checkHgwVersion(String var0, float var1) {
      boolean var3 = TextUtils.isEmpty(var0);
      boolean var2 = false;
      if(var3) {
         return false;
      } else {
         if(Float.valueOf(var0.replace("v", "")).floatValue() >= var1) {
            var2 = true;
         }

         return var2;
      }
   }

   public static boolean checkPvLastVersion(String var0, float var1) {
      return !TextUtils.isEmpty(var0) && Float.valueOf(var0).floatValue() > var1;
   }

   public static boolean checkPvVersion(String var0, float var1) {
      return !TextUtils.isEmpty(var0) && Float.valueOf(var0).floatValue() >= var1;
   }

   public static boolean checkServiceProcess(Context var0, String var1) {
      Iterator var2 = ((ActivityManager)var0.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE).iterator();

      do {
         if(!var2.hasNext()) {
            return false;
         }
      } while(!TextUtils.equals(var1, ((RunningServiceInfo)var2.next()).service.getPackageName()));

      return true;
   }

   public static boolean checkServiceVersion(String var0, String var1) {
      return TextUtils.isEmpty(var0) || Float.valueOf(var0).floatValue() < Float.valueOf(var1).floatValue();
   }

   public static int compare(String var0, String var1) {
      if(var0 != null && var0.length() != 0 && var1 != null && var1.length() != 0) {
         int[] var3 = getValue(var0);
         int[] var4 = getValue(var1);

         for(int var2 = 0; var2 < var3.length && var2 < var4.length; ++var2) {
            if(var3[var2] < var4[var2]) {
               return -1;
            }

            if(var3[var2] > var4[var2]) {
               return 1;
            }
         }

         return var3.length == var4.length?0:(var3.length > var4.length?1:-1);
      } else {
         throw new IllegalArgumentException("Invalid parameter!");
      }
   }

   public static int compareVersion(String var0, String var1) {
      return var0 != null && var0.length() != 0 && var1 != null && var1.length() != 0?compare(var0, var1):-1;
   }

   public static int dip2px(Context var0, float var1) {
      return (int)(var1 * var0.getResources().getDisplayMetrics().density + 0.5F);
   }

   public static byte[] drawable2bytes(Drawable var0) {
      Bitmap var2 = ((BitmapDrawable)var0).getBitmap();
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      var2.compress(CompressFormat.JPEG, 100, var1);
      return var1.toByteArray();
   }

   public static String formatDate(int var0, String var1) {
      return formatDate((long)var0 * 1000L, var1);
   }

   public static String formatDate(long var0, String var2) {
      SimpleDateFormat var4 = new SimpleDateFormat(var2, Locale.US);

      try {
         var2 = var4.format(new Date(var0));
         return var2;
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public static Date formatDate(String var0, String var1) {
      SimpleDateFormat var4 = new SimpleDateFormat(var1, Locale.US);

      try {
         Date var3 = var4.parse(var0);
         return var3;
      } catch (ParseException var2) {
         return null;
      }
   }

   public static String getApplicationName(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public static byte[] getAssetsData(Context var0, String var1, byte[] var2) {
      try {
         InputStream var4 = var0.getApplicationContext().getAssets().open(var1);
         byte[] var5 = new byte[var4.available()];
         var4.read(var5);
         var4.close();
         return var5;
      } catch (IOException var3) {
         var3.printStackTrace();
         return var2;
      }
   }

   public static String getCountryCode(Context param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static final DisplayMetrics getDisplayMetrics(Context var0) {
      return var0 == null?null:var0.getResources().getDisplayMetrics();
   }

   public static int getDrawableResId(Context var0, String var1, int var2) {
      try {
         int var3 = var0.getResources().getIdentifier(var1, "drawable", var0.getPackageName());
         return var3;
      } catch (Exception var4) {
         return var2;
      }
   }

   public static Intent getExplicitIntent(Context var0, Intent var1) {
      Iterator var3 = var0.getPackageManager().queryIntentServices(var1, 0).iterator();

      ResolveInfo var2;
      do {
         if(!var3.hasNext()) {
            return null;
         }

         var2 = (ResolveInfo)var3.next();
      } while(!checkServiceProcess(var0, var2.serviceInfo.packageName));

      Intent var4 = new Intent(var1);
      var4.setPackage(var2.serviceInfo.packageName);
      return var4;
   }

   public static Intent getExplicitIntent(Context var0, Intent var1, String var2) {
      if(var0 == null) {
         return null;
      } else {
         ActivityManager var4 = (ActivityManager)var0.getSystemService("activity");
         if(var4 == null) {
            return null;
         } else {
            List var5 = var4.getRunningServices(Integer.MAX_VALUE);
            if(var5 == null) {
               return null;
            } else {
               Iterator var3 = var5.iterator();

               RunningServiceInfo var6;
               do {
                  if(!var3.hasNext()) {
                     return null;
                  }

                  var6 = (RunningServiceInfo)var3.next();
               } while(!TextUtils.equals(var2, var6.service.getClassName()));

               var1 = new Intent(var1);
               var1.setPackage(var6.service.getPackageName());
               return var1;
            }
         }
      }
   }

   public static String getLang(Context var0) {
      return TyCommonUtil.getLang(var0);
   }

   public static String getSpaceString(long var0) {
      String[] var2 = spaceFormat(var0);
      String[] var3 = TextUtils.split(var2[0], "\\.");
      StringBuilder var4 = new StringBuilder();
      var4.append(var3[0]);
      var4.append(var2[1]);
      return var4.toString();
   }

   public static String getString(Context var0, String var1, String var2) {
      try {
         String var4 = var0.getString(var0.getResources().getIdentifier(var1, "string", var0.getPackageName()));
         return var4;
      } catch (Exception var3) {
         return var2;
      }
   }

   public static String getUidBySessionId(String var0) {
      int var2 = var0.length();
      int var1 = 0;
      var0 = var0.substring(0, var2 - 32);
      String var8 = var0.substring(0, var0.length() - 1);
      Integer var9 = Integer.valueOf(var0.substring(var0.length() - 1, var0.length()));
      Integer var6 = Integer.valueOf(var8.length() - var9.intValue());
      int var3 = (int)Math.ceil((double)((float)var6.intValue() / 8.0F));
      var9 = Integer.valueOf(0);

      String var4;
      for(var4 = ""; var1 < var3; var1 = var2) {
         var2 = var1 + 1;
         Integer var7 = Integer.valueOf(var2 * 8);
         Integer var5 = var7;
         if(var7.intValue() > var6.intValue()) {
            var5 = var6;
         }

         StringBuilder var10 = new StringBuilder();
         var10.append(var4);
         var10.append(var8.substring(var9.intValue(), var5.intValue() + var1));
         var4 = var10.toString();
         var9 = Integer.valueOf(var5.intValue() + var1 + 1);
      }

      return var4;
   }

   public static int[] getValue(String var0) {
      String[] var5 = var0.split("\\.");
      int[] var2 = new int[var5.length];

      for(int var1 = 0; var1 < var5.length; ++var1) {
         try {
            var2[var1] = Integer.valueOf(var5[var1]).intValue();
         } catch (Exception var4) {
            var2[var1] = 0;
         }
      }

      return var2;
   }

   public static boolean goToMarket(Context var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("market://details?id=");
      var1.append(var0.getPackageName());
      Intent var3 = new Intent("android.intent.action.VIEW", Uri.parse(var1.toString()));
      var3.addFlags(268435456);
      if(var0.getPackageManager().resolveActivity(var3, 65536) != null) {
         try {
            var0.startActivity(var3);
            return true;
         } catch (ActivityNotFoundException var2) {
            ;
         }
      }

      return false;
   }

   public static boolean hasLatOrLon(String var0, String var1) {
      if(!TextUtils.isEmpty(var0)) {
         if(TextUtils.isEmpty(var1)) {
            return false;
         } else {
            double var2;
            double var4;
            try {
               var2 = Double.valueOf(var0).doubleValue();
               var4 = Double.valueOf(var1).doubleValue();
            } catch (Exception var6) {
               return false;
            }

            return var2 > 1.0E-5D || var4 > 1.0E-5D;
         }
      } else {
         return false;
      }
   }

   public static boolean isAppForeground(Context var0) {
      ActivityManager var1 = (ActivityManager)var0.getSystemService("activity");
      if(var1 == null) {
         return false;
      } else {
         List var3 = var1.getRunningAppProcesses();
         if(var3 != null) {
            if(var3.isEmpty()) {
               return false;
            } else {
               Iterator var4 = var3.iterator();

               RunningAppProcessInfo var2;
               do {
                  if(!var4.hasNext()) {
                     return false;
                  }

                  var2 = (RunningAppProcessInfo)var4.next();
               } while(!var2.processName.equals(var0.getPackageName()) || var2.importance != 100);

               return true;
            }
         } else {
            return false;
         }
      }
   }

   public static boolean isAppInstalled(Context var0, String var1) {
      PackageManager var3 = var0.getPackageManager();

      try {
         var3.getPackageInfo(var1, 1);
         return true;
      } catch (NameNotFoundException var2) {
         return false;
      }
   }

   public static boolean isApplicationBroughtToBackground(Context var0) {
      String var3 = var0.getApplicationContext().getPackageName();
      ActivityManager var4 = (ActivityManager)var0.getSystemService("activity");
      KeyguardManager var6 = (KeyguardManager)var0.getSystemService("keyguard");
      if(var4 == null) {
         return false;
      } else {
         Iterator var7 = var4.getRunningAppProcesses().iterator();

         RunningAppProcessInfo var5;
         do {
            if(!var7.hasNext()) {
               return false;
            }

            var5 = (RunningAppProcessInfo)var7.next();
         } while(!var5.processName.startsWith(var3));

         boolean var1;
         if(var5.importance != 100 && var5.importance != 200) {
            var1 = true;
         } else {
            var1 = false;
         }

         boolean var2 = var6.inKeyguardRestrictedInputMode();
         if(!var1) {
            if(var2) {
               return true;
            } else {
               return false;
            }
         } else {
            return true;
         }
      }
   }

   public static boolean isHgwVersionEquals(String var0, String var1) {
      return TextUtils.isEmpty(var0)?false:TextUtils.equals(var0.replace("v", ""), var1);
   }

   public static boolean isZh(Context var0) {
      return getLang(var0).endsWith("zh");
   }

   public static int px2dip(Context var0, float var1) {
      return (int)(var1 / var0.getResources().getDisplayMetrics().density + 0.5F);
   }

   public static String[] spaceFormat(long var0) {
      String[] var3 = new String[]{"0", "MB"};
      if(var0 <= 0L) {
         var3[0] = "0";
         var3[1] = "MB";
         return var3;
      } else {
         float var2 = (float)var0 / 1024.0F;
         if(var2 < 1.0F) {
            var3[0] = "1";
            var3[1] = "MB";
            return var3;
         } else if(var2 < 100.0F) {
            var3[0] = String.format("%d", new Object[]{Long.valueOf((long)var2)});
            var3[1] = "MB";
            return var3;
         } else {
            var2 /= 1024.0F;
            if(var2 < 100.0F) {
               var3[0] = String.format("%.2f", new Object[]{Float.valueOf(var2)});
               var3[1] = "GB";
               return var3;
            } else {
               var3[0] = String.format("%d", new Object[]{Long.valueOf((long)var2)});
               var3[1] = "GB";
               return var3;
            }
         }
      }
   }

   public static String spacePercent(long var0, long var2) {
      float var4 = (float)var0 / (float)var2 * 100.0F;
      return var4 == 0.0F?"0%":((double)var4 <= 0.01D?"0.01%":(var4 < 10.0F?String.format("%.2f%%", new Object[]{Float.valueOf(var4)}):String.format("%d%%", new Object[]{Long.valueOf((long)var4)})));
   }

   public static void startForegroundService(@NonNull Context var0, @NonNull Intent var1) {
      if(VERSION.SDK_INT >= 26) {
         var0.startForegroundService(var1);
      } else {
         var0.startService(var1);
      }
   }

   public static int stringToInt(String var0) {
      try {
         int var1 = Integer.valueOf(var0).intValue();
         return var1;
      } catch (Exception var2) {
         return 0;
      }
   }
}
