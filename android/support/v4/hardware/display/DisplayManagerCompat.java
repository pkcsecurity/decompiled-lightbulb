package android.support.v4.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.WindowManager;
import java.util.WeakHashMap;

public final class DisplayManagerCompat {

   public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
   private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap();
   private final Context mContext;


   private DisplayManagerCompat(Context var1) {
      this.mContext = var1;
   }

   @NonNull
   public static DisplayManagerCompat getInstance(@NonNull Context param0) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   public Display getDisplay(int var1) {
      if(VERSION.SDK_INT >= 17) {
         return ((DisplayManager)this.mContext.getSystemService("display")).getDisplay(var1);
      } else {
         Display var2 = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay();
         return var2.getDisplayId() == var1?var2:null;
      }
   }

   @NonNull
   public Display[] getDisplays() {
      return VERSION.SDK_INT >= 17?((DisplayManager)this.mContext.getSystemService("display")).getDisplays():new Display[]{((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay()};
   }

   @NonNull
   public Display[] getDisplays(@Nullable String var1) {
      return VERSION.SDK_INT >= 17?((DisplayManager)this.mContext.getSystemService("display")).getDisplays(var1):(var1 == null?new Display[0]:new Display[]{((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay()});
   }
}
