package com.facebook.react.uimanager.util;

import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class ReactFindViewUtil {

   private static final List<ReactFindViewUtil.OnViewFoundListener> mOnViewFoundListeners = new ArrayList();


   public static void addViewListener(ReactFindViewUtil.OnViewFoundListener var0) {
      mOnViewFoundListeners.add(var0);
   }

   @Nullable
   public static View findView(View var0, String var1) {
      String var3 = getNativeId(var0);
      if(var3 != null && var3.equals(var1)) {
         return var0;
      } else {
         if(var0 instanceof ViewGroup) {
            ViewGroup var4 = (ViewGroup)var0;

            for(int var2 = 0; var2 < var4.getChildCount(); ++var2) {
               View var5 = findView(var4.getChildAt(var2), var1);
               if(var5 != null) {
                  return var5;
               }
            }
         }

         return null;
      }
   }

   public static void findView(View var0, ReactFindViewUtil.OnViewFoundListener var1) {
      var0 = findView(var0, var1.getNativeId());
      if(var0 != null) {
         var1.onViewFound(var0);
      }

      addViewListener(var1);
   }

   @Nullable
   private static String getNativeId(View var0) {
      Object var1 = var0.getTag(R.id.view_tag_native_id);
      return var1 instanceof String?(String)var1:null;
   }

   public static void notifyViewRendered(View var0) {
      String var1 = getNativeId(var0);
      if(var1 != null) {
         Iterator var2 = mOnViewFoundListeners.iterator();

         while(var2.hasNext()) {
            ReactFindViewUtil.OnViewFoundListener var3 = (ReactFindViewUtil.OnViewFoundListener)var2.next();
            if(var1 != null && var1.equals(var3.getNativeId())) {
               var3.onViewFound(var0);
               var2.remove();
            }
         }

      }
   }

   public static void removeViewListener(ReactFindViewUtil.OnViewFoundListener var0) {
      mOnViewFoundListeners.remove(var0);
   }

   public interface OnViewFoundListener {

      String getNativeId();

      void onViewFound(View var1);
   }
}
