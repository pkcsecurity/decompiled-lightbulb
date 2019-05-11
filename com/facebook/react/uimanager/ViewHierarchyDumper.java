package com.facebook.react.uimanager;

import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.UiThreadUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewHierarchyDumper {

   public static JSONObject toJSON(View var0) throws JSONException {
      UiThreadUtil.assertOnUiThread();
      JSONObject var2 = new JSONObject();
      var2.put("n", var0.getClass().getName());
      var2.put("i", System.identityHashCode(var0));
      Object var3 = var0.getTag();
      if(var3 != null && var3 instanceof String) {
         var2.put("t", var3);
      }

      if(var0 instanceof ViewGroup) {
         ViewGroup var4 = (ViewGroup)var0;
         if(var4.getChildCount() > 0) {
            JSONArray var5 = new JSONArray();

            for(int var1 = 0; var1 < var4.getChildCount(); ++var1) {
               var5.put(var1, toJSON(var4.getChildAt(var1)));
            }

            var2.put("c", var5);
         }
      }

      return var2;
   }
}
