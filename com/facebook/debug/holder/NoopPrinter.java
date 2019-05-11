package com.facebook.debug.holder;

import com.facebook.debug.debugoverlay.model.DebugOverlayTag;
import com.facebook.debug.holder.Printer;

public class NoopPrinter implements Printer {

   public static final NoopPrinter INSTANCE = new NoopPrinter();


   public void logMessage(DebugOverlayTag var1, String var2) {}

   public void logMessage(DebugOverlayTag var1, String var2, Object ... var3) {}

   public boolean shouldDisplayLogMessage(DebugOverlayTag var1) {
      return false;
   }
}
