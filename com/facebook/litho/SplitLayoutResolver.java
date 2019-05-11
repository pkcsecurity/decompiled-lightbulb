package com.facebook.litho;

import android.os.Looper;
import android.support.annotation.GuardedBy;
import android.support.annotation.VisibleForTesting;
import com.facebook.infer.annotation.ThreadSafe;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.InternalNode;
import com.facebook.litho.Layout;
import com.facebook.litho.LayoutThreadPoolExecutor;
import com.facebook.litho.ThreadUtils;
import com.facebook.litho.config.LayoutThreadPoolConfiguration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorCompletionService;
import javax.annotation.Nullable;

@ThreadSafe
public class SplitLayoutResolver {

   @GuardedBy("SplitLayoutResolver.class")
   private static final Map<String, SplitLayoutResolver> sSplitLayoutResolvers = new HashMap();
   @Nullable
   private ExecutorCompletionService bgService;
   private final Set<String> mEnabledComponents = new LinkedHashSet();
   @Nullable
   private ExecutorCompletionService mainService;


   private SplitLayoutResolver(@Nullable LayoutThreadPoolConfiguration var1, @Nullable LayoutThreadPoolConfiguration var2, Set<String> var3) {
      if(var1 != null) {
         this.mainService = new ExecutorCompletionService(new LayoutThreadPoolExecutor(var1.getCorePoolSize(), var1.getMaxPoolSize(), var1.getThreadPriority()));
      }

      if(var2 != null) {
         this.bgService = new ExecutorCompletionService(new LayoutThreadPoolExecutor(var2.getCorePoolSize(), var2.getMaxPoolSize(), var2.getThreadPriority()));
      }

      if(var3 != null) {
         this.mEnabledComponents.addAll(var3);
      }

   }

   private boolean canSplitLayoutOnCurrentThread() {
      if(ThreadUtils.isMainThread()) {
         if(this.mainService != null) {
            return true;
         }
      } else if(this.bgService != null) {
         return true;
      }

      return false;
   }

   @VisibleForTesting
   static void clearTag(String var0) {
      sSplitLayoutResolvers.remove(var0);
   }

   public static void createForTag(String param0, @Nullable LayoutThreadPoolConfiguration param1, @Nullable LayoutThreadPoolConfiguration param2, Set<String> param3) {
      // $FF: Couldn't be decompiled
   }

   private static InternalNode getChildLayout(ComponentContext var0, Component var1) {
      return var1 != null?Layout.create(var0, var1):ComponentContext.NULL_LAYOUT;
   }

   @VisibleForTesting
   static SplitLayoutResolver getForTag(String var0) {
      return (SplitLayoutResolver)sSplitLayoutResolvers.get(var0);
   }

   @Nullable
   private static SplitLayoutResolver getResolver(ComponentContext param0) {
      // $FF: Couldn't be decompiled
   }

   static boolean isComponentEnabledForSplitting(ComponentContext var0, Component var1) {
      SplitLayoutResolver var2 = getResolver(var0);
      return var2 != null && var2.mEnabledComponents.contains(var1.getClass().getSimpleName());
   }

   static boolean resolveLayouts(ComponentContext var0, List<Component> var1, final InternalNode var2) {
      SplitLayoutResolver var9 = getResolver(var0);
      byte var5 = 0;
      if(var9 == null) {
         return false;
      } else if(!var9.canSplitLayoutOnCurrentThread()) {
         return false;
      } else {
         ExecutorCompletionService var10;
         if(ThreadUtils.isMainThread()) {
            var10 = var9.mainService;
         } else {
            var10 = var9.bgService;
         }

         final InternalNode[] var7 = new InternalNode[var1.size()];
         int var6 = var1.size();

         final int var3;
         for(var3 = 1; var3 < var6; ++var3) {
            var10.submit(new Runnable() {

               // $FF: synthetic field
               final Component val$child;

               {
                  this.val$child = var4;
               }
               public void run() {
                  if(Looper.myLooper() == null) {
                     Looper.prepare();
                  }

                  var7[var3] = SplitLayoutResolver.getChildLayout(var2.getContext(), this.val$child);
               }
            }, Integer.valueOf(var3 - 1));
         }

         var7[0] = getChildLayout(var2.getContext(), (Component)var1.get(0));
         var3 = 0;

         while(true) {
            int var4 = var5;
            if(var3 >= var6 - 1) {
               while(var4 < var7.length) {
                  var2.child(var7[var4]);
                  ++var4;
               }

               return true;
            }

            try {
               var10.take();
            } catch (InterruptedException var8) {
               throw new RuntimeException("Could not execute split layout task", var8);
            }

            ++var3;
         }
      }
   }
}
