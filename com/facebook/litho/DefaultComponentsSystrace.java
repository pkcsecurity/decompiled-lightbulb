package com.facebook.litho;

import android.os.Trace;
import com.facebook.litho.ComponentsSystrace;

public class DefaultComponentsSystrace implements ComponentsSystrace.Systrace {

   public void beginSection(String var1) {}

   public void beginSectionAsync(String var1) {}

   public void beginSectionAsync(String var1, int var2) {}

   public ComponentsSystrace.ArgsBuilder beginSectionWithArgs(String var1) {
      return ComponentsSystrace.NO_OP_ARGS_BUILDER;
   }

   public void endSection() {}

   public void endSectionAsync(String var1) {}

   public void endSectionAsync(String var1, int var2) {}

   public boolean isTracing() {
      return false;
   }

   static final class DefaultArgsBuilder implements ComponentsSystrace.ArgsBuilder {

      private final String mName;


      public DefaultArgsBuilder(String var1) {
         this.mName = var1;
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

      public void flush() {
         Trace.beginSection(this.mName);
      }
   }
}
