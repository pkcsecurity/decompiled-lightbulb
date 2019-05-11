import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.support.v4.view.ViewCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.base.R.string;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class bpc {

   private static int a;


   public static String a(int var0) {
      return String.format(Locale.US, "%02d", new Object[]{Integer.valueOf(var0)});
   }

   public static String a(long var0, String var2) {
      SimpleDateFormat var4 = new SimpleDateFormat(var2, Locale.US);

      try {
         var2 = var4.format(new Date(var0));
         return var2;
      } catch (Exception var3) {
         return null;
      }
   }

   public static String a(Context var0) {
      String var2 = "0";

      String var5;
      String var6;
      label28: {
         label27: {
            Exception var1;
            label33: {
               try {
                  var5 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), 0).versionName;
               } catch (Exception var4) {
                  var1 = var4;
                  var5 = var2;
                  break label33;
               }

               var6 = var5;

               try {
                  if(!TextUtils.isEmpty(var5)) {
                     return var6;
                  }
                  break label27;
               } catch (Exception var3) {
                  var1 = var3;
               }
            }

            var1.printStackTrace();
            break label28;
         }

         var5 = "0";
      }

      var6 = var5;
      return var6;
   }

   public static String a(Context var0, String var1) {
      try {
         TelephonyManager var3 = (TelephonyManager)var0.getSystemService("phone");
         if(var3.getPhoneType() != 2) {
            String var4 = var3.getNetworkCountryIso().toUpperCase();
            return var4;
         } else {
            return var1;
         }
      } catch (Exception var2) {
         return var1;
      }
   }

   public static String a(String var0, String var1) {
      return TextUtils.isEmpty(var1)?var0:String.format(var0, new Object[]{var1});
   }

   public static void a(Activity var0) {
      if(VERSION.SDK_INT >= 19) {
         a(var0, true);
         ahj var1 = new ahj(var0);
         var1.a(true);
         var1.a(Color.parseColor("#303030"));
         ((ViewGroup)var0.findViewById(16908290)).getChildAt(0).setFitsSystemWindows(true);
      }

   }

   public static void a(Activity var0, int var1) {
      if(var0 != null) {
         if(VERSION.SDK_INT >= 21) {
            Window var2 = var0.getWindow();
            var2.clearFlags(67108864);
            var2.addFlags(Integer.MIN_VALUE);
            var2.setStatusBarColor(var1);
            View var3 = ((ViewGroup)var0.findViewById(16908290)).getChildAt(0);
            if(var3 != null) {
               var3.setSystemUiVisibility(1280);
               ViewCompat.setFitsSystemWindows(var3, true);
               return;
            }
         } else if(VERSION.SDK_INT >= 19) {
            var0.getWindow().addFlags(67108864);
            ahj var4 = new ahj(var0);
            var4.a(var1);
            var4.a(true);
         }

      }
   }

   public static void a(Activity var0, int var1, boolean var2) {
      if(VERSION.SDK_INT >= 19) {
         a(var0, true);
         ahj var3 = new ahj(var0);
         if(var1 == 0) {
            var3.b(false);
            var3.a(false);
            var3.a(var1);
         } else {
            var3.a(true);
            var3.a(var1);
         }

         if(var2) {
            ((ViewGroup)var0.findViewById(16908290)).getChildAt(0).setFitsSystemWindows(true);
            return;
         }

         ((ViewGroup)var0.findViewById(16908290)).getChildAt(0).setFitsSystemWindows(false);
      }

   }

   public static void a(Activity var0, String var1, boolean var2) {
      a(var0, Color.parseColor(var1), var2);
   }

   @TargetApi(19)
   public static void a(Activity var0, boolean var1) {
      Window var3 = var0.getWindow();
      LayoutParams var2 = var3.getAttributes();
      if(var1) {
         var2.flags |= 67108864;
      } else {
         var2.flags &= -67108865;
      }

      var3.setAttributes(var2);
   }

   public static void a(Context var0, int var1) {
      Resources var3 = var0.getResources();
      Configuration var2 = var3.getConfiguration();
      switch(var1) {
      case 0:
         var2.locale = Locale.getDefault();
         break;
      case 1:
         var2.locale = Locale.SIMPLIFIED_CHINESE;
         break;
      case 2:
         var2.locale = Locale.ENGLISH;
         break;
      case 3:
         var2.locale = new Locale("es", "ES");
      }

      var3.updateConfiguration(var2, (DisplayMetrics)null);
   }

   public static boolean a() {
      String var0 = Locale.getDefault().getCountry().toUpperCase();
      return !TextUtils.isEmpty(var0) && var0.equals("ZH")?true:Locale.getDefault().getLanguage().equals("zh");
   }

   public static boolean a(String var0) {
      if(TextUtils.isEmpty(var0)) {
         return false;
      } else {
         try {
            var0 = URLDecoder.decode(var0, "utf-8");
         } catch (UnsupportedEncodingException var1) {
            var1.printStackTrace();
            return false;
         }

         return var0.indexOf("http://") == -1 || var0.indexOf("https://") == -1 || var0.indexOf("file:///") == -1;
      }
   }

   public static String b() {
      TimeZone var4 = TimeZone.getDefault();
      String var3 = "+08:00";
      String var2 = var3;
      if(var4 != null) {
         String var6 = var4.getDisplayName();
         var2 = var3;
         if(!TextUtils.isEmpty(var6)) {
            int var1 = var6.indexOf("+");
            int var0 = var1;
            if(var1 == -1) {
               var0 = var6.indexOf("-");
            }

            if(var0 != -1) {
               var3 = var6.substring(var0);
            }

            var2 = var3;
            if(!var3.contains(":")) {
               StringBuilder var5 = new StringBuilder();
               var5.append(var3.substring(0, 3));
               var5.append(":");
               var5.append(var3.substring(3));
               var2 = var5.toString();
            }
         }
      }

      return var2;
   }

   public static String b(Context var0, String var1) {
      StringBuilder var10 = new StringBuilder();
      int var3 = string.sunday;
      int var2 = 0;
      int var4 = string.monday;
      int var5 = string.tuesday;
      int var6 = string.wednesday;
      int var7 = string.thursday;
      int var8 = string.friday;

      for(int var9 = string.saturday; var2 < var1.length(); ++var2) {
         if(var1.charAt(var2) == 49) {
            var10.append(var0.getString((new int[]{var3, var4, var5, var6, var7, var8, var9})[var2]));
            var10.append(", ");
         }
      }

      if(var10.length() > 0) {
         var10.delete(var10.length() - ", ".length(), var10.length());
      }

      return var10.toString();
   }

   public static String b(String var0) {
      if(TextUtils.isEmpty(var0)) {
         return "";
      } else {
         int var1 = var0.indexOf("-");
         String var2 = var0;
         if(var1 >= 0) {
            var2 = var0.substring(var1 + 1);
         }

         return var2;
      }
   }

   public static void b(Activity var0, int var1) {
      if(var0 != null) {
         if(VERSION.SDK_INT >= 21) {
            Window var2 = var0.getWindow();
            var2.clearFlags(67108864);
            var2.addFlags(Integer.MIN_VALUE);
            var2.setStatusBarColor(var1);
            View var3 = ((ViewGroup)var0.findViewById(16908290)).getChildAt(0);
            if(var3 != null) {
               var3.setSystemUiVisibility(1280);
               ViewCompat.setFitsSystemWindows(var3, true);
               return;
            }
         } else if(VERSION.SDK_INT >= 19) {
            ((ViewGroup)var0.findViewById(16908290)).setPadding(0, d((Context)var0), 0, 0);
            var0.getWindow().addFlags(67108864);
            ahj var4 = new ahj(var0);
            var4.a(var1);
            var4.a(true);
         }

      }
   }

   public static boolean b(int var0) {
      return VERSION.SDK_INT >= var0;
   }

   public static boolean b(Context var0) {
      try {
         String var3 = a(var0, (String)null);
         if(TextUtils.isEmpty(var3)) {
            return TextUtils.equals(TimeZone.getDefault().getID(), "Asia/Shanghai");
         } else {
            boolean var1 = TextUtils.equals(var3, "CN");
            return var1;
         }
      } catch (Exception var2) {
         return false;
      }
   }

   public static String c(String var0) {
      return var0.replace("-", "").replace("+", "");
   }

   public static void c(Context var0) {
      Intent var1 = var0.getPackageManager().getLaunchIntentForPackage(var0.getPackageName());
      var1.addFlags(67108864);
      var0.startActivity(var1);
   }

   public static Object[] c() {
      // $FF: Couldn't be decompiled
   }

   public static int d(Context var0) {
      int var1 = 0;

      Exception var7;
      label19: {
         int var2;
         try {
            Class var3 = Class.forName("com.android.internal.R$dimen");
            Object var4 = var3.newInstance();
            var2 = Integer.parseInt(var3.getField("status_bar_height").get(var4).toString());
            var2 = var0.getResources().getDimensionPixelSize(var2);
         } catch (Exception var6) {
            var7 = var6;
            break label19;
         }

         try {
            StringBuilder var8 = new StringBuilder();
            var8.append("the status bar height is : ");
            var8.append(var2);
            L.v("@@@@@@", var8.toString());
            return var2;
         } catch (Exception var5) {
            var7 = var5;
            var1 = var2;
         }
      }

      var7.printStackTrace();
      return var1;
   }

   public static boolean d(String var0) {
      return Pattern.compile("\\s*\\r*").matcher(var0).matches();
   }

   public static boolean e(Context var0) {
      String var1 = System.getString(var0.getContentResolver(), "time_12_24");
      return var1 != null && var1.equals("12");
   }
}
