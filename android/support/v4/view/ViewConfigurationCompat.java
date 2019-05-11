package android.support.v4.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import java.lang.reflect.Method;

public final class ViewConfigurationCompat {

   private static final String TAG = "ViewConfigCompat";
   private static Method sGetScaledScrollFactorMethod;


   static {
      if(VERSION.SDK_INT == 25) {
         try {
            sGetScaledScrollFactorMethod = ViewConfiguration.class.getDeclaredMethod("getScaledScrollFactor", new Class[0]);
            return;
         } catch (Exception var1) {
            Log.i("ViewConfigCompat", "Could not find method getScaledScrollFactor() on ViewConfiguration");
         }
      }

   }

   private static float getLegacyScrollFactor(ViewConfiguration var0, Context var1) {
      if(VERSION.SDK_INT >= 25 && sGetScaledScrollFactorMethod != null) {
         label19: {
            int var2;
            try {
               var2 = ((Integer)sGetScaledScrollFactorMethod.invoke(var0, new Object[0])).intValue();
            } catch (Exception var3) {
               Log.i("ViewConfigCompat", "Could not find method getScaledScrollFactor() on ViewConfiguration");
               break label19;
            }

            return (float)var2;
         }
      }

      TypedValue var4 = new TypedValue();
      return var1.getTheme().resolveAttribute(16842829, var4, true)?var4.getDimension(var1.getResources().getDisplayMetrics()):0.0F;
   }

   public static float getScaledHorizontalScrollFactor(@NonNull ViewConfiguration var0, @NonNull Context var1) {
      return VERSION.SDK_INT >= 26?var0.getScaledHorizontalScrollFactor():getLegacyScrollFactor(var0, var1);
   }

   public static int getScaledHoverSlop(ViewConfiguration var0) {
      return VERSION.SDK_INT >= 28?var0.getScaledHoverSlop():var0.getScaledTouchSlop() / 2;
   }

   @Deprecated
   public static int getScaledPagingTouchSlop(ViewConfiguration var0) {
      return var0.getScaledPagingTouchSlop();
   }

   public static float getScaledVerticalScrollFactor(@NonNull ViewConfiguration var0, @NonNull Context var1) {
      return VERSION.SDK_INT >= 26?var0.getScaledVerticalScrollFactor():getLegacyScrollFactor(var0, var1);
   }

   @Deprecated
   public static boolean hasPermanentMenuKey(ViewConfiguration var0) {
      return var0.hasPermanentMenuKey();
   }

   public static boolean shouldShowMenuShortcutsWhenKeyboardPresent(ViewConfiguration var0, @NonNull Context var1) {
      if(VERSION.SDK_INT >= 28) {
         return var0.shouldShowMenuShortcutsWhenKeyboardPresent();
      } else {
         Resources var3 = var1.getResources();
         int var2 = var3.getIdentifier("config_showMenuShortcutsWhenKeyboardPresent", "bool", "android");
         return var2 != 0 && var3.getBoolean(var2);
      }
   }
}
