package com.facebook.systrace;


public final class SystraceMessage {

   private static final SystraceMessage.Builder NOOP_BUILDER = new SystraceMessage.NoopBuilder(null);


   public static SystraceMessage.Builder beginSection(long var0, String var2) {
      return NOOP_BUILDER;
   }

   public static SystraceMessage.Builder endSection(long var0) {
      return NOOP_BUILDER;
   }

   public abstract static class Builder {

      public abstract SystraceMessage.Builder arg(String var1, double var2);

      public abstract SystraceMessage.Builder arg(String var1, int var2);

      public abstract SystraceMessage.Builder arg(String var1, long var2);

      public abstract SystraceMessage.Builder arg(String var1, Object var2);

      public abstract void flush();
   }

   interface Flusher {

      void flush(StringBuilder var1);
   }

   static class NoopBuilder extends SystraceMessage.Builder {

      private NoopBuilder() {}

      // $FF: synthetic method
      NoopBuilder(Object var1) {
         this();
      }

      public SystraceMessage.Builder arg(String var1, double var2) {
         return this;
      }

      public SystraceMessage.Builder arg(String var1, int var2) {
         return this;
      }

      public SystraceMessage.Builder arg(String var1, long var2) {
         return this;
      }

      public SystraceMessage.Builder arg(String var1, Object var2) {
         return this;
      }

      public void flush() {}
   }
}
