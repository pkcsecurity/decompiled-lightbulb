package com.facebook.litho;


public class ComponentsSystrace {

   public static final ComponentsSystrace.ArgsBuilder NO_OP_ARGS_BUILDER = new ComponentsSystrace.NoOpArgsBuilder(null);
   private static volatile ComponentsSystrace.Systrace sInstance;


   public static void beginSection(String var0) {
      getInstance().beginSection(var0);
   }

   public static void beginSectionAsync(String var0) {
      getInstance().beginSectionAsync(var0);
   }

   public static void beginSectionAsync(String var0, int var1) {
      getInstance().beginSectionAsync(var0, var1);
   }

   public static ComponentsSystrace.ArgsBuilder beginSectionWithArgs(String var0) {
      return getInstance().beginSectionWithArgs(var0);
   }

   public static void endSection() {
      getInstance().endSection();
   }

   public static void endSectionAsync(String var0) {
      getInstance().endSectionAsync(var0);
   }

   public static void endSectionAsync(String var0, int var1) {
      getInstance().endSectionAsync(var0, var1);
   }

   private static ComponentsSystrace.Systrace getInstance() {
      // $FF: Couldn't be decompiled
   }

   public static boolean isTracing() {
      return getInstance().isTracing();
   }

   public static void provide(ComponentsSystrace.Systrace var0) {
      sInstance = var0;
   }

   static final class NoOpArgsBuilder implements ComponentsSystrace.ArgsBuilder {

      private NoOpArgsBuilder() {}

      // $FF: synthetic method
      NoOpArgsBuilder(Object var1) {
         this();
      }

      public ComponentsSystrace.ArgsBuilder arg(String var1, double var2) {
         return this;
      }

      public ComponentsSystrace.ArgsBuilder arg(String var1, int var2) {
         return this;
      }

      public ComponentsSystrace.ArgsBuilder arg(String var1, long var2) {
         return this;
      }

      public ComponentsSystrace.ArgsBuilder arg(String var1, Object var2) {
         return this;
      }

      public void flush() {}
   }

   public interface Systrace {

      void beginSection(String var1);

      void beginSectionAsync(String var1);

      void beginSectionAsync(String var1, int var2);

      ComponentsSystrace.ArgsBuilder beginSectionWithArgs(String var1);

      void endSection();

      void endSectionAsync(String var1);

      void endSectionAsync(String var1, int var2);

      boolean isTracing();
   }

   public interface ArgsBuilder {

      ComponentsSystrace.ArgsBuilder arg(String var1, double var2);

      ComponentsSystrace.ArgsBuilder arg(String var1, int var2);

      ComponentsSystrace.ArgsBuilder arg(String var1, long var2);

      ComponentsSystrace.ArgsBuilder arg(String var1, Object var2);

      void flush();
   }
}
