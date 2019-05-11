package com.facebook.react.devsupport;

import android.text.SpannedString;
import com.facebook.react.devsupport.interfaces.StackFrame;

public interface RedBoxHandler {

   void handleRedbox(String var1, StackFrame[] var2, RedBoxHandler.ErrorType var3);

   boolean isReportEnabled();

   void reportRedbox(String var1, StackFrame[] var2, String var3, RedBoxHandler.ReportCompletedListener var4);

   public static enum ErrorType {

      // $FF: synthetic field
      private static final RedBoxHandler.ErrorType[] $VALUES = new RedBoxHandler.ErrorType[]{JS, NATIVE};
      JS("JS", 0, "JS"),
      NATIVE("NATIVE", 1, "Native");
      private final String name;


      private ErrorType(String var1, int var2, String var3) {
         this.name = var3;
      }

      public String getName() {
         return this.name;
      }
   }

   public interface ReportCompletedListener {

      void onReportError(SpannedString var1);

      void onReportSuccess(SpannedString var1);
   }
}
