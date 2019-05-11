package com.facebook.litho;

import android.support.annotation.Nullable;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentsLogger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class KeyHandler {

   private static final String STACK_TRACE_NO_SPEC_MESSAGE = "Unable to determine root of duplicate key in a *Spec.java file.";
   private static final String STACK_TRACE_SPEC_MESSAGE = "Please look at the following spec hierarchy and make sure all sibling children components of the same type have unique keys:\n";
   private final Set<String> mKnownGlobalKeys = new HashSet();
   @Nullable
   private final ComponentsLogger mLogger;


   public KeyHandler(@Nullable ComponentsLogger var1) {
      this.mLogger = var1;
   }

   private void checkIsDuplicateKey(Component var1) {
      if(this.mKnownGlobalKeys.contains(var1.getGlobalKey())) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Found another ");
         var2.append(var1.getSimpleName());
         var2.append(" Component with the same key: ");
         var2.append(var1.getKey());
         String var3 = var2.toString();
         String var8;
         if(this.mLogger == null) {
            var8 = var3;
         } else {
            var8 = this.getDuplicateKeyMessage();
         }

         if(var1.hasState()) {
            StringBuilder var7 = new StringBuilder();
            var7.append(var3);
            var7.append("\n");
            var7.append(var8);
            throw new RuntimeException(var7.toString());
         }

         if(this.mLogger != null) {
            ComponentsLogger var6 = this.mLogger;
            ComponentsLogger.LogLevel var4 = ComponentsLogger.LogLevel.ERROR;
            StringBuilder var5 = new StringBuilder();
            var5.append(var3);
            var5.append("\n");
            var5.append(var8);
            var6.emitMessage(var4, var5.toString());
         }
      }

   }

   private static String format(List<String> var0) {
      Collections.reverse(var0);
      StringBuilder var3 = new StringBuilder();
      var3.append("Please look at the following spec hierarchy and make sure all sibling children components of the same type have unique keys:\n");
      Iterator var5 = var0.iterator();
      int var1 = 1;

      while(var5.hasNext()) {
         String var4 = (String)var5.next();

         for(int var2 = 0; var2 < var1; ++var2) {
            var3.append("\t");
         }

         ++var1;
         var3.append(var4);
         var3.append("\n");
      }

      return var3.toString();
   }

   private String getDuplicateKeyMessage() {
      StackTraceElement[] var5 = Thread.currentThread().getStackTrace();
      ArrayList var6 = new ArrayList();
      int var3 = var5.length;

      for(int var1 = 0; var1 < var3; ++var1) {
         String var7 = var5[var1].getFileName();
         if(var7 != null) {
            boolean var4 = var6.isEmpty();
            boolean var2 = true;
            if(var4 || !((String)var6.get(var6.size() - 1)).equals(var7)) {
               var2 = false;
            }

            if(this.hasMatch(var7) && !this.mLogger.getKeyCollisionStackTraceBlacklist().contains(var7) && !var2) {
               var6.add(var7);
            }
         }
      }

      if(var6.isEmpty()) {
         return "Unable to determine root of duplicate key in a *Spec.java file.";
      } else {
         return format(var6);
      }
   }

   private boolean hasMatch(String var1) {
      Iterator var2 = this.mLogger.getKeyCollisionStackTraceKeywords().iterator();

      do {
         if(!var2.hasNext()) {
            return false;
         }
      } while(!var1.contains((String)var2.next()));

      return true;
   }

   public boolean hasKey(String var1) {
      return this.mKnownGlobalKeys.contains(var1);
   }

   public void registerKey(Component var1) {
      this.checkIsDuplicateKey(var1);
      this.mKnownGlobalKeys.add(var1.getGlobalKey());
   }
}
