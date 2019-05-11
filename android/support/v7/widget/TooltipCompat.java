package android.support.v7.widget;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.TooltipCompatHandler;
import android.view.View;

public class TooltipCompat {

   public static void setTooltipText(@NonNull View var0, @Nullable CharSequence var1) {
      if(VERSION.SDK_INT >= 26) {
         var0.setTooltipText(var1);
      } else {
         TooltipCompatHandler.setTooltipText(var0, var1);
      }
   }
}
