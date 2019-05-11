package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.facebook.AccessTokenSource;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class LegacyTokenHelper {

   public static final String APPLICATION_ID_KEY = "com.facebook.TokenCachingStrategy.ApplicationId";
   public static final String DECLINED_PERMISSIONS_KEY = "com.facebook.TokenCachingStrategy.DeclinedPermissions";
   public static final String DEFAULT_CACHE_KEY = "com.facebook.SharedPreferencesTokenCachingStrategy.DEFAULT_KEY";
   public static final String EXPIRATION_DATE_KEY = "com.facebook.TokenCachingStrategy.ExpirationDate";
   private static final long INVALID_BUNDLE_MILLISECONDS = Long.MIN_VALUE;
   private static final String IS_SSO_KEY = "com.facebook.TokenCachingStrategy.IsSSO";
   private static final String JSON_VALUE = "value";
   private static final String JSON_VALUE_ENUM_TYPE = "enumType";
   private static final String JSON_VALUE_TYPE = "valueType";
   public static final String LAST_REFRESH_DATE_KEY = "com.facebook.TokenCachingStrategy.LastRefreshDate";
   public static final String PERMISSIONS_KEY = "com.facebook.TokenCachingStrategy.Permissions";
   private static final String TAG = "LegacyTokenHelper";
   public static final String TOKEN_KEY = "com.facebook.TokenCachingStrategy.Token";
   public static final String TOKEN_SOURCE_KEY = "com.facebook.TokenCachingStrategy.AccessTokenSource";
   private static final String TYPE_BOOLEAN = "bool";
   private static final String TYPE_BOOLEAN_ARRAY = "bool[]";
   private static final String TYPE_BYTE = "byte";
   private static final String TYPE_BYTE_ARRAY = "byte[]";
   private static final String TYPE_CHAR = "char";
   private static final String TYPE_CHAR_ARRAY = "char[]";
   private static final String TYPE_DOUBLE = "double";
   private static final String TYPE_DOUBLE_ARRAY = "double[]";
   private static final String TYPE_ENUM = "enum";
   private static final String TYPE_FLOAT = "float";
   private static final String TYPE_FLOAT_ARRAY = "float[]";
   private static final String TYPE_INTEGER = "int";
   private static final String TYPE_INTEGER_ARRAY = "int[]";
   private static final String TYPE_LONG = "long";
   private static final String TYPE_LONG_ARRAY = "long[]";
   private static final String TYPE_SHORT = "short";
   private static final String TYPE_SHORT_ARRAY = "short[]";
   private static final String TYPE_STRING = "string";
   private static final String TYPE_STRING_LIST = "stringList";
   private SharedPreferences cache;
   private String cacheKey;


   public LegacyTokenHelper(Context var1) {
      this(var1, (String)null);
   }

   public LegacyTokenHelper(Context var1, String var2) {
      Validate.notNull(var1, "context");
      String var3 = var2;
      if(Utility.isNullOrEmpty(var2)) {
         var3 = "com.facebook.SharedPreferencesTokenCachingStrategy.DEFAULT_KEY";
      }

      this.cacheKey = var3;
      Context var4 = var1.getApplicationContext();
      if(var4 != null) {
         var1 = var4;
      }

      this.cache = var1.getSharedPreferences(this.cacheKey, 0);
   }

   private void deserializeKey(String var1, Bundle var2) throws JSONException {
      JSONObject var12 = new JSONObject(this.cache.getString(var1, "{}"));
      String var13 = var12.getString("valueType");
      if(var13.equals("bool")) {
         var2.putBoolean(var1, var12.getBoolean("value"));
      } else {
         boolean var11 = var13.equals("bool[]");
         byte var5 = 0;
         byte var6 = 0;
         byte var7 = 0;
         byte var8 = 0;
         byte var9 = 0;
         byte var10 = 0;
         int var3 = 0;
         byte var4 = 0;
         JSONArray var17;
         if(var11) {
            var17 = var12.getJSONArray("value");
            boolean[] var29 = new boolean[var17.length()];

            for(var3 = var4; var3 < var29.length; ++var3) {
               var29[var3] = var17.getBoolean(var3);
            }

            var2.putBooleanArray(var1, var29);
         } else if(var13.equals("byte")) {
            var2.putByte(var1, (byte)var12.getInt("value"));
         } else if(var13.equals("byte[]")) {
            var17 = var12.getJSONArray("value");
            byte[] var28 = new byte[var17.length()];

            for(var3 = var5; var3 < var28.length; ++var3) {
               var28[var3] = (byte)var17.getInt(var3);
            }

            var2.putByteArray(var1, var28);
         } else if(var13.equals("short")) {
            var2.putShort(var1, (short)var12.getInt("value"));
         } else if(var13.equals("short[]")) {
            var17 = var12.getJSONArray("value");
            short[] var27 = new short[var17.length()];

            for(var3 = var6; var3 < var27.length; ++var3) {
               var27[var3] = (short)var17.getInt(var3);
            }

            var2.putShortArray(var1, var27);
         } else if(var13.equals("int")) {
            var2.putInt(var1, var12.getInt("value"));
         } else if(var13.equals("int[]")) {
            var17 = var12.getJSONArray("value");
            int[] var26 = new int[var17.length()];

            for(var3 = var7; var3 < var26.length; ++var3) {
               var26[var3] = var17.getInt(var3);
            }

            var2.putIntArray(var1, var26);
         } else if(var13.equals("long")) {
            var2.putLong(var1, var12.getLong("value"));
         } else if(var13.equals("long[]")) {
            var17 = var12.getJSONArray("value");
            long[] var25 = new long[var17.length()];

            for(var3 = var8; var3 < var25.length; ++var3) {
               var25[var3] = var17.getLong(var3);
            }

            var2.putLongArray(var1, var25);
         } else if(var13.equals("float")) {
            var2.putFloat(var1, (float)var12.getDouble("value"));
         } else if(var13.equals("float[]")) {
            var17 = var12.getJSONArray("value");
            float[] var23 = new float[var17.length()];

            for(var3 = var9; var3 < var23.length; ++var3) {
               var23[var3] = (float)var17.getDouble(var3);
            }

            var2.putFloatArray(var1, var23);
         } else if(var13.equals("double")) {
            var2.putDouble(var1, var12.getDouble("value"));
         } else if(!var13.equals("double[]")) {
            String var18;
            if(var13.equals("char")) {
               var18 = var12.getString("value");
               if(var18 != null && var18.length() == 1) {
                  var2.putChar(var1, var18.charAt(0));
                  return;
               }
            } else {
               if(var13.equals("char[]")) {
                  var17 = var12.getJSONArray("value");
                  char[] var22 = new char[var17.length()];

                  for(var3 = 0; var3 < var22.length; ++var3) {
                     String var24 = var17.getString(var3);
                     if(var24 != null && var24.length() == 1) {
                        var22[var3] = var24.charAt(0);
                     }
                  }

                  var2.putCharArray(var1, var22);
                  return;
               }

               if(var13.equals("string")) {
                  var2.putString(var1, var12.getString("value"));
                  return;
               }

               if(var13.equals("stringList")) {
                  JSONArray var21 = var12.getJSONArray("value");
                  int var16 = var21.length();

                  ArrayList var14;
                  for(var14 = new ArrayList(var16); var3 < var16; ++var3) {
                     Object var19 = var21.get(var3);
                     if(var19 == JSONObject.NULL) {
                        var18 = null;
                     } else {
                        var18 = (String)var19;
                     }

                     var14.add(var3, var18);
                  }

                  var2.putStringArrayList(var1, var14);
                  return;
               }

               if(var13.equals("enum")) {
                  try {
                     var2.putSerializable(var1, Enum.valueOf(Class.forName(var12.getString("enumType")), var12.getString("value")));
                  } catch (IllegalArgumentException var15) {
                     return;
                  }
               }
            }

         } else {
            var17 = var12.getJSONArray("value");
            double[] var20 = new double[var17.length()];

            for(var3 = var10; var3 < var20.length; ++var3) {
               var20[var3] = var17.getDouble(var3);
            }

            var2.putDoubleArray(var1, var20);
         }
      }
   }

   public static String getApplicationId(Bundle var0) {
      Validate.notNull(var0, "bundle");
      return var0.getString("com.facebook.TokenCachingStrategy.ApplicationId");
   }

   static Date getDate(Bundle var0, String var1) {
      if(var0 == null) {
         return null;
      } else {
         long var2 = var0.getLong(var1, Long.MIN_VALUE);
         return var2 == Long.MIN_VALUE?null:new Date(var2);
      }
   }

   public static Date getExpirationDate(Bundle var0) {
      Validate.notNull(var0, "bundle");
      return getDate(var0, "com.facebook.TokenCachingStrategy.ExpirationDate");
   }

   public static long getExpirationMilliseconds(Bundle var0) {
      Validate.notNull(var0, "bundle");
      return var0.getLong("com.facebook.TokenCachingStrategy.ExpirationDate");
   }

   public static Date getLastRefreshDate(Bundle var0) {
      Validate.notNull(var0, "bundle");
      return getDate(var0, "com.facebook.TokenCachingStrategy.LastRefreshDate");
   }

   public static long getLastRefreshMilliseconds(Bundle var0) {
      Validate.notNull(var0, "bundle");
      return var0.getLong("com.facebook.TokenCachingStrategy.LastRefreshDate");
   }

   public static Set<String> getPermissions(Bundle var0) {
      Validate.notNull(var0, "bundle");
      ArrayList var1 = var0.getStringArrayList("com.facebook.TokenCachingStrategy.Permissions");
      return var1 == null?null:new HashSet(var1);
   }

   public static AccessTokenSource getSource(Bundle var0) {
      Validate.notNull(var0, "bundle");
      return var0.containsKey("com.facebook.TokenCachingStrategy.AccessTokenSource")?(AccessTokenSource)var0.getSerializable("com.facebook.TokenCachingStrategy.AccessTokenSource"):(var0.getBoolean("com.facebook.TokenCachingStrategy.IsSSO")?AccessTokenSource.FACEBOOK_APPLICATION_WEB:AccessTokenSource.WEB_VIEW);
   }

   public static String getToken(Bundle var0) {
      Validate.notNull(var0, "bundle");
      return var0.getString("com.facebook.TokenCachingStrategy.Token");
   }

   public static boolean hasTokenInformation(Bundle var0) {
      if(var0 == null) {
         return false;
      } else {
         String var1 = var0.getString("com.facebook.TokenCachingStrategy.Token");
         return var1 != null?(var1.length() == 0?false:var0.getLong("com.facebook.TokenCachingStrategy.ExpirationDate", 0L) != 0L):false;
      }
   }

   public static void putApplicationId(Bundle var0, String var1) {
      Validate.notNull(var0, "bundle");
      var0.putString("com.facebook.TokenCachingStrategy.ApplicationId", var1);
   }

   static void putDate(Bundle var0, String var1, Date var2) {
      var0.putLong(var1, var2.getTime());
   }

   public static void putDeclinedPermissions(Bundle var0, Collection<String> var1) {
      Validate.notNull(var0, "bundle");
      Validate.notNull(var1, "value");
      var0.putStringArrayList("com.facebook.TokenCachingStrategy.DeclinedPermissions", new ArrayList(var1));
   }

   public static void putExpirationDate(Bundle var0, Date var1) {
      Validate.notNull(var0, "bundle");
      Validate.notNull(var1, "value");
      putDate(var0, "com.facebook.TokenCachingStrategy.ExpirationDate", var1);
   }

   public static void putExpirationMilliseconds(Bundle var0, long var1) {
      Validate.notNull(var0, "bundle");
      var0.putLong("com.facebook.TokenCachingStrategy.ExpirationDate", var1);
   }

   public static void putLastRefreshDate(Bundle var0, Date var1) {
      Validate.notNull(var0, "bundle");
      Validate.notNull(var1, "value");
      putDate(var0, "com.facebook.TokenCachingStrategy.LastRefreshDate", var1);
   }

   public static void putLastRefreshMilliseconds(Bundle var0, long var1) {
      Validate.notNull(var0, "bundle");
      var0.putLong("com.facebook.TokenCachingStrategy.LastRefreshDate", var1);
   }

   public static void putPermissions(Bundle var0, Collection<String> var1) {
      Validate.notNull(var0, "bundle");
      Validate.notNull(var1, "value");
      var0.putStringArrayList("com.facebook.TokenCachingStrategy.Permissions", new ArrayList(var1));
   }

   public static void putSource(Bundle var0, AccessTokenSource var1) {
      Validate.notNull(var0, "bundle");
      var0.putSerializable("com.facebook.TokenCachingStrategy.AccessTokenSource", var1);
   }

   public static void putToken(Bundle var0, String var1) {
      Validate.notNull(var0, "bundle");
      Validate.notNull(var1, "value");
      var0.putString("com.facebook.TokenCachingStrategy.Token", var1);
   }

   private void serializeKey(String var1, Bundle var2, Editor var3) throws JSONException {
      Object var15 = var2.get(var1);
      if(var15 != null) {
         JSONObject var16 = new JSONObject();
         boolean var12 = var15 instanceof Byte;
         String var14 = null;
         JSONArray var13;
         String var18;
         if(var12) {
            var18 = "byte";
            var16.put("value", ((Byte)var15).intValue());
            var13 = var14;
         } else if(var15 instanceof Short) {
            var18 = "short";
            var16.put("value", ((Short)var15).intValue());
            var13 = var14;
         } else if(var15 instanceof Integer) {
            var18 = "int";
            var16.put("value", ((Integer)var15).intValue());
            var13 = var14;
         } else if(var15 instanceof Long) {
            var18 = "long";
            var16.put("value", ((Long)var15).longValue());
            var13 = var14;
         } else if(var15 instanceof Float) {
            var18 = "float";
            var16.put("value", ((Float)var15).doubleValue());
            var13 = var14;
         } else if(var15 instanceof Double) {
            var18 = "double";
            var16.put("value", ((Double)var15).doubleValue());
            var13 = var14;
         } else if(var15 instanceof Boolean) {
            var18 = "bool";
            var16.put("value", ((Boolean)var15).booleanValue());
            var13 = var14;
         } else if(var15 instanceof Character) {
            var18 = "char";
            var16.put("value", var15.toString());
            var13 = var14;
         } else if(var15 instanceof String) {
            var18 = "string";
            var16.put("value", (String)var15);
            var13 = var14;
         } else if(var15 instanceof Enum) {
            var18 = "enum";
            var16.put("value", var15.toString());
            var16.put("enumType", var15.getClass().getName());
            var13 = var14;
         } else {
            var13 = new JSONArray();
            var12 = var15 instanceof byte[];
            byte var5 = 0;
            byte var6 = 0;
            byte var7 = 0;
            byte var8 = 0;
            byte var9 = 0;
            byte var10 = 0;
            byte var11 = 0;
            int var4 = 0;
            int var19;
            if(var12) {
               var14 = "byte[]";
               byte[] var22 = (byte[])var15;
               var19 = var22.length;

               while(true) {
                  var18 = var14;
                  if(var4 >= var19) {
                     break;
                  }

                  var13.put(var22[var4]);
                  ++var4;
               }
            } else if(var15 instanceof short[]) {
               var14 = "short[]";
               short[] var23 = (short[])var15;
               int var20 = var23.length;
               var4 = var5;

               while(true) {
                  var18 = var14;
                  if(var4 >= var20) {
                     break;
                  }

                  var13.put(var23[var4]);
                  ++var4;
               }
            } else if(var15 instanceof int[]) {
               var14 = "int[]";
               int[] var24 = (int[])var15;
               var19 = var24.length;
               var4 = var6;

               while(true) {
                  var18 = var14;
                  if(var4 >= var19) {
                     break;
                  }

                  var13.put(var24[var4]);
                  ++var4;
               }
            } else if(var15 instanceof long[]) {
               var14 = "long[]";
               long[] var25 = (long[])var15;
               var19 = var25.length;
               var4 = var7;

               while(true) {
                  var18 = var14;
                  if(var4 >= var19) {
                     break;
                  }

                  var13.put(var25[var4]);
                  ++var4;
               }
            } else if(var15 instanceof float[]) {
               var14 = "float[]";
               float[] var26 = (float[])var15;
               var19 = var26.length;
               var4 = var8;

               while(true) {
                  var18 = var14;
                  if(var4 >= var19) {
                     break;
                  }

                  var13.put((double)var26[var4]);
                  ++var4;
               }
            } else if(var15 instanceof double[]) {
               var14 = "double[]";
               double[] var27 = (double[])var15;
               var19 = var27.length;
               var4 = var9;

               while(true) {
                  var18 = var14;
                  if(var4 >= var19) {
                     break;
                  }

                  var13.put(var27[var4]);
                  ++var4;
               }
            } else if(var15 instanceof boolean[]) {
               var14 = "bool[]";
               boolean[] var28 = (boolean[])var15;
               var19 = var28.length;
               var4 = var10;

               while(true) {
                  var18 = var14;
                  if(var4 >= var19) {
                     break;
                  }

                  var13.put(var28[var4]);
                  ++var4;
               }
            } else if(var15 instanceof char[]) {
               var14 = "char[]";
               char[] var29 = (char[])var15;
               var19 = var29.length;
               var4 = var11;

               while(true) {
                  var18 = var14;
                  if(var4 >= var19) {
                     break;
                  }

                  var13.put(String.valueOf(var29[var4]));
                  ++var4;
               }
            } else if(var15 instanceof List) {
               var14 = "stringList";
               Iterator var17 = ((List)var15).iterator();

               while(true) {
                  var18 = var14;
                  if(!var17.hasNext()) {
                     break;
                  }

                  String var30 = (String)var17.next();
                  Object var21 = var30;
                  if(var30 == null) {
                     var21 = JSONObject.NULL;
                  }

                  var13.put(var21);
               }
            } else {
               var18 = null;
               var13 = var14;
            }
         }

         if(var18 != null) {
            var16.put("valueType", var18);
            if(var13 != null) {
               var16.putOpt("value", var13);
            }

            var3.putString(var1, var16.toString());
         }

      }
   }

   public void clear() {
      this.cache.edit().clear().apply();
   }

   public Bundle load() {
      Bundle var2 = new Bundle();
      Iterator var3 = this.cache.getAll().keySet().iterator();

      while(var3.hasNext()) {
         String var1 = (String)var3.next();

         try {
            this.deserializeKey(var1, var2);
         } catch (JSONException var6) {
            LoggingBehavior var7 = LoggingBehavior.CACHE;
            String var4 = TAG;
            StringBuilder var5 = new StringBuilder();
            var5.append("Error reading cached value for key: \'");
            var5.append(var1);
            var5.append("\' -- ");
            var5.append(var6);
            Logger.log(var7, 5, var4, var5.toString());
            return null;
         }
      }

      return var2;
   }

   public void save(Bundle var1) {
      Validate.notNull(var1, "bundle");
      Editor var3 = this.cache.edit();
      Iterator var4 = var1.keySet().iterator();

      while(var4.hasNext()) {
         String var2 = (String)var4.next();

         try {
            this.serializeKey(var2, var1, var3);
         } catch (JSONException var6) {
            LoggingBehavior var7 = LoggingBehavior.CACHE;
            String var8 = TAG;
            StringBuilder var5 = new StringBuilder();
            var5.append("Error processing value for key: \'");
            var5.append(var2);
            var5.append("\' -- ");
            var5.append(var6);
            Logger.log(var7, 5, var8, var5.toString());
            return;
         }
      }

      var3.apply();
   }
}
