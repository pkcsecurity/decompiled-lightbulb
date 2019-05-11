package com.facebook.appevents.codeless.internal;

import org.json.JSONException;
import org.json.JSONObject;

public final class PathComponent {

   private static final String PATH_CLASS_NAME_KEY = "class_name";
   private static final String PATH_DESCRIPTION_KEY = "description";
   private static final String PATH_HINT_KEY = "hint";
   private static final String PATH_ID_KEY = "id";
   private static final String PATH_INDEX_KEY = "index";
   private static final String PATH_MATCH_BITMASK_KEY = "match_bitmask";
   private static final String PATH_TAG_KEY = "tag";
   private static final String PATH_TEXT_KEY = "text";
   public final String className;
   public final String description;
   public final String hint;
   public final int id;
   public final int index;
   public final int matchBitmask;
   public final String tag;
   public final String text;


   PathComponent(JSONObject var1) throws JSONException {
      this.className = var1.getString("class_name");
      this.index = var1.optInt("index", -1);
      this.id = var1.optInt("id");
      this.text = var1.optString("text");
      this.tag = var1.optString("tag");
      this.description = var1.optString("description");
      this.hint = var1.optString("hint");
      this.matchBitmask = var1.optInt("match_bitmask");
   }

   public static enum MatchBitmaskType {

      // $FF: synthetic field
      private static final PathComponent.MatchBitmaskType[] $VALUES = new PathComponent.MatchBitmaskType[]{ID, TEXT, TAG, DESCRIPTION, HINT};
      DESCRIPTION("DESCRIPTION", 3, 8),
      HINT("HINT", 4, 16),
      ID("ID", 0, 1),
      TAG("TAG", 2, 4),
      TEXT("TEXT", 1, 2);
      private final int value;


      private MatchBitmaskType(String var1, int var2, int var3) {
         this.value = var3;
      }

      public int getValue() {
         return this.value;
      }
   }
}
