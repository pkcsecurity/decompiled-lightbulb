package com.google.android.gms.common.util;

import android.text.TextUtils;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.util.zzd;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@KeepForSdk
@VisibleForTesting
public final class JsonUtils {

   private static final Pattern zzhb = Pattern.compile("\\\\.");
   private static final Pattern zzhc = Pattern.compile("[\\\\\"/\b\f\n\r\t]");


   @KeepForSdk
   public static boolean areJsonValuesEquivalent(Object var0, Object var1) {
      if(var0 == null && var1 == null) {
         return true;
      } else if(var0 == null) {
         return false;
      } else if(var1 == null) {
         return false;
      } else {
         boolean var3;
         if(var0 instanceof JSONObject && var1 instanceof JSONObject) {
            JSONObject var9 = (JSONObject)var0;
            JSONObject var11 = (JSONObject)var1;
            if(var9.length() != var11.length()) {
               return false;
            } else {
               Iterator var4 = var9.keys();

               do {
                  if(!var4.hasNext()) {
                     return true;
                  }

                  String var5 = (String)var4.next();
                  if(!var11.has(var5)) {
                     return false;
                  }

                  try {
                     var3 = areJsonValuesEquivalent(var9.get(var5), var11.get(var5));
                  } catch (JSONException var6) {
                     return false;
                  }
               } while(var3);

               return false;
            }
         } else if(var0 instanceof JSONArray && var1 instanceof JSONArray) {
            JSONArray var8 = (JSONArray)var0;
            JSONArray var10 = (JSONArray)var1;
            if(var8.length() != var10.length()) {
               return false;
            } else {
               for(int var2 = 0; var2 < var8.length(); ++var2) {
                  try {
                     var3 = areJsonValuesEquivalent(var8.get(var2), var10.get(var2));
                  } catch (JSONException var7) {
                     return false;
                  }

                  if(!var3) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return var0.equals(var1);
         }
      }
   }

   @KeepForSdk
   public static String escapeString(String var0) {
      if(!TextUtils.isEmpty(var0)) {
         Matcher var4 = zzhc.matcher(var0);
         StringBuffer var2 = null;

         while(var4.find()) {
            StringBuffer var3 = var2;
            if(var2 == null) {
               var3 = new StringBuffer();
            }

            char var1 = var4.group().charAt(0);
            if(var1 != 34) {
               if(var1 != 47) {
                  if(var1 != 92) {
                     switch(var1) {
                     case 8:
                        var4.appendReplacement(var3, "\\\\b");
                        var2 = var3;
                        break;
                     case 9:
                        var4.appendReplacement(var3, "\\\\t");
                        var2 = var3;
                        break;
                     case 10:
                        var4.appendReplacement(var3, "\\\\n");
                        var2 = var3;
                        break;
                     default:
                        switch(var1) {
                        case 12:
                           var4.appendReplacement(var3, "\\\\f");
                           var2 = var3;
                           break;
                        case 13:
                           var4.appendReplacement(var3, "\\\\r");
                           var2 = var3;
                           break;
                        default:
                           var2 = var3;
                        }
                     }
                  } else {
                     var4.appendReplacement(var3, "\\\\\\\\");
                     var2 = var3;
                  }
               } else {
                  var4.appendReplacement(var3, "\\\\/");
                  var2 = var3;
               }
            } else {
               var4.appendReplacement(var3, "\\\\\\\"");
               var2 = var3;
            }
         }

         if(var2 == null) {
            return var0;
         } else {
            var4.appendTail(var2);
            return var2.toString();
         }
      } else {
         return var0;
      }
   }

   @KeepForSdk
   public static String unescapeString(String var0) {
      if(!TextUtils.isEmpty(var0)) {
         String var3 = zzd.unescape(var0);
         Matcher var4 = zzhb.matcher(var3);
         StringBuffer var5 = null;

         while(var4.find()) {
            StringBuffer var2 = var5;
            if(var5 == null) {
               var2 = new StringBuffer();
            }

            char var1 = var4.group().charAt(1);
            if(var1 != 34) {
               if(var1 != 47) {
                  if(var1 != 92) {
                     if(var1 != 98) {
                        if(var1 != 102) {
                           if(var1 != 110) {
                              if(var1 != 114) {
                                 if(var1 != 116) {
                                    throw new IllegalStateException("Found an escaped character that should never be.");
                                 }

                                 var4.appendReplacement(var2, "\t");
                                 var5 = var2;
                              } else {
                                 var4.appendReplacement(var2, "\r");
                                 var5 = var2;
                              }
                           } else {
                              var4.appendReplacement(var2, "\n");
                              var5 = var2;
                           }
                        } else {
                           var4.appendReplacement(var2, "\f");
                           var5 = var2;
                        }
                     } else {
                        var4.appendReplacement(var2, "\b");
                        var5 = var2;
                     }
                  } else {
                     var4.appendReplacement(var2, "\\\\");
                     var5 = var2;
                  }
               } else {
                  var4.appendReplacement(var2, "/");
                  var5 = var2;
               }
            } else {
               var4.appendReplacement(var2, "\"");
               var5 = var2;
            }
         }

         if(var5 == null) {
            return var3;
         } else {
            var4.appendTail(var5);
            return var5.toString();
         }
      } else {
         return var0;
      }
   }
}
