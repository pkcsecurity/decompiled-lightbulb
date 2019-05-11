package com.facebook.litho;

import android.support.annotation.Nullable;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentsLogger;
import com.facebook.litho.PerfEvent;
import com.facebook.litho.TreeProps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.CheckReturnValue;

public final class LogTreePopulator {

   @Nullable
   public static String getAnnotationBundleFromLogger(Component var0, ComponentsLogger var1) {
      ComponentContext var3 = var0.getScopedContext();
      if(var3 == null) {
         return null;
      } else {
         TreeProps var4 = var3.getTreeProps();
         if(var4 == null) {
            return null;
         } else {
            Map var6 = var1.getExtraAnnotations(var4);
            if(var6 == null) {
               return null;
            } else {
               StringBuilder var5 = new StringBuilder(var6.size() * 16);
               Iterator var7 = var6.entrySet().iterator();

               while(var7.hasNext()) {
                  Entry var2 = (Entry)var7.next();
                  var5.append((String)var2.getKey());
                  var5.append(':');
                  var5.append((String)var2.getValue());
                  var5.append(':');
               }

               return var5.toString();
            }
         }
      }
   }

   @CheckReturnValue
   @Nullable
   public static PerfEvent populatePerfEventFromLogger(ComponentContext var0, ComponentsLogger var1, PerfEvent var2) {
      String var3 = var0.getLogTag();
      if(var3 == null) {
         var1.cancelPerfEvent(var2);
         return null;
      } else {
         var2.markerAnnotate("log_tag", var3);
         TreeProps var4 = var0.getTreeProps();
         if(var4 == null) {
            return var2;
         } else {
            Map var5 = var1.getExtraAnnotations(var4);
            if(var5 == null) {
               return var2;
            } else {
               Iterator var6 = var5.entrySet().iterator();

               while(var6.hasNext()) {
                  Entry var7 = (Entry)var6.next();
                  var2.markerAnnotate((String)var7.getKey(), (String)var7.getValue());
               }

               return var2;
            }
         }
      }
   }
}
