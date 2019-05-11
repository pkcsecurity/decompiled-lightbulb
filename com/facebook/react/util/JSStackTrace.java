package com.facebook.react.util;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSStackTrace {

   private static final Pattern mJsModuleIdPattern = Pattern.compile("(?:^|[/\\\\])(\\d+\\.js)$");


   public static String format(String var0, ReadableArray var1) {
      StringBuilder var4 = new StringBuilder(var0);
      var4.append(", stack:\n");

      for(int var2 = 0; var2 < var1.size(); ++var2) {
         ReadableMap var3 = var1.getMap(var2);
         var4.append(var3.getString("methodName"));
         var4.append("@");
         var4.append(stackFrameToModuleId(var3));
         var4.append(var3.getInt("lineNumber"));
         if(var3.hasKey("column") && !var3.isNull("column") && var3.getType("column") == ReadableType.Number) {
            var4.append(":");
            var4.append(var3.getInt("column"));
         }

         var4.append("\n");
      }

      return var4.toString();
   }

   private static String stackFrameToModuleId(ReadableMap var0) {
      if(var0.hasKey("file") && !var0.isNull("file") && var0.getType("file") == ReadableType.String) {
         Matcher var2 = mJsModuleIdPattern.matcher(var0.getString("file"));
         if(var2.find()) {
            StringBuilder var1 = new StringBuilder();
            var1.append(var2.group(1));
            var1.append(":");
            return var1.toString();
         }
      }

      return "";
   }
}
