package android.support.v4.text.util;

import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import java.util.Locale;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class FindAddress {

   private static final String HOUSE_COMPONENT = "(?:one|\\d+([a-z](?=[^a-z]|$)|st|nd|rd|th)?)";
   private static final String HOUSE_END = "(?=[,\"\'\t                　\n\f\r  ]|$)";
   private static final String HOUSE_POST_DELIM = ",\"\'\t                　\n\f\r  ";
   private static final String HOUSE_PRE_DELIM = ":,\"\'\t                　\n\f\r  ";
   private static final int MAX_ADDRESS_LINES = 5;
   private static final int MAX_ADDRESS_WORDS = 14;
   private static final int MAX_LOCATION_NAME_DISTANCE = 5;
   private static final int MIN_ADDRESS_WORDS = 4;
   private static final String NL = "\n\f\r  ";
   private static final String SP = "\t                　";
   private static final String WORD_DELIM = ",*•\t                　\n\f\r  ";
   private static final String WORD_END = "(?=[,*•\t                　\n\f\r  ]|$)";
   private static final String WS = "\t                　\n\f\r  ";
   private static final int kMaxAddressNameWordLength = 25;
   private static final Pattern sHouseNumberRe = Pattern.compile("(?:one|\\d+([a-z](?=[^a-z]|$)|st|nd|rd|th)?)(?:-(?:one|\\d+([a-z](?=[^a-z]|$)|st|nd|rd|th)?))*(?=[,\"\'\t                　\n\f\r  ]|$)", 2);
   private static final Pattern sLocationNameRe = Pattern.compile("(?:alley|annex|arcade|ave[.]?|avenue|alameda|bayou|beach|bend|bluffs?|bottom|boulevard|branch|bridge|brooks?|burgs?|bypass|broadway|camino|camp|canyon|cape|causeway|centers?|circles?|cliffs?|club|common|corners?|course|courts?|coves?|creek|crescent|crest|crossing|crossroad|curve|circulo|dale|dam|divide|drives?|estates?|expressway|extensions?|falls?|ferry|fields?|flats?|fords?|forest|forges?|forks?|fort|freeway|gardens?|gateway|glens?|greens?|groves?|harbors?|haven|heights|highway|hills?|hollow|inlet|islands?|isle|junctions?|keys?|knolls?|lakes?|land|landing|lane|lights?|loaf|locks?|lodge|loop|mall|manors?|meadows?|mews|mills?|mission|motorway|mount|mountains?|neck|orchard|oval|overpass|parks?|parkways?|pass|passage|path|pike|pines?|plains?|plaza|points?|ports?|prairie|privada|radial|ramp|ranch|rapids?|rd[.]?|rest|ridges?|river|roads?|route|row|rue|run|shoals?|shores?|skyway|springs?|spurs?|squares?|station|stravenue|stream|st[.]?|streets?|summit|speedway|terrace|throughway|trace|track|trafficway|trail|tunnel|turnpike|underpass|unions?|valleys?|viaduct|views?|villages?|ville|vista|walks?|wall|ways?|wells?|xing|xrd)(?=[,*•\t                　\n\f\r  ]|$)", 2);
   private static final Pattern sStateRe = Pattern.compile("(?:(ak|alaska)|(al|alabama)|(ar|arkansas)|(as|american[\t                　]+samoa)|(az|arizona)|(ca|california)|(co|colorado)|(ct|connecticut)|(dc|district[\t                　]+of[\t                　]+columbia)|(de|delaware)|(fl|florida)|(fm|federated[\t                　]+states[\t                　]+of[\t                　]+micronesia)|(ga|georgia)|(gu|guam)|(hi|hawaii)|(ia|iowa)|(id|idaho)|(il|illinois)|(in|indiana)|(ks|kansas)|(ky|kentucky)|(la|louisiana)|(ma|massachusetts)|(md|maryland)|(me|maine)|(mh|marshall[\t                　]+islands)|(mi|michigan)|(mn|minnesota)|(mo|missouri)|(mp|northern[\t                　]+mariana[\t                　]+islands)|(ms|mississippi)|(mt|montana)|(nc|north[\t                　]+carolina)|(nd|north[\t                　]+dakota)|(ne|nebraska)|(nh|new[\t                　]+hampshire)|(nj|new[\t                　]+jersey)|(nm|new[\t                　]+mexico)|(nv|nevada)|(ny|new[\t                　]+york)|(oh|ohio)|(ok|oklahoma)|(or|oregon)|(pa|pennsylvania)|(pr|puerto[\t                　]+rico)|(pw|palau)|(ri|rhode[\t                　]+island)|(sc|south[\t                　]+carolina)|(sd|south[\t                　]+dakota)|(tn|tennessee)|(tx|texas)|(ut|utah)|(va|virginia)|(vi|virgin[\t                　]+islands)|(vt|vermont)|(wa|washington)|(wi|wisconsin)|(wv|west[\t                　]+virginia)|(wy|wyoming))(?=[,*•\t                　\n\f\r  ]|$)", 2);
   private static final FindAddress.ZipRange[] sStateZipCodeRanges = new FindAddress.ZipRange[]{new FindAddress.ZipRange(99, 99, -1, -1), new FindAddress.ZipRange(35, 36, -1, -1), new FindAddress.ZipRange(71, 72, -1, -1), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(85, 86, -1, -1), new FindAddress.ZipRange(90, 96, -1, -1), new FindAddress.ZipRange(80, 81, -1, -1), new FindAddress.ZipRange(6, 6, -1, -1), new FindAddress.ZipRange(20, 20, -1, -1), new FindAddress.ZipRange(19, 19, -1, -1), new FindAddress.ZipRange(32, 34, -1, -1), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(30, 31, -1, -1), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(50, 52, -1, -1), new FindAddress.ZipRange(83, 83, -1, -1), new FindAddress.ZipRange(60, 62, -1, -1), new FindAddress.ZipRange(46, 47, -1, -1), new FindAddress.ZipRange(66, 67, 73, -1), new FindAddress.ZipRange(40, 42, -1, -1), new FindAddress.ZipRange(70, 71, -1, -1), new FindAddress.ZipRange(1, 2, -1, -1), new FindAddress.ZipRange(20, 21, -1, -1), new FindAddress.ZipRange(3, 4, -1, -1), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(48, 49, -1, -1), new FindAddress.ZipRange(55, 56, -1, -1), new FindAddress.ZipRange(63, 65, -1, -1), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(38, 39, -1, -1), new FindAddress.ZipRange(55, 56, -1, -1), new FindAddress.ZipRange(27, 28, -1, -1), new FindAddress.ZipRange(58, 58, -1, -1), new FindAddress.ZipRange(68, 69, -1, -1), new FindAddress.ZipRange(3, 4, -1, -1), new FindAddress.ZipRange(7, 8, -1, -1), new FindAddress.ZipRange(87, 88, 86, -1), new FindAddress.ZipRange(88, 89, 96, -1), new FindAddress.ZipRange(10, 14, 0, 6), new FindAddress.ZipRange(43, 45, -1, -1), new FindAddress.ZipRange(73, 74, -1, -1), new FindAddress.ZipRange(97, 97, -1, -1), new FindAddress.ZipRange(15, 19, -1, -1), new FindAddress.ZipRange(6, 6, 0, 9), new FindAddress.ZipRange(96, 96, -1, -1), new FindAddress.ZipRange(2, 2, -1, -1), new FindAddress.ZipRange(29, 29, -1, -1), new FindAddress.ZipRange(57, 57, -1, -1), new FindAddress.ZipRange(37, 38, -1, -1), new FindAddress.ZipRange(75, 79, 87, 88), new FindAddress.ZipRange(84, 84, -1, -1), new FindAddress.ZipRange(22, 24, 20, -1), new FindAddress.ZipRange(6, 9, -1, -1), new FindAddress.ZipRange(5, 5, -1, -1), new FindAddress.ZipRange(98, 99, -1, -1), new FindAddress.ZipRange(53, 54, -1, -1), new FindAddress.ZipRange(24, 26, -1, -1), new FindAddress.ZipRange(82, 83, -1, -1)};
   private static final Pattern sSuffixedNumberRe = Pattern.compile("(\\d+)(st|nd|rd|th)", 2);
   private static final Pattern sWordRe = Pattern.compile("[^,*•\t                　\n\f\r  ]+(?=[,*•\t                　\n\f\r  ]|$)", 2);
   private static final Pattern sZipCodeRe = Pattern.compile("(?:\\d{5}(?:-\\d{4})?)(?=[,*•\t                　\n\f\r  ]|$)", 2);


   private static int attemptMatch(String var0, MatchResult var1) {
      int var3 = var1.end();
      Matcher var13 = sWordRe.matcher(var0);
      String var15 = "";
      int var8 = 1;
      int var9 = 1;
      boolean var7 = true;
      int var4 = -1;
      boolean var6 = false;
      int var5 = -1;

      int var2;
      while(true) {
         var2 = var3;
         if(var3 >= var0.length()) {
            break;
         }

         if(!var13.find(var3)) {
            return -var0.length();
         }

         var2 = var3;
         if(var13.end() - var13.start() > 25) {
            return -var13.end();
         }

         while(var2 < var13.start()) {
            var3 = var8;
            if("\n\f\r  ".indexOf(var0.charAt(var2)) != -1) {
               var3 = var8 + 1;
            }

            ++var2;
            var8 = var3;
         }

         if(var8 > 5) {
            break;
         }

         ++var9;
         if(var9 > 14) {
            break;
         }

         int var10;
         boolean var11;
         int var12;
         boolean var18;
         if(matchHouseNumber(var0, var2) != null) {
            if(var7 && var8 > 1) {
               return -var2;
            }

            var18 = var7;
            var10 = var4;
            var11 = var6;
            var12 = var5;
            if(var4 == -1) {
               var18 = var7;
               var10 = var2;
               var11 = var6;
               var12 = var5;
            }
         } else if(isValidLocationName(var13.group(0))) {
            var18 = false;
            var11 = true;
            var10 = var4;
            var12 = var5;
         } else {
            if(var9 == 5 && !var6) {
               var2 = var13.end();
               break;
            }

            var3 = var5;
            if(var6) {
               var3 = var5;
               if(var9 > 4) {
                  MatchResult var14 = matchState(var0, var2);
                  var3 = var5;
                  if(var14 != null) {
                     if(var15.equals("et") && var14.group(0).equals("al")) {
                        var2 = var14.end();
                        break;
                     }

                     Matcher var16 = sWordRe.matcher(var0);
                     if(var16.find(var14.end())) {
                        var3 = var5;
                        if(isValidZipCode(var16.group(0), var14)) {
                           return var16.end();
                        }
                     } else {
                        var3 = var14.end();
                     }
                  }
               }
            }

            boolean var17 = false;
            var12 = var3;
            var11 = var6;
            var10 = var4;
            var18 = var17;
         }

         var15 = var13.group(0);
         var2 = var13.end();
         var7 = var18;
         var4 = var10;
         var6 = var11;
         var5 = var12;
         var3 = var2;
      }

      if(var5 > 0) {
         return var5;
      } else {
         if(var4 > 0) {
            var2 = var4;
         }

         return -var2;
      }
   }

   private static boolean checkHouseNumber(String var0) {
      int var1 = 0;

      int var2;
      int var3;
      for(var2 = 0; var1 < var0.length(); var2 = var3) {
         var3 = var2;
         if(Character.isDigit(var0.charAt(var1))) {
            var3 = var2 + 1;
         }

         ++var1;
      }

      if(var2 > 5) {
         return false;
      } else {
         Matcher var5 = sSuffixedNumberRe.matcher(var0);
         if(var5.find()) {
            var1 = Integer.parseInt(var5.group(1));
            if(var1 == 0) {
               return false;
            } else {
               String var4 = var5.group(2).toLowerCase(Locale.getDefault());
               switch(var1 % 10) {
               case 1:
                  if(var1 % 100 == 11) {
                     var0 = "th";
                  } else {
                     var0 = "st";
                  }

                  return var4.equals(var0);
               case 2:
                  if(var1 % 100 == 12) {
                     var0 = "th";
                  } else {
                     var0 = "nd";
                  }

                  return var4.equals(var0);
               case 3:
                  if(var1 % 100 == 13) {
                     var0 = "th";
                  } else {
                     var0 = "rd";
                  }

                  return var4.equals(var0);
               default:
                  return var4.equals("th");
               }
            }
         } else {
            return true;
         }
      }
   }

   static String findAddress(String var0) {
      Matcher var3 = sHouseNumberRe.matcher(var0);
      int var1 = 0;

      while(var3.find(var1)) {
         if(checkHouseNumber(var3.group(0))) {
            var1 = var3.start();
            int var2 = attemptMatch(var0, var3);
            if(var2 > 0) {
               return var0.substring(var1, var2);
            }

            var1 = -var2;
         } else {
            var1 = var3.end();
         }
      }

      return null;
   }

   @VisibleForTesting
   public static boolean isValidLocationName(String var0) {
      return sLocationNameRe.matcher(var0).matches();
   }

   @VisibleForTesting
   public static boolean isValidZipCode(String var0) {
      return sZipCodeRe.matcher(var0).matches();
   }

   @VisibleForTesting
   public static boolean isValidZipCode(String var0, String var1) {
      return isValidZipCode(var0, matchState(var1, 0));
   }

   private static boolean isValidZipCode(String var0, MatchResult var1) {
      boolean var5 = false;
      if(var1 == null) {
         return false;
      } else {
         int var2 = var1.groupCount();

         int var3;
         while(true) {
            var3 = var2;
            if(var2 <= 0) {
               break;
            }

            var3 = var2 - 1;
            if(var1.group(var2) != null) {
               break;
            }

            var2 = var3;
         }

         boolean var4 = var5;
         if(sZipCodeRe.matcher(var0).matches()) {
            var4 = var5;
            if(sStateZipCodeRanges[var3].matches(var0)) {
               var4 = true;
            }
         }

         return var4;
      }
   }

   @VisibleForTesting
   public static MatchResult matchHouseNumber(String var0, int var1) {
      if(var1 > 0 && ":,\"\'\t                　\n\f\r  ".indexOf(var0.charAt(var1 - 1)) == -1) {
         return null;
      } else {
         Matcher var2 = sHouseNumberRe.matcher(var0).region(var1, var0.length());
         if(var2.lookingAt()) {
            MatchResult var3 = var2.toMatchResult();
            if(checkHouseNumber(var3.group(0))) {
               return var3;
            }
         }

         return null;
      }
   }

   @VisibleForTesting
   public static MatchResult matchState(String var0, int var1) {
      Object var2 = null;
      if(var1 > 0 && ",*•\t                　\n\f\r  ".indexOf(var0.charAt(var1 - 1)) == -1) {
         return null;
      } else {
         Matcher var3 = sStateRe.matcher(var0).region(var1, var0.length());
         MatchResult var4 = (MatchResult)var2;
         if(var3.lookingAt()) {
            var4 = var3.toMatchResult();
         }

         return var4;
      }
   }

   static class ZipRange {

      int mException1;
      int mException2;
      int mHigh;
      int mLow;


      ZipRange(int var1, int var2, int var3, int var4) {
         this.mLow = var1;
         this.mHigh = var2;
         this.mException1 = var3;
         this.mException2 = var3;
      }

      boolean matches(String var1) {
         boolean var3 = false;
         int var2 = Integer.parseInt(var1.substring(0, 2));
         if(this.mLow <= var2 && var2 <= this.mHigh || var2 == this.mException1 || var2 == this.mException2) {
            var3 = true;
         }

         return var3;
      }
   }
}
