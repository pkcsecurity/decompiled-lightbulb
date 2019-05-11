package com.facebook.litho.sections;

import com.facebook.litho.sections.logger.SectionsDebugLogger;
import com.facebook.litho.widget.RenderInfo;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Logger implements SectionsDebugLogger {

   protected Set<SectionsDebugLogger> mSectionsDebugLoggers = new HashSet();


   public Logger(Collection<SectionsDebugLogger> var1) {
      if(var1 != null) {
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            SectionsDebugLogger var2 = (SectionsDebugLogger)var3.next();
            if(var2 != null) {
               this.mSectionsDebugLoggers.add(var2);
            }
         }
      }

   }

   public void logDelete(String var1, int var2, String var3) {
      Iterator var4 = this.mSectionsDebugLoggers.iterator();

      while(var4.hasNext()) {
         ((SectionsDebugLogger)var4.next()).logDelete(var1, var2, var3);
      }

   }

   public void logInsert(String var1, int var2, RenderInfo var3, String var4) {
      Iterator var5 = this.mSectionsDebugLoggers.iterator();

      while(var5.hasNext()) {
         ((SectionsDebugLogger)var5.next()).logInsert(var1, var2, var3, var4);
      }

   }

   public void logMove(String var1, int var2, int var3, String var4) {
      Iterator var5 = this.mSectionsDebugLoggers.iterator();

      while(var5.hasNext()) {
         ((SectionsDebugLogger)var5.next()).logMove(var1, var2, var3, var4);
      }

   }

   public void logRequestFocus(String var1, int var2, RenderInfo var3, String var4) {
      Iterator var5 = this.mSectionsDebugLoggers.iterator();

      while(var5.hasNext()) {
         ((SectionsDebugLogger)var5.next()).logRequestFocus(var1, var2, var3, var4);
      }

   }

   public void logRequestFocusWithOffset(String var1, int var2, int var3, RenderInfo var4, String var5) {
      Iterator var6 = this.mSectionsDebugLoggers.iterator();

      while(var6.hasNext()) {
         ((SectionsDebugLogger)var6.next()).logRequestFocusWithOffset(var1, var2, var3, var4, var5);
      }

   }

   public void logShouldUpdate(String var1, Object var2, Object var3, String var4, String var5, Boolean var6, String var7) {
      Iterator var8 = this.mSectionsDebugLoggers.iterator();

      while(var8.hasNext()) {
         ((SectionsDebugLogger)var8.next()).logShouldUpdate(var1, var2, var3, var4, var5, var6, var7);
      }

   }

   public void logUpdate(String var1, int var2, RenderInfo var3, String var4) {
      Iterator var5 = this.mSectionsDebugLoggers.iterator();

      while(var5.hasNext()) {
         ((SectionsDebugLogger)var5.next()).logUpdate(var1, var2, var3, var4);
      }

   }
}
