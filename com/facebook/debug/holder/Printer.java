package com.facebook.debug.holder;

import com.facebook.debug.debugoverlay.model.DebugOverlayTag;

public interface Printer {

   void logMessage(DebugOverlayTag var1, String var2);

   void logMessage(DebugOverlayTag var1, String var2, Object ... var3);

   boolean shouldDisplayLogMessage(DebugOverlayTag var1);
}
