package com.facebook.appevents.codeless.internal;

import com.facebook.appevents.codeless.internal.PathComponent;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ParameterComponent {

   private static final String PARAMETER_NAME_KEY = "name";
   private static final String PARAMETER_PATH_KEY = "path";
   private static final String PARAMETER_VALUE_KEY = "value";
   public final String name;
   public final List<PathComponent> path;
   public final String pathType;
   public final String value;


   public ParameterComponent(JSONObject var1) throws JSONException {
      this.name = var1.getString("name");
      this.value = var1.optString("value");
      ArrayList var3 = new ArrayList();
      JSONArray var4 = var1.optJSONArray("path");
      if(var4 != null) {
         for(int var2 = 0; var2 < var4.length(); ++var2) {
            var3.add(new PathComponent(var4.getJSONObject(var2)));
         }
      }

      this.path = var3;
      this.pathType = var1.optString("path_type", "absolute");
   }
}
