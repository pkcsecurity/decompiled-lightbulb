package com.facebook.internal;

import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Validate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Logger {

   public static final String LOG_TAG_BASE = "FacebookSDK.";
   private static final HashMap<String, String> stringsToReplace = new HashMap();
   private final LoggingBehavior behavior;
   private StringBuilder contents;
   private int priority = 3;
   private final String tag;


   public Logger(LoggingBehavior var1, String var2) {
      Validate.notNullOrEmpty(var2, "tag");
      this.behavior = var1;
      StringBuilder var3 = new StringBuilder();
      var3.append("FacebookSDK.");
      var3.append(var2);
      this.tag = var3.toString();
      this.contents = new StringBuilder();
   }

   public static void log(LoggingBehavior var0, int var1, String var2, String var3) {
      if(FacebookSdk.isLoggingBehaviorEnabled(var0)) {
         String var4 = replaceStrings(var3);
         var3 = var2;
         if(!var2.startsWith("FacebookSDK.")) {
            StringBuilder var5 = new StringBuilder();
            var5.append("FacebookSDK.");
            var5.append(var2);
            var3 = var5.toString();
         }

         Log.println(var1, var3, var4);
         if(var0 == LoggingBehavior.DEVELOPER_ERRORS) {
            (new Exception()).printStackTrace();
         }
      }

   }

   public static void log(LoggingBehavior var0, int var1, String var2, String var3, Object ... var4) {
      if(FacebookSdk.isLoggingBehaviorEnabled(var0)) {
         log(var0, var1, var2, String.format(var3, var4));
      }

   }

   public static void log(LoggingBehavior var0, String var1, String var2) {
      log(var0, 3, var1, var2);
   }

   public static void log(LoggingBehavior var0, String var1, String var2, Object ... var3) {
      if(FacebookSdk.isLoggingBehaviorEnabled(var0)) {
         log(var0, 3, var1, String.format(var2, var3));
      }

   }

   public static void registerAccessToken(String var0) {
      synchronized(Logger.class){}

      try {
         if(!FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)) {
            registerStringToReplace(var0, "ACCESS_TOKEN_REMOVED");
         }
      } finally {
         ;
      }

   }

   public static void registerStringToReplace(String var0, String var1) {
      synchronized(Logger.class){}

      try {
         stringsToReplace.put(var0, var1);
      } finally {
         ;
      }

   }

   private static String replaceStrings(String var0) {
      synchronized(Logger.class){}

      Entry var2;
      try {
         for(Iterator var1 = stringsToReplace.entrySet().iterator(); var1.hasNext(); var0 = var0.replace((CharSequence)var2.getKey(), (CharSequence)var2.getValue())) {
            var2 = (Entry)var1.next();
         }
      } finally {
         ;
      }

      return var0;
   }

   private boolean shouldLog() {
      return FacebookSdk.isLoggingBehaviorEnabled(this.behavior);
   }

   public void append(String var1) {
      if(this.shouldLog()) {
         this.contents.append(var1);
      }

   }

   public void append(String var1, Object ... var2) {
      if(this.shouldLog()) {
         this.contents.append(String.format(var1, var2));
      }

   }

   public void append(StringBuilder var1) {
      if(this.shouldLog()) {
         this.contents.append(var1);
      }

   }

   public void appendKeyValue(String var1, Object var2) {
      this.append("  %s:\t%s\n", new Object[]{var1, var2});
   }

   public String getContents() {
      return replaceStrings(this.contents.toString());
   }

   public int getPriority() {
      return this.priority;
   }

   public void log() {
      this.logString(this.contents.toString());
      this.contents = new StringBuilder();
   }

   public void logString(String var1) {
      log(this.behavior, this.priority, this.tag, var1);
   }

   public void setPriority(int var1) {
      Validate.oneOf(Integer.valueOf(var1), "value", new Object[]{Integer.valueOf(7), Integer.valueOf(3), Integer.valueOf(6), Integer.valueOf(4), Integer.valueOf(2), Integer.valueOf(5)});
      this.priority = var1;
   }
}
