package com.facebook.appevents.codeless.internal;

import com.facebook.appevents.codeless.internal.ParameterComponent;
import com.facebook.appevents.codeless.internal.PathComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventBinding {

   private final String activityName;
   private final String appVersion;
   private final String componentId;
   private final String eventName;
   private final EventBinding.MappingMethod method;
   private final List<ParameterComponent> parameters;
   private final List<PathComponent> path;
   private final String pathType;
   private final EventBinding.ActionType type;


   public EventBinding(String var1, EventBinding.MappingMethod var2, EventBinding.ActionType var3, String var4, List<PathComponent> var5, List<ParameterComponent> var6, String var7, String var8, String var9) {
      this.eventName = var1;
      this.method = var2;
      this.type = var3;
      this.appVersion = var4;
      this.path = var5;
      this.parameters = var6;
      this.componentId = var7;
      this.pathType = var8;
      this.activityName = var9;
   }

   public static EventBinding getInstanceFromJson(JSONObject var0) throws JSONException {
      String var3 = var0.getString("event_name");
      EventBinding.MappingMethod var4 = EventBinding.MappingMethod.valueOf(var0.getString("method").toUpperCase());
      EventBinding.ActionType var5 = EventBinding.ActionType.valueOf(var0.getString("event_type").toUpperCase());
      String var6 = var0.getString("app_version");
      JSONArray var8 = var0.getJSONArray("path");
      ArrayList var7 = new ArrayList();
      byte var2 = 0;

      int var1;
      for(var1 = 0; var1 < var8.length(); ++var1) {
         var7.add(new PathComponent(var8.getJSONObject(var1)));
      }

      String var11 = var0.optString("path_type", "absolute");
      JSONArray var9 = var0.optJSONArray("parameters");
      ArrayList var10 = new ArrayList();
      if(var9 != null) {
         for(var1 = var2; var1 < var9.length(); ++var1) {
            var10.add(new ParameterComponent(var9.getJSONObject(var1)));
         }
      }

      return new EventBinding(var3, var4, var5, var6, var7, var10, var0.optString("component_id"), var11, var0.optString("activity_name"));
   }

   public static List<EventBinding> parseArray(JSONArray param0) {
      // $FF: Couldn't be decompiled
   }

   public String getActivityName() {
      return this.activityName;
   }

   public String getAppVersion() {
      return this.appVersion;
   }

   public String getComponentId() {
      return this.componentId;
   }

   public String getEventName() {
      return this.eventName;
   }

   public EventBinding.MappingMethod getMethod() {
      return this.method;
   }

   public String getPathType() {
      return this.pathType;
   }

   public EventBinding.ActionType getType() {
      return this.type;
   }

   public List<ParameterComponent> getViewParameters() {
      return Collections.unmodifiableList(this.parameters);
   }

   public List<PathComponent> getViewPath() {
      return Collections.unmodifiableList(this.path);
   }

   public static enum ActionType {

      // $FF: synthetic field
      private static final EventBinding.ActionType[] $VALUES = new EventBinding.ActionType[]{CLICK, SELECTED, TEXT_CHANGED};
      CLICK("CLICK", 0),
      SELECTED("SELECTED", 1),
      TEXT_CHANGED("TEXT_CHANGED", 2);


      private ActionType(String var1, int var2) {}
   }

   public static enum MappingMethod {

      // $FF: synthetic field
      private static final EventBinding.MappingMethod[] $VALUES = new EventBinding.MappingMethod[]{MANUAL, INFERENCE};
      INFERENCE("INFERENCE", 1),
      MANUAL("MANUAL", 0);


      private MappingMethod(String var1, int var2) {}
   }
}
