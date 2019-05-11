package com.facebook.react.devsupport;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.devsupport.interfaces.StackFrame;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

public class StackTraceHelper {

   public static final String COLUMN_KEY = "column";
   public static final String LINE_NUMBER_KEY = "lineNumber";
   private static final Pattern STACK_FRAME_PATTERN = Pattern.compile("^(?:(.*?)@)?(.*?)\\:([0-9]+)\\:([0-9]+)$");


   public static StackFrame[] convertJavaStackTrace(Throwable var0) {
      StackTraceElement[] var3 = var0.getStackTrace();
      StackFrame[] var2 = new StackFrame[var3.length];

      for(int var1 = 0; var1 < var3.length; ++var1) {
         var2[var1] = new StackTraceHelper.StackFrameImpl(var3[var1].getClassName(), var3[var1].getFileName(), var3[var1].getMethodName(), var3[var1].getLineNumber(), -1, null);
      }

      return var2;
   }

   public static StackFrame[] convertJsStackTrace(@Nullable ReadableArray var0) {
      int var2 = 0;
      int var1;
      if(var0 != null) {
         var1 = var0.size();
      } else {
         var1 = 0;
      }

      StackFrame[] var5;
      for(var5 = new StackFrame[var1]; var2 < var1; ++var2) {
         ReadableType var6 = var0.getType(var2);
         if(var6 == ReadableType.Map) {
            ReadableMap var9 = var0.getMap(var2);
            String var7 = var9.getString("methodName");
            String var8 = var9.getString("file");
            int var3;
            if(var9.hasKey("lineNumber") && !var9.isNull("lineNumber")) {
               var3 = var9.getInt("lineNumber");
            } else {
               var3 = -1;
            }

            int var4;
            if(var9.hasKey("column") && !var9.isNull("column")) {
               var4 = var9.getInt("column");
            } else {
               var4 = -1;
            }

            var5[var2] = new StackTraceHelper.StackFrameImpl(var8, var7, var3, var4, null);
         } else if(var6 == ReadableType.String) {
            var5[var2] = new StackTraceHelper.StackFrameImpl((String)null, var0.getString(var2), -1, -1, null);
         }
      }

      return var5;
   }

   public static StackFrame[] convertJsStackTrace(String var0) {
      String[] var2 = var0.split("\n");
      StackFrame[] var3 = new StackFrame[var2.length];

      for(int var1 = 0; var1 < var2.length; ++var1) {
         Matcher var4 = STACK_FRAME_PATTERN.matcher(var2[var1]);
         if(var4.find()) {
            String var5 = var4.group(2);
            if(var4.group(1) == null) {
               var0 = "(unknown)";
            } else {
               var0 = var4.group(1);
            }

            var3[var1] = new StackTraceHelper.StackFrameImpl(var5, var0, Integer.parseInt(var4.group(3)), Integer.parseInt(var4.group(4)), null);
         } else {
            var3[var1] = new StackTraceHelper.StackFrameImpl((String)null, var2[var1], -1, -1, null);
         }
      }

      return var3;
   }

   public static StackFrame[] convertJsStackTrace(JSONArray param0) {
      // $FF: Couldn't be decompiled
   }

   public static String formatFrameSource(StackFrame var0) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var0.getFileName());
      int var1 = var0.getLine();
      if(var1 > 0) {
         var2.append(":");
         var2.append(var1);
         var1 = var0.getColumn();
         if(var1 > 0) {
            var2.append(":");
            var2.append(var1);
         }
      }

      return var2.toString();
   }

   public static String formatStackTrace(String var0, StackFrame[] var1) {
      StringBuilder var4 = new StringBuilder();
      var4.append(var0);
      var4.append("\n");
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         StackFrame var5 = var1[var2];
         var4.append(var5.getMethod());
         var4.append("\n");
         var4.append("    ");
         var4.append(formatFrameSource(var5));
         var4.append("\n");
      }

      return var4.toString();
   }

   public static class StackFrameImpl implements StackFrame {

      private final int mColumn;
      private final String mFile;
      private final String mFileName;
      private final int mLine;
      private final String mMethod;


      private StackFrameImpl(String var1, String var2, int var3, int var4) {
         this.mFile = var1;
         this.mMethod = var2;
         this.mLine = var3;
         this.mColumn = var4;
         if(var1 != null) {
            var1 = (new File(var1)).getName();
         } else {
            var1 = "";
         }

         this.mFileName = var1;
      }

      // $FF: synthetic method
      StackFrameImpl(String var1, String var2, int var3, int var4, Object var5) {
         this(var1, var2, var3, var4);
      }

      private StackFrameImpl(String var1, String var2, String var3, int var4, int var5) {
         this.mFile = var1;
         this.mFileName = var2;
         this.mMethod = var3;
         this.mLine = var4;
         this.mColumn = var5;
      }

      // $FF: synthetic method
      StackFrameImpl(String var1, String var2, String var3, int var4, int var5, Object var6) {
         this(var1, var2, var3, var4, var5);
      }

      public int getColumn() {
         return this.mColumn;
      }

      public String getFile() {
         return this.mFile;
      }

      public String getFileName() {
         return this.mFileName;
      }

      public int getLine() {
         return this.mLine;
      }

      public String getMethod() {
         return this.mMethod;
      }

      public JSONObject toJSON() {
         return new JSONObject(MapBuilder.of("file", this.getFile(), "methodName", this.getMethod(), "lineNumber", Integer.valueOf(this.getLine()), "column", Integer.valueOf(this.getColumn())));
      }
   }
}
