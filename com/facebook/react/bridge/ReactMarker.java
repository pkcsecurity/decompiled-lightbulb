package com.facebook.react.bridge;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.ReactMarkerConstants;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

@DoNotStrip
public class ReactMarker {

   private static final List<ReactMarker.MarkerListener> sListeners = new ArrayList();


   @DoNotStrip
   public static void addListener(ReactMarker.MarkerListener param0) {
      // $FF: Couldn't be decompiled
   }

   @DoNotStrip
   public static void clearMarkerListeners() {
      // $FF: Couldn't be decompiled
   }

   @DoNotStrip
   public static void logMarker(ReactMarkerConstants var0) {
      logMarker(var0, (String)null, 0);
   }

   @DoNotStrip
   public static void logMarker(ReactMarkerConstants var0, int var1) {
      logMarker(var0, (String)null, var1);
   }

   @DoNotStrip
   public static void logMarker(ReactMarkerConstants var0, @Nullable String var1) {
      logMarker(var0, var1, 0);
   }

   @DoNotStrip
   public static void logMarker(ReactMarkerConstants param0, @Nullable String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   @DoNotStrip
   public static void logMarker(String var0) {
      logMarker(var0, (String)null);
   }

   @DoNotStrip
   public static void logMarker(String var0, int var1) {
      logMarker(var0, (String)null, var1);
   }

   @DoNotStrip
   public static void logMarker(String var0, @Nullable String var1) {
      logMarker(var0, var1, 0);
   }

   @DoNotStrip
   public static void logMarker(String var0, @Nullable String var1, int var2) {
      logMarker(ReactMarkerConstants.valueOf(var0), var1, var2);
   }

   @DoNotStrip
   public static void removeListener(ReactMarker.MarkerListener param0) {
      // $FF: Couldn't be decompiled
   }

   public interface MarkerListener {

      void logMarker(ReactMarkerConstants var1, @Nullable String var2, int var3);
   }
}
