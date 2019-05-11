package com.facebook.litho;

import android.util.Log;
import com.facebook.litho.ComponentsReporter;

public class DefaultComponentsReporter implements ComponentsReporter.Reporter {

   private static final String CATEGORY = "Components";


   public void emitMessage(ComponentsReporter.LogLevel var1, String var2) {
      this.emitMessage(var1, var2, 0);
   }

   public void emitMessage(ComponentsReporter.LogLevel var1, String var2, int var3) {
      switch(null.$SwitchMap$com$facebook$litho$ComponentsReporter$LogLevel[var1.ordinal()]) {
      case 1:
         Log.w("Components", var2);
         return;
      case 2:
         Log.e("Components", var2);
         return;
      case 3:
         Log.e("Components", var2);
         throw new RuntimeException(var2);
      default:
      }
   }
}
