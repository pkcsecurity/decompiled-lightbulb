package com.fasterxml.jackson.core.io;


public final class NumberInput {

   static final long L_BILLION = 1000000000L;
   static final String MAX_LONG_STR = String.valueOf(Long.MAX_VALUE);
   static final String MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE).substring(1);
   public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";


   public static boolean inLongRange(String var0, boolean var1) {
      String var5;
      if(var1) {
         var5 = MIN_LONG_STR_NO_SIGN;
      } else {
         var5 = MAX_LONG_STR;
      }

      int var3 = var5.length();
      int var2 = var0.length();
      if(var2 < var3) {
         return true;
      } else if(var2 > var3) {
         return false;
      } else {
         for(var2 = 0; var2 < var3; ++var2) {
            int var4 = var0.charAt(var2) - var5.charAt(var2);
            if(var4 != 0) {
               if(var4 < 0) {
                  return true;
               }

               return false;
            }
         }

         return true;
      }
   }

   public static boolean inLongRange(char[] var0, int var1, int var2, boolean var3) {
      String var6;
      if(var3) {
         var6 = MIN_LONG_STR_NO_SIGN;
      } else {
         var6 = MAX_LONG_STR;
      }

      int var4 = var6.length();
      if(var2 < var4) {
         return true;
      } else if(var2 > var4) {
         return false;
      } else {
         for(var2 = 0; var2 < var4; ++var2) {
            int var5 = var0[var1 + var2] - var6.charAt(var2);
            if(var5 != 0) {
               if(var5 < 0) {
                  return true;
               }

               return false;
            }
         }

         return true;
      }
   }

   public static double parseAsDouble(String var0, double var1) {
      if(var0 == null) {
         return var1;
      } else {
         var0 = var0.trim();
         if(var0.length() == 0) {
            return var1;
         } else {
            try {
               double var3 = parseDouble(var0);
               return var3;
            } catch (NumberFormatException var5) {
               return var1;
            }
         }
      }
   }

   public static int parseAsInt(String var0, int var1) {
      if(var0 == null) {
         return var1;
      } else {
         String var9 = var0.trim();
         int var6 = var9.length();
         if(var6 == 0) {
            return var1;
         } else {
            byte var7 = 0;
            int var5 = var6;
            int var4 = var7;
            var0 = var9;
            if(var6 > 0) {
               char var8 = var9.charAt(0);
               if(var8 == 43) {
                  var0 = var9.substring(1);
                  var5 = var0.length();
                  var4 = var7;
               } else {
                  var5 = var6;
                  var4 = var7;
                  var0 = var9;
                  if(var8 == 45) {
                     var4 = 1;
                     var0 = var9;
                     var5 = var6;
                  }
               }
            }

            while(var4 < var5) {
               char var12 = var0.charAt(var4);
               if(var12 > 57 || var12 < 48) {
                  double var2;
                  try {
                     var2 = parseDouble(var0);
                  } catch (NumberFormatException var10) {
                     return var1;
                  }

                  return (int)var2;
               }

               ++var4;
            }

            try {
               var4 = Integer.parseInt(var0);
               return var4;
            } catch (NumberFormatException var11) {
               return var1;
            }
         }
      }
   }

   public static long parseAsLong(String var0, long var1) {
      if(var0 == null) {
         return var1;
      } else {
         String var12 = var0.trim();
         int var7 = var12.length();
         if(var7 == 0) {
            return var1;
         } else {
            byte var8 = 0;
            int var6 = var7;
            int var5 = var8;
            var0 = var12;
            if(var7 > 0) {
               char var9 = var12.charAt(0);
               if(var9 == 43) {
                  var0 = var12.substring(1);
                  var6 = var0.length();
                  var5 = var8;
               } else {
                  var6 = var7;
                  var5 = var8;
                  var0 = var12;
                  if(var9 == 45) {
                     var5 = 1;
                     var0 = var12;
                     var6 = var7;
                  }
               }
            }

            while(var5 < var6) {
               char var15 = var0.charAt(var5);
               if(var15 > 57 || var15 < 48) {
                  double var3;
                  try {
                     var3 = parseDouble(var0);
                  } catch (NumberFormatException var13) {
                     return var1;
                  }

                  return (long)var3;
               }

               ++var5;
            }

            try {
               long var10 = Long.parseLong(var0);
               return var10;
            } catch (NumberFormatException var14) {
               return var1;
            }
         }
      }
   }

   public static double parseDouble(String var0) throws NumberFormatException {
      return "2.2250738585072012e-308".equals(var0)?Double.MIN_VALUE:Double.parseDouble(var0);
   }

   public static int parseInt(String var0) {
      boolean var2 = false;
      char var1 = var0.charAt(0);
      int var5 = var0.length();
      byte var3 = 1;
      if(var1 == 45) {
         var2 = true;
      }

      if(var2) {
         if(var5 == 1 || var5 > 10) {
            return Integer.parseInt(var0);
         }

         var1 = var0.charAt(1);
         var3 = 2;
      } else if(var5 > 9) {
         return Integer.parseInt(var0);
      }

      if(var1 <= 57 && var1 >= 48) {
         int var4 = var1 - 48;
         int var7 = var4;
         int var8;
         if(var3 < var5) {
            int var6 = var3 + 1;
            var1 = var0.charAt(var3);
            if(var1 > 57 || var1 < 48) {
               return Integer.parseInt(var0);
            }

            var8 = var4 * 10 + (var1 - 48);
            var7 = var8;
            if(var6 < var5) {
               var4 = var6 + 1;
               var1 = var0.charAt(var6);
               if(var1 > 57 || var1 < 48) {
                  return Integer.parseInt(var0);
               }

               var8 = var8 * 10 + (var1 - 48);
               var7 = var8;
               if(var4 < var5) {
                  var7 = var8;

                  while(true) {
                     var8 = var4 + 1;
                     char var9 = var0.charAt(var4);
                     if(var9 > 57 || var9 < 48) {
                        return Integer.parseInt(var0);
                     }

                     var7 = var7 * 10 + (var9 - 48);
                     if(var8 >= var5) {
                        break;
                     }

                     var4 = var8;
                  }
               }
            }
         }

         var8 = var7;
         if(var2) {
            var8 = -var7;
         }

         return var8;
      } else {
         return Integer.parseInt(var0);
      }
   }

   public static int parseInt(char[] var0, int var1, int var2) {
      int var3 = var0[var1] - 48;
      int var4 = var2 + var1;
      int var5 = var1 + 1;
      var1 = var3;
      if(var5 < var4) {
         var2 = var3 * 10 + (var0[var5] - 48);
         var3 = var5 + 1;
         var1 = var2;
         if(var3 < var4) {
            var2 = var2 * 10 + (var0[var3] - 48);
            ++var3;
            var1 = var2;
            if(var3 < var4) {
               var2 = var2 * 10 + (var0[var3] - 48);
               ++var3;
               var1 = var2;
               if(var3 < var4) {
                  var2 = var2 * 10 + (var0[var3] - 48);
                  ++var3;
                  var1 = var2;
                  if(var3 < var4) {
                     var2 = var2 * 10 + (var0[var3] - 48);
                     ++var3;
                     var1 = var2;
                     if(var3 < var4) {
                        var2 = var2 * 10 + (var0[var3] - 48);
                        ++var3;
                        var1 = var2;
                        if(var3 < var4) {
                           var2 = var2 * 10 + (var0[var3] - 48);
                           ++var3;
                           var1 = var2;
                           if(var3 < var4) {
                              var1 = var2 * 10 + (var0[var3] - 48);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return var1;
   }

   public static long parseLong(String var0) {
      return var0.length() <= 9?(long)parseInt(var0):Long.parseLong(var0);
   }

   public static long parseLong(char[] var0, int var1, int var2) {
      var2 -= 9;
      return (long)parseInt(var0, var1, var2) * 1000000000L + (long)parseInt(var0, var1 + var2, 9);
   }
}
