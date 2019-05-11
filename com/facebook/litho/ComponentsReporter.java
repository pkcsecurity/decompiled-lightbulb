package com.facebook.litho;


public class ComponentsReporter {

   private static volatile ComponentsReporter.Reporter sInstance;


   public static void emitMessage(ComponentsReporter.LogLevel var0, String var1) {
      getInstance().emitMessage(var0, var1);
   }

   public static void emitMessage(ComponentsReporter.LogLevel var0, String var1, int var2) {
      getInstance().emitMessage(var0, var1, var2);
   }

   private static ComponentsReporter.Reporter getInstance() {
      // $FF: Couldn't be decompiled
   }

   public static void provide(ComponentsReporter.Reporter var0) {
      sInstance = var0;
   }

   public static enum LogLevel {

      // $FF: synthetic field
      private static final ComponentsReporter.LogLevel[] $VALUES = new ComponentsReporter.LogLevel[]{WARNING, ERROR, FATAL};
      ERROR("ERROR", 1),
      FATAL("FATAL", 2),
      WARNING("WARNING", 0);


      private LogLevel(String var1, int var2) {}
   }

   public interface Reporter {

      void emitMessage(ComponentsReporter.LogLevel var1, String var2);

      void emitMessage(ComponentsReporter.LogLevel var1, String var2, int var3);
   }
}
