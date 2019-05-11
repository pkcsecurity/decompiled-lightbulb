package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.SymbolTable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class JSONLexer {

   public static final char[] CA;
   public static final int END = 4;
   public static final char EOI = '\u001a';
   static final int[] IA;
   public static final int NOT_MATCH = -1;
   public static final int NOT_MATCH_NAME = -2;
   public static final int UNKNOWN = 0;
   private static boolean V6;
   public static final int VALUE = 3;
   protected static final int[] digits;
   public static final boolean[] firstIdentifierFlags;
   public static final boolean[] identifierFlags;
   private static final ThreadLocal<char[]> sbufLocal;
   protected int bp;
   public Calendar calendar;
   protected char ch;
   public boolean disableCircularReferenceDetect;
   protected int eofPos;
   protected boolean exp;
   public int features;
   protected long fieldHash;
   protected boolean hasSpecial;
   protected boolean isDouble;
   protected final int len;
   public Locale locale;
   public int matchStat;
   protected int np;
   protected int pos;
   protected char[] sbuf;
   protected int sp;
   protected String stringDefaultValue;
   protected final String text;
   public TimeZone timeZone;
   protected int token;


   static {
      int var0;
      try {
         var0 = Class.forName("android.os.Build$VERSION").getField("SDK_INT").getInt((Object)null);
      } catch (Exception var5) {
         var0 = -1;
      }

      byte var1 = 0;
      boolean var3;
      if(var0 >= 23) {
         var3 = true;
      } else {
         var3 = false;
      }

      V6 = var3;
      sbufLocal = new ThreadLocal();
      digits = new int[103];

      for(var0 = 48; var0 <= 57; ++var0) {
         digits[var0] = var0 - 48;
      }

      for(var0 = 97; var0 <= 102; ++var0) {
         digits[var0] = var0 - 97 + 10;
      }

      for(var0 = 65; var0 <= 70; ++var0) {
         digits[var0] = var0 - 65 + 10;
      }

      CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
      IA = new int[256];
      Arrays.fill(IA, -1);
      int var2 = CA.length;

      for(var0 = 0; var0 < var2; IA[CA[var0]] = var0++) {
         ;
      }

      IA[61] = 0;
      firstIdentifierFlags = new boolean[256];

      char var6;
      for(var6 = 0; var6 < firstIdentifierFlags.length; ++var6) {
         if(var6 >= 65 && var6 <= 90) {
            firstIdentifierFlags[var6] = true;
         } else if(var6 >= 97 && var6 <= 122) {
            firstIdentifierFlags[var6] = true;
         } else if(var6 == 95) {
            firstIdentifierFlags[var6] = true;
         }
      }

      identifierFlags = new boolean[256];

      for(var6 = (char)var1; var6 < identifierFlags.length; ++var6) {
         if(var6 >= 65 && var6 <= 90) {
            identifierFlags[var6] = true;
         } else if(var6 >= 97 && var6 <= 122) {
            identifierFlags[var6] = true;
         } else if(var6 == 95) {
            identifierFlags[var6] = true;
         } else if(var6 >= 48 && var6 <= 57) {
            identifierFlags[var6] = true;
         }
      }

   }

   public JSONLexer(String var1) {
      this(var1, JSON.DEFAULT_PARSER_FEATURE);
   }

   public JSONLexer(String var1, int var2) {
      this.features = JSON.DEFAULT_PARSER_FEATURE;
      boolean var5 = false;
      this.exp = false;
      this.isDouble = false;
      this.timeZone = JSON.defaultTimeZone;
      this.locale = JSON.defaultLocale;
      Object var6 = null;
      this.calendar = null;
      this.matchStat = 0;
      this.sbuf = (char[])sbufLocal.get();
      if(this.sbuf == null) {
         this.sbuf = new char[512];
      }

      this.features = var2;
      this.text = var1;
      this.len = this.text.length();
      this.bp = -1;
      int var4 = this.bp + 1;
      this.bp = var4;
      char var3;
      if(var4 >= this.len) {
         var3 = 26;
      } else {
         var3 = this.text.charAt(var4);
      }

      this.ch = var3;
      if(this.ch == '\ufeff') {
         this.next();
      }

      var1 = (String)var6;
      if((Feature.InitStringFieldAsEmpty.mask & var2) != 0) {
         var1 = "";
      }

      this.stringDefaultValue = var1;
      if((Feature.DisableCircularReferenceDetect.mask & var2) != 0) {
         var5 = true;
      }

      this.disableCircularReferenceDetect = var5;
   }

   public JSONLexer(char[] var1, int var2) {
      this(var1, var2, JSON.DEFAULT_PARSER_FEATURE);
   }

   public JSONLexer(char[] var1, int var2, int var3) {
      this(new String(var1, 0, var2), var3);
   }

   static boolean checkDate(char var0, char var1, char var2, char var3, char var4, char var5, int var6, int var7) {
      if(var0 >= 49) {
         if(var0 > 51) {
            return false;
         } else if(var1 >= 48) {
            if(var1 > 57) {
               return false;
            } else if(var2 >= 48) {
               if(var2 > 57) {
                  return false;
               } else if(var3 >= 48) {
                  if(var3 > 57) {
                     return false;
                  } else {
                     if(var4 == 48) {
                        if(var5 < 49 || var5 > 57) {
                           return false;
                        }
                     } else {
                        if(var4 != 49) {
                           return false;
                        }

                        if(var5 != 48 && var5 != 49 && var5 != 50) {
                           return false;
                        }
                     }

                     if(var6 == 48) {
                        if(var7 < 49 || var7 > 57) {
                           return false;
                        }
                     } else if(var6 != 49 && var6 != 50) {
                        if(var6 != 51) {
                           return false;
                        }

                        if(var7 != 48 && var7 != 49) {
                           return false;
                        }
                     } else {
                        if(var7 < 48) {
                           return false;
                        }

                        if(var7 > 57) {
                           return false;
                        }
                     }

                     return true;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   static boolean checkTime(char var0, char var1, char var2, char var3, char var4, char var5) {
      if(var0 == 48) {
         if(var1 < 48 || var1 > 57) {
            return false;
         }
      } else if(var0 == 49) {
         if(var1 < 48 || var1 > 57) {
            return false;
         }
      } else {
         if(var0 != 50) {
            return false;
         }

         if(var1 < 48) {
            return false;
         }

         if(var1 > 52) {
            return false;
         }
      }

      if(var2 >= 48 && var2 <= 53) {
         if(var3 < 48 || var3 > 57) {
            return false;
         }
      } else {
         if(var2 != 54) {
            return false;
         }

         if(var3 != 48) {
            return false;
         }
      }

      if(var4 >= 48 && var4 <= 53) {
         if(var5 < 48 || var5 > 57) {
            return false;
         }
      } else {
         if(var4 != 54) {
            return false;
         }

         if(var5 != 48) {
            return false;
         }
      }

      return true;
   }

   public static final byte[] decodeFast(String var0, int var1, int var2) {
      byte var7 = 0;
      if(var2 == 0) {
         return new byte[0];
      } else {
         int var4 = var1 + var2 - 1;
         int var3 = var1;

         int var5;
         while(true) {
            var5 = var4;
            if(var3 >= var4) {
               break;
            }

            var5 = var4;
            if(IA[var0.charAt(var3)] >= 0) {
               break;
            }

            ++var3;
         }

         while(var5 > 0 && IA[var0.charAt(var5)] < 0) {
            --var5;
         }

         byte var16;
         if(var0.charAt(var5) == 61) {
            if(var0.charAt(var5 - 1) == 61) {
               var16 = 2;
            } else {
               var16 = 1;
            }
         } else {
            var16 = 0;
         }

         var4 = var5 - var3 + 1;
         int var6;
         if(var2 > 76) {
            if(var0.charAt(76) == 13) {
               var2 = var4 / 78;
            } else {
               var2 = 0;
            }

            var6 = var2 << 1;
         } else {
            var6 = 0;
         }

         int var9 = ((var4 - var6) * 6 >> 3) - var16;
         byte[] var14 = new byte[var9];
         int var10 = var9 / 3;
         var4 = var3;
         var2 = 0;

         int var8;
         for(var3 = 0; var2 < var10 * 3; var2 = var8) {
            int[] var15 = IA;
            int var11 = var4 + 1;
            var8 = var15[var0.charAt(var4)];
            var15 = IA;
            var4 = var11 + 1;
            var11 = var15[var0.charAt(var11)];
            var15 = IA;
            int var12 = var4 + 1;
            int var13 = var15[var0.charAt(var4)];
            var15 = IA;
            var4 = var12 + 1;
            var11 = var8 << 18 | var11 << 12 | var13 << 6 | var15[var0.charAt(var12)];
            var12 = var2 + 1;
            var14[var2] = (byte)(var11 >> 16);
            var8 = var12 + 1;
            var14[var12] = (byte)(var11 >> 8);
            var14[var8] = (byte)var11;
            var2 = var3;
            if(var6 > 0) {
               ++var3;
               var2 = var3;
               if(var3 == 19) {
                  var4 += 2;
                  var2 = 0;
               }
            }

            ++var8;
            var3 = var2;
         }

         if(var2 < var9) {
            var6 = 0;

            for(var3 = var7; var4 <= var5 - var16; ++var4) {
               var3 |= IA[var0.charAt(var4)] << 18 - var6 * 6;
               ++var6;
            }

            for(var1 = 16; var2 < var9; ++var2) {
               var14[var2] = (byte)(var3 >> var1);
               var1 -= 8;
            }
         }

         return var14;
      }
   }

   private int matchFieldHash(long var1) {
      char var4 = this.ch;
      int var3 = this.bp;

      int var9;
      for(var3 = 1; var4 != 34 && var4 != 39; ++var3) {
         if(var4 != 32 && var4 != 10 && var4 != 13 && var4 != 9 && var4 != 12 && var4 != 8) {
            this.fieldHash = 0L;
            this.matchStat = -2;
            return 0;
         }

         var9 = this.bp + var3;
         if(var9 >= this.len) {
            var4 = 26;
         } else {
            var4 = this.text.charAt(var9);
         }
      }

      long var7 = -3750763034362895579L;
      int var5 = this.bp + var3;

      int var6;
      while(true) {
         var6 = var3;
         if(var5 >= this.len) {
            break;
         }

         char var11 = this.text.charAt(var5);
         if(var11 == var4) {
            var6 = var3 + (var5 - this.bp - var3);
            break;
         }

         var7 = (var7 ^ (long)var11) * 1099511628211L;
         ++var5;
      }

      if(var7 != var1) {
         this.fieldHash = var7;
         this.matchStat = -2;
         return 0;
      } else {
         var3 = this.bp;
         var9 = var6 + 1;
         var3 += var9;
         char var10;
         if(var3 >= this.len) {
            var10 = 26;
         } else {
            var10 = this.text.charAt(var3);
         }

         for(; var10 != 58; ++var9) {
            if(var10 > 32 || var10 != 32 && var10 != 10 && var10 != 13 && var10 != 9 && var10 != 12 && var10 != 8) {
               throw new JSONException("match feild error expect \':\'");
            }

            var3 = this.bp + var9;
            if(var3 >= this.len) {
               var10 = 26;
            } else {
               var10 = this.text.charAt(var3);
            }
         }

         return var9 + 1;
      }
   }

   private static String readString(char[] var0, int var1) {
      char[] var9 = new char[var1];
      int var5 = 0;

      int var6;
      for(var6 = 0; var5 < var1; ++var5) {
         char var2 = var0[var5];
         if(var2 != 92) {
            var9[var6] = var2;
            ++var6;
         } else {
            int var7 = var5 + 1;
            char var11 = var0[var7];
            switch(var11) {
            case 47:
               var5 = var6 + 1;
               var9[var6] = 47;
               var6 = var7;
               break;
            case 48:
               var5 = var6 + 1;
               var9[var6] = 0;
               var6 = var7;
               break;
            case 49:
               var5 = var6 + 1;
               var9[var6] = 1;
               var6 = var7;
               break;
            case 50:
               var5 = var6 + 1;
               var9[var6] = 2;
               var6 = var7;
               break;
            case 51:
               var5 = var6 + 1;
               var9[var6] = 3;
               var6 = var7;
               break;
            case 52:
               var5 = var6 + 1;
               var9[var6] = 4;
               var6 = var7;
               break;
            case 53:
               var5 = var6 + 1;
               var9[var6] = 5;
               var6 = var7;
               break;
            case 54:
               var5 = var6 + 1;
               var9[var6] = 6;
               var6 = var7;
               break;
            case 55:
               var5 = var6 + 1;
               var9[var6] = 7;
               var6 = var7;
               break;
            default:
               switch(var11) {
               case 116:
                  var5 = var6 + 1;
                  var9[var6] = 9;
                  var6 = var7;
                  break;
               case 117:
                  var5 = var6 + 1;
                  ++var7;
                  var2 = var0[var7];
                  ++var7;
                  char var3 = var0[var7];
                  ++var7;
                  char var4 = var0[var7];
                  ++var7;
                  var9[var6] = (char)Integer.parseInt(new String(new char[]{var2, var3, var4, var0[var7]}), 16);
                  var6 = var7;
                  break;
               case 118:
                  var5 = var6 + 1;
                  var9[var6] = 11;
                  var6 = var7;
                  break;
               default:
                  switch(var11) {
                  case 34:
                     var5 = var6 + 1;
                     var9[var6] = 34;
                     var6 = var7;
                     break;
                  case 39:
                     var5 = var6 + 1;
                     var9[var6] = 39;
                     var6 = var7;
                     break;
                  case 70:
                  case 102:
                     var5 = var6 + 1;
                     var9[var6] = 12;
                     var6 = var7;
                     break;
                  case 92:
                     var5 = var6 + 1;
                     var9[var6] = 92;
                     var6 = var7;
                     break;
                  case 98:
                     var5 = var6 + 1;
                     var9[var6] = 8;
                     var6 = var7;
                     break;
                  case 110:
                     var5 = var6 + 1;
                     var9[var6] = 10;
                     var6 = var7;
                     break;
                  case 114:
                     var5 = var6 + 1;
                     var9[var6] = 13;
                     var6 = var7;
                     break;
                  case 120:
                     var5 = var6 + 1;
                     int[] var10 = digits;
                     ++var7;
                     int var8 = var10[var0[var7]];
                     var10 = digits;
                     ++var7;
                     var9[var6] = (char)(var8 * 16 + var10[var0[var7]]);
                     var6 = var7;
                     break;
                  default:
                     throw new JSONException("unclosed.str.lit");
                  }
               }
            }

            var7 = var5;
            var5 = var6;
            var6 = var7;
         }
      }

      return new String(var9, 0, var6);
   }

   private void scanIdent() {
      this.np = this.bp - 1;
      this.hasSpecial = false;

      do {
         ++this.sp;
         this.next();
      } while(Character.isLetterOrDigit(this.ch));

      String var1 = this.stringVal();
      if(var1.equals("null")) {
         this.token = 8;
      } else if(var1.equals("true")) {
         this.token = 6;
      } else if(var1.equals("false")) {
         this.token = 7;
      } else if(var1.equals("new")) {
         this.token = 9;
      } else if(var1.equals("undefined")) {
         this.token = 23;
      } else if(var1.equals("Set")) {
         this.token = 21;
      } else if(var1.equals("TreeSet")) {
         this.token = 22;
      } else {
         this.token = 18;
      }
   }

   private void setCalendar(char var1, char var2, char var3, char var4, char var5, char var6, char var7, char var8) {
      this.calendar = Calendar.getInstance(this.timeZone, this.locale);
      this.calendar.set(1, (var1 - 48) * 1000 + (var2 - 48) * 100 + (var3 - 48) * 10 + (var4 - 48));
      this.calendar.set(2, (var5 - 48) * 10 + (var6 - 48) - 1);
      this.calendar.set(5, (var7 - 48) * 10 + (var8 - 48));
   }

   private final String subString(int var1, int var2) {
      if(var2 < this.sbuf.length) {
         this.text.getChars(var1, var1 + var2, this.sbuf, 0);
         return new String(this.sbuf, 0, var2);
      } else {
         char[] var3 = new char[var2];
         this.text.getChars(var1, var2 + var1, var3, 0);
         return new String(var3);
      }
   }

   public byte[] bytesValue() {
      return decodeFast(this.text, this.np + 1, this.sp);
   }

   protected char charAt(int var1) {
      return var1 >= this.len?'\u001a':this.text.charAt(var1);
   }

   public void close() {
      if(this.sbuf.length <= 8196) {
         sbufLocal.set(this.sbuf);
      }

      this.sbuf = null;
   }

   public final void config(Feature var1, boolean var2) {
      if(var2) {
         this.features |= var1.mask;
      } else {
         this.features &= ~var1.mask;
      }

      if(var1 == Feature.InitStringFieldAsEmpty) {
         String var3;
         if(var2) {
            var3 = "";
         } else {
            var3 = null;
         }

         this.stringDefaultValue = var3;
      }

      if((this.features & Feature.DisableCircularReferenceDetect.mask) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.disableCircularReferenceDetect = var2;
   }

   public final Number decimalValue(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public final BigDecimal decimalValue() {
      int var1;
      int var2;
      label21: {
         var1 = this.np;
         var2 = this.sp;
         char var3 = this.text.charAt(var1 + var2 - 1);
         var2 = this.sp;
         if(var3 != 76 && var3 != 83 && var3 != 66 && var3 != 70) {
            var1 = var2;
            if(var3 != 68) {
               break label21;
            }
         }

         var1 = var2 - 1;
      }

      var2 = this.np;
      if(var1 < this.sbuf.length) {
         this.text.getChars(var2, var2 + var1, this.sbuf, 0);
         return new BigDecimal(this.sbuf, 0, var1);
      } else {
         char[] var4 = new char[var1];
         this.text.getChars(var2, var1 + var2, var4, 0);
         return new BigDecimal(var4);
      }
   }

   public String info() {
      StringBuilder var2 = new StringBuilder();
      var2.append("pos ");
      var2.append(this.bp);
      var2.append(", json : ");
      String var1;
      if(this.len < 65536) {
         var1 = this.text;
      } else {
         var1 = this.text.substring(0, 65536);
      }

      var2.append(var1);
      return var2.toString();
   }

   public final int intValue() {
      int var3 = this.np;
      int var6 = this.np + this.sp;
      char var1;
      if(this.np >= this.len) {
         var1 = 26;
      } else {
         var1 = this.text.charAt(this.np);
      }

      int var2 = 0;
      boolean var4;
      int var5;
      if(var1 == 45) {
         var5 = Integer.MIN_VALUE;
         ++var3;
         var4 = true;
      } else {
         var5 = -2147483647;
         var4 = false;
      }

      int var7 = var3;
      if(var3 < var6) {
         if(var3 >= this.len) {
            var1 = 26;
         } else {
            var1 = this.text.charAt(var3);
         }

         var2 = -(var1 - 48);
         var7 = var3 + 1;
      }

      while(true) {
         var3 = var7;
         if(var7 >= var6) {
            break;
         }

         var3 = var7 + 1;
         if(var7 >= this.len) {
            var1 = 26;
         } else {
            var1 = this.text.charAt(var7);
         }

         if(var1 == 76 || var1 == 83 || var1 == 66) {
            break;
         }

         var7 = var1 - 48;
         if(var2 < -214748364) {
            throw new NumberFormatException(this.numberString());
         }

         var2 *= 10;
         if(var2 < var5 + var7) {
            throw new NumberFormatException(this.numberString());
         }

         var2 -= var7;
         var7 = var3;
      }

      if(var4) {
         if(var3 > this.np + 1) {
            return var2;
         } else {
            throw new NumberFormatException(this.numberString());
         }
      } else {
         return -var2;
      }
   }

   public final Number integerValue() throws NumberFormatException {
      int var5 = this.np;
      int var2 = this.np + this.sp;
      int var1 = var2 - 1;
      char var12;
      if(var1 >= this.len) {
         var12 = 26;
      } else {
         var12 = this.text.charAt(var1);
      }

      byte var13;
      if(var12 != 66) {
         if(var12 != 76) {
            if(var12 != 83) {
               byte var3 = 32;
               var1 = var2;
               var13 = var3;
            } else {
               var1 = var2 - 1;
               var13 = 83;
            }
         } else {
            var1 = var2 - 1;
            var13 = 76;
         }
      } else {
         var1 = var2 - 1;
         var13 = 66;
      }

      char var14;
      if(this.np >= this.len) {
         var14 = 26;
      } else {
         var14 = this.text.charAt(this.np);
      }

      boolean var4;
      long var8;
      int var15;
      if(var14 == 45) {
         var8 = Long.MIN_VALUE;
         var15 = var5 + 1;
         var4 = true;
      } else {
         var8 = -9223372036854775807L;
         var4 = false;
         var15 = var5;
      }

      long var6;
      char var16;
      if(var15 < var1) {
         if(var15 >= this.len) {
            var16 = 26;
         } else {
            var16 = this.text.charAt(var15);
         }

         var6 = (long)(-(var16 - 48));
         ++var15;
      } else {
         var6 = 0L;
      }

      while(var15 < var1) {
         if(var15 >= this.len) {
            var16 = 26;
         } else {
            var16 = this.text.charAt(var15);
         }

         if(var6 < -922337203685477580L) {
            return new BigInteger(this.numberString());
         }

         var6 *= 10L;
         long var10 = (long)(var16 - 48);
         if(var6 < var8 + var10) {
            return new BigInteger(this.numberString());
         }

         ++var15;
         var6 -= var10;
      }

      if(var4) {
         if(var15 > this.np + 1) {
            if(var6 >= -2147483648L && var13 != 76) {
               if(var13 == 83) {
                  return Short.valueOf((short)((int)var6));
               } else if(var13 == 66) {
                  return Byte.valueOf((byte)((int)var6));
               } else {
                  return Integer.valueOf((int)var6);
               }
            } else {
               return Long.valueOf(var6);
            }
         } else {
            throw new NumberFormatException(this.numberString());
         }
      } else {
         var6 = -var6;
         if(var6 <= 2147483647L && var13 != 76) {
            if(var13 == 83) {
               return Short.valueOf((short)((int)var6));
            } else if(var13 == 66) {
               return Byte.valueOf((byte)((int)var6));
            } else {
               return Integer.valueOf((int)var6);
            }
         } else {
            return Long.valueOf(var6);
         }
      }
   }

   public final boolean isBlankInput() {
      int var1 = 0;

      while(true) {
         char var4 = this.charAt(var1);
         boolean var3 = true;
         if(var4 == 26) {
            return true;
         }

         boolean var2;
         label36: {
            if(var4 <= 32) {
               var2 = var3;
               if(var4 == 32) {
                  break label36;
               }

               var2 = var3;
               if(var4 == 10) {
                  break label36;
               }

               var2 = var3;
               if(var4 == 13) {
                  break label36;
               }

               var2 = var3;
               if(var4 == 9) {
                  break label36;
               }

               var2 = var3;
               if(var4 == 12) {
                  break label36;
               }

               if(var4 == 8) {
                  var2 = var3;
                  break label36;
               }
            }

            var2 = false;
         }

         if(!var2) {
            return false;
         }

         ++var1;
      }
   }

   public final boolean isEnabled(Feature var1) {
      int var2 = this.features;
      return (var1.mask & var2) != 0;
   }

   public final long longValue() throws NumberFormatException {
      int var1 = this.np;
      int var4 = this.np + this.sp;
      boolean var2;
      long var7;
      if(this.charAt(this.np) == 45) {
         var7 = Long.MIN_VALUE;
         ++var1;
         var2 = true;
      } else {
         var7 = -9223372036854775807L;
         var2 = false;
      }

      long var5;
      if(var1 < var4) {
         var5 = (long)(-(this.charAt(var1) - 48));
         ++var1;
      } else {
         var5 = 0L;
      }

      int var3;
      while(true) {
         var3 = var1;
         if(var1 >= var4) {
            break;
         }

         var3 = var1 + 1;
         char var11;
         if(var1 >= this.len) {
            var11 = 26;
         } else {
            var11 = this.text.charAt(var1);
         }

         if(var11 == 76 || var11 == 83 || var11 == 66) {
            break;
         }

         if(var5 < -922337203685477580L) {
            throw new NumberFormatException(this.numberString());
         }

         var5 *= 10L;
         long var9 = (long)(var11 - 48);
         if(var5 < var7 + var9) {
            throw new NumberFormatException(this.numberString());
         }

         var1 = var3;
         var5 -= var9;
      }

      if(var2) {
         if(var3 > this.np + 1) {
            return var5;
         } else {
            throw new NumberFormatException(this.numberString());
         }
      } else {
         return -var5;
      }
   }

   public boolean matchField(long var1) {
      char var5 = this.ch;
      int var8 = this.bp + 1;
      int var4 = 1;

      while(true) {
         int var12;
         if(var5 != 34 && var5 != 39) {
            if(var5 <= 32 && (var5 == 32 || var5 == 10 || var5 == 13 || var5 == 9 || var5 == 12 || var5 == 8)) {
               var12 = this.bp + var4;
               if(var12 >= this.len) {
                  var5 = 26;
               } else {
                  var5 = this.text.charAt(var12);
               }

               ++var4;
               continue;
            }

            this.fieldHash = 0L;
            this.matchStat = -2;
            return false;
         }

         int var7 = var8;
         long var9 = -3750763034362895579L;

         int var6;
         while(true) {
            var6 = var4;
            if(var7 >= this.len) {
               break;
            }

            char var13 = this.text.charAt(var7);
            if(var13 == var5) {
               var6 = var4 + var7 - var8 + 1;
               break;
            }

            var9 = (var9 ^ (long)var13) * 1099511628211L;
            ++var7;
         }

         if(var9 != var1) {
            this.matchStat = -2;
            this.fieldHash = var9;
            return false;
         }

         var4 = this.bp;
         var12 = var6 + 1;
         var4 += var6;
         char var11;
         if(var4 >= this.len) {
            var11 = 26;
         } else {
            var11 = this.text.charAt(var4);
         }

         for(; var11 != 58; ++var12) {
            if(var11 > 32 || var11 != 32 && var11 != 10 && var11 != 13 && var11 != 9 && var11 != 12 && var11 != 8) {
               throw new JSONException("match feild error expect \':\'");
            }

            var4 = this.bp + var12;
            if(var4 >= this.len) {
               var11 = 26;
            } else {
               var11 = this.text.charAt(var4);
            }
         }

         var12 += this.bp;
         if(var12 >= this.len) {
            var11 = 26;
         } else {
            var11 = this.text.charAt(var12);
         }

         char var3;
         if(var11 == 123) {
            this.bp = var12 + 1;
            if(this.bp >= this.len) {
               var3 = 26;
            } else {
               var3 = this.text.charAt(this.bp);
            }

            this.ch = var3;
            this.token = 12;
            return true;
         }

         if(var11 == 91) {
            this.bp = var12 + 1;
            if(this.bp >= this.len) {
               var3 = 26;
            } else {
               var3 = this.text.charAt(this.bp);
            }

            this.ch = var3;
            this.token = 14;
            return true;
         }

         this.bp = var12;
         if(this.bp >= this.len) {
            var3 = 26;
         } else {
            var3 = this.text.charAt(this.bp);
         }

         this.ch = var3;
         this.nextToken();
         return true;
      }
   }

   public char next() {
      int var2 = this.bp + 1;
      this.bp = var2;
      char var1;
      if(var2 >= this.len) {
         var1 = 26;
      } else {
         var1 = this.text.charAt(var2);
      }

      this.ch = var1;
      return var1;
   }

   public final void nextIdent() {
      while(true) {
         boolean var1;
         if(this.ch <= 32 && (this.ch == 32 || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 12 || this.ch == 8)) {
            var1 = true;
         } else {
            var1 = false;
         }

         if(!var1) {
            if(this.ch != 95 && !Character.isLetter(this.ch)) {
               this.nextToken();
               return;
            }

            this.scanIdent();
            return;
         }

         this.next();
      }
   }

   public final void nextToken() {
      byte var3 = 0;
      this.sp = 0;

      while(true) {
         while(true) {
            this.pos = this.bp;
            if(this.ch != 47) {
               if(this.ch == 34) {
                  this.scanString();
                  return;
               }

               if(this.ch >= 48 && this.ch <= 57 || this.ch == 45) {
                  this.scanNumber();
                  return;
               }

               if(this.ch == 44) {
                  this.next();
                  this.token = 16;
                  return;
               }

               char var2 = this.ch;
               char var1 = 26;
               int var4;
               switch(var2) {
               case 8:
               case 9:
               case 10:
               case 12:
               case 13:
               case 32:
                  this.next();
                  break;
               case 39:
                  this.scanString();
                  return;
               case 40:
                  this.next();
                  this.token = 10;
                  return;
               case 41:
                  this.next();
                  this.token = 11;
                  return;
               case 58:
                  this.next();
                  this.token = 17;
                  return;
               case 83:
               case 84:
               case 117:
                  this.scanIdent();
                  return;
               case 91:
                  var4 = this.bp + 1;
                  this.bp = var4;
                  if(var4 < this.len) {
                     var1 = this.text.charAt(var4);
                  }

                  this.ch = var1;
                  this.token = 14;
                  return;
               case 93:
                  this.next();
                  this.token = 15;
                  return;
               case 102:
                  if(this.text.startsWith("false", this.bp)) {
                     this.bp += 5;
                     this.ch = this.charAt(this.bp);
                     if(this.ch == 32 || this.ch == 44 || this.ch == 125 || this.ch == 93 || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 26 || this.ch == 12 || this.ch == 8 || this.ch == 58) {
                        this.token = 7;
                        return;
                     }
                  }

                  throw new JSONException("scan false error");
               case 110:
                  byte var5;
                  if(this.text.startsWith("null", this.bp)) {
                     this.bp += 4;
                     var5 = 8;
                  } else {
                     var5 = var3;
                     if(this.text.startsWith("new", this.bp)) {
                        this.bp += 3;
                        var5 = 9;
                     }
                  }

                  if(var5 != 0) {
                     this.ch = this.charAt(this.bp);
                     if(this.ch == 32 || this.ch == 44 || this.ch == 125 || this.ch == 93 || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 26 || this.ch == 12 || this.ch == 8) {
                        this.token = var5;
                        return;
                     }
                  }

                  throw new JSONException("scan null/new error");
               case 116:
                  if(this.text.startsWith("true", this.bp)) {
                     this.bp += 4;
                     this.ch = this.charAt(this.bp);
                     if(this.ch == 32 || this.ch == 44 || this.ch == 125 || this.ch == 93 || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 26 || this.ch == 12 || this.ch == 8 || this.ch == 58) {
                        this.token = 6;
                        return;
                     }
                  }

                  throw new JSONException("scan true error");
               case 123:
                  var4 = this.bp + 1;
                  this.bp = var4;
                  if(var4 < this.len) {
                     var1 = this.text.charAt(var4);
                  }

                  this.ch = var1;
                  this.token = 12;
                  return;
               case 125:
                  var4 = this.bp + 1;
                  this.bp = var4;
                  if(var4 < this.len) {
                     var1 = this.text.charAt(var4);
                  }

                  this.ch = var1;
                  this.token = 13;
                  return;
               default:
                  boolean var6;
                  if(this.bp != this.len && (this.ch != 26 || this.bp + 1 != this.len)) {
                     var6 = false;
                  } else {
                     var6 = true;
                  }

                  if(var6) {
                     if(this.token == 20) {
                        throw new JSONException("EOF error");
                     }

                     this.token = 20;
                     var4 = this.eofPos;
                     this.bp = var4;
                     this.pos = var4;
                     return;
                  }

                  if(this.ch > 31 && this.ch != 127) {
                     this.token = 1;
                     this.next();
                     return;
                  }

                  this.next();
               }
            } else {
               this.skipComment();
            }
         }
      }
   }

   public final void nextToken(int var1) {
      this.sp = 0;

      while(true) {
         if(var1 != 2) {
            char var2 = 26;
            if(var1 != 4) {
               if(var1 != 12) {
                  label139: {
                     if(var1 == 18) {
                        this.nextIdent();
                        return;
                     }

                     if(var1 != 20) {
                        switch(var1) {
                        case 14:
                           if(this.ch == 91) {
                              this.token = 14;
                              this.next();
                              return;
                           }

                           if(this.ch == 123) {
                              this.token = 12;
                              this.next();
                              return;
                           }
                           break label139;
                        case 15:
                           if(this.ch == 93) {
                              this.token = 15;
                              this.next();
                              return;
                           }
                           break;
                        case 16:
                           if(this.ch == 44) {
                              this.token = 16;
                              var1 = this.bp + 1;
                              this.bp = var1;
                              if(var1 < this.len) {
                                 var2 = this.text.charAt(var1);
                              }

                              this.ch = var2;
                              return;
                           }

                           if(this.ch == 125) {
                              this.token = 13;
                              var1 = this.bp + 1;
                              this.bp = var1;
                              if(var1 < this.len) {
                                 var2 = this.text.charAt(var1);
                              }

                              this.ch = var2;
                              return;
                           }

                           if(this.ch == 93) {
                              this.token = 15;
                              var1 = this.bp + 1;
                              this.bp = var1;
                              if(var1 < this.len) {
                                 var2 = this.text.charAt(var1);
                              }

                              this.ch = var2;
                              return;
                           }

                           if(this.ch == 26) {
                              this.token = 20;
                              return;
                           }
                        default:
                           break label139;
                        }
                     }

                     if(this.ch == 26) {
                        this.token = 20;
                        return;
                     }
                  }
               } else {
                  if(this.ch == 123) {
                     this.token = 12;
                     var1 = this.bp + 1;
                     this.bp = var1;
                     if(var1 < this.len) {
                        var2 = this.text.charAt(var1);
                     }

                     this.ch = var2;
                     return;
                  }

                  if(this.ch == 91) {
                     this.token = 14;
                     var1 = this.bp + 1;
                     this.bp = var1;
                     if(var1 < this.len) {
                        var2 = this.text.charAt(var1);
                     }

                     this.ch = var2;
                     return;
                  }
               }
            } else {
               if(this.ch == 34) {
                  this.pos = this.bp;
                  this.scanString();
                  return;
               }

               if(this.ch >= 48 && this.ch <= 57) {
                  this.pos = this.bp;
                  this.scanNumber();
                  return;
               }

               if(this.ch == 123) {
                  this.token = 12;
                  var1 = this.bp + 1;
                  this.bp = var1;
                  if(var1 < this.len) {
                     var2 = this.text.charAt(var1);
                  }

                  this.ch = var2;
                  return;
               }
            }
         } else {
            if(this.ch >= 48 && this.ch <= 57) {
               this.pos = this.bp;
               this.scanNumber();
               return;
            }

            if(this.ch == 34) {
               this.pos = this.bp;
               this.scanString();
               return;
            }

            if(this.ch == 91) {
               this.token = 14;
               this.next();
               return;
            }

            if(this.ch == 123) {
               this.token = 12;
               this.next();
               return;
            }
         }

         if(this.ch != 32 && this.ch != 10 && this.ch != 13 && this.ch != 9 && this.ch != 12 && this.ch != 8) {
            this.nextToken();
            return;
         }

         this.next();
      }
   }

   public final void nextTokenWithChar(char var1) {
      this.sp = 0;

      while(this.ch != var1) {
         if(this.ch != 32 && this.ch != 10 && this.ch != 13 && this.ch != 9 && this.ch != 12 && this.ch != 8) {
            StringBuilder var3 = new StringBuilder();
            var3.append("not match ");
            var3.append(var1);
            var3.append(" - ");
            var3.append(this.ch);
            throw new JSONException(var3.toString());
         }

         this.next();
      }

      int var2 = this.bp + 1;
      this.bp = var2;
      if(var2 >= this.len) {
         var1 = 26;
      } else {
         var1 = this.text.charAt(var2);
      }

      this.ch = var1;
      this.nextToken();
   }

   public final String numberString() {
      int var1 = this.np;
      int var2 = this.sp;
      char var3 = this.text.charAt(var1 + var2 - 1);
      var2 = this.sp;
      if(var3 != 76 && var3 != 83 && var3 != 66 && var3 != 70) {
         var1 = var2;
         if(var3 != 68) {
            return this.subString(this.np, var1);
         }
      }

      var1 = var2 - 1;
      return this.subString(this.np, var1);
   }

   public boolean scanBoolean() {
      boolean var3 = this.text.startsWith("false", this.bp);
      boolean var2 = false;
      byte var1 = 1;
      if(var3) {
         var1 = 5;
      } else if(this.text.startsWith("true", this.bp)) {
         var2 = true;
         var1 = 4;
      } else if(this.ch == 49) {
         var2 = true;
      } else if(this.ch != 48) {
         this.matchStat = -1;
         return false;
      }

      this.bp += var1;
      this.ch = this.charAt(this.bp);
      return var2;
   }

   public boolean scanFieldBoolean(long var1) {
      this.matchStat = 0;
      int var4 = this.matchFieldHash(var1);
      if(var4 == 0) {
         return false;
      } else {
         boolean var7;
         label115: {
            if(this.text.startsWith("false", this.bp + var4)) {
               var4 += 5;
            } else {
               label113: {
                  if(this.text.startsWith("true", this.bp + var4)) {
                     var4 += 4;
                  } else {
                     if(this.text.startsWith("\"false\"", this.bp + var4)) {
                        var4 += 7;
                        break label113;
                     }

                     if(this.text.startsWith("\"true\"", this.bp + var4)) {
                        var4 += 6;
                     } else if(this.text.charAt(this.bp + var4) == 49) {
                        ++var4;
                     } else {
                        if(this.text.charAt(this.bp + var4) == 48) {
                           ++var4;
                           break label113;
                        }

                        if(!this.text.startsWith("\"1\"", this.bp + var4)) {
                           if(!this.text.startsWith("\"0\"", this.bp + var4)) {
                              this.matchStat = -1;
                              return false;
                           }

                           var4 += 3;
                           break label113;
                        }

                        var4 += 3;
                     }
                  }

                  var7 = true;
                  break label115;
               }
            }

            var7 = false;
         }

         int var6 = this.bp;
         int var5 = var4 + 1;
         var4 += var6;
         var6 = this.len;
         char var3 = 26;
         char var9;
         if(var4 >= var6) {
            var9 = 26;
         } else {
            var9 = this.text.charAt(var4);
         }

         for(; var9 != 44; ++var5) {
            if(var9 == 125 || var9 != 32 && var9 != 10 && var9 != 13 && var9 != 9 && var9 != 12 && var9 != 8) {
               if(var9 == 125) {
                  var6 = this.bp;
                  var4 = var5 + 1;
                  char var8 = this.charAt(var6 + var5);
                  if(var8 == 44) {
                     this.token = 16;
                     this.bp += var4 - 1;
                     var4 = this.bp + 1;
                     this.bp = var4;
                     if(var4 < this.len) {
                        var3 = this.text.charAt(var4);
                     }

                     this.ch = var3;
                  } else if(var8 == 93) {
                     this.token = 15;
                     this.bp += var4 - 1;
                     var4 = this.bp + 1;
                     this.bp = var4;
                     if(var4 < this.len) {
                        var3 = this.text.charAt(var4);
                     }

                     this.ch = var3;
                  } else if(var8 == 125) {
                     this.token = 13;
                     this.bp += var4 - 1;
                     var4 = this.bp + 1;
                     this.bp = var4;
                     if(var4 < this.len) {
                        var3 = this.text.charAt(var4);
                     }

                     this.ch = var3;
                  } else {
                     if(var8 != 26) {
                        this.matchStat = -1;
                        return false;
                     }

                     this.token = 20;
                     this.bp += var4 - 1;
                     this.ch = 26;
                  }

                  this.matchStat = 4;
                  return var7;
               } else {
                  this.matchStat = -1;
                  return false;
               }
            }

            var4 = this.bp + var5;
            if(var4 >= this.len) {
               var9 = 26;
            } else {
               var9 = this.text.charAt(var4);
            }
         }

         this.bp += var5 - 1;
         var4 = this.bp + 1;
         this.bp = var4;
         if(var4 < this.len) {
            var3 = this.text.charAt(var4);
         }

         this.ch = var3;
         this.matchStat = 3;
         this.token = 16;
         return var7;
      }
   }

   public Date scanFieldDate(long var1) {
      this.matchStat = 0;
      int var4 = this.matchFieldHash(var1);
      if(var4 == 0) {
         return null;
      } else {
         int var6 = this.bp;
         char var5 = this.ch;
         int var7 = this.bp;
         int var13 = var4 + 1;
         var4 += var7;
         var7 = this.len;
         char var3 = 26;
         char var12;
         if(var4 >= var7) {
            var12 = 26;
         } else {
            var12 = this.text.charAt(var4);
         }

         Date var11;
         if(var12 == 34) {
            var4 = this.bp + var13;
            int var8 = this.bp;
            var7 = var13 + 1;
            var13 += var8;
            if(var13 < this.len) {
               this.text.charAt(var13);
            }

            var13 = this.text.indexOf(34, this.bp + var7);
            if(var13 == -1) {
               throw new JSONException("unclosed str");
            }

            var13 -= var4;
            this.bp = var4;
            if(!this.scanISO8601DateIfMatch(false, var13)) {
               this.bp = var6;
               this.matchStat = -1;
               return null;
            }

            var11 = this.calendar.getTime();
            var13 += var7;
            var4 = var13 + 1;
            var5 = this.charAt(var13 + var6);
            this.bp = var6;
         } else {
            if(var12 < 48 || var12 > 57) {
               this.matchStat = -1;
               return null;
            }

            var1 = (long)(var12 - 48);
            var4 = var13;

            while(true) {
               var6 = this.bp;
               var13 = var4 + 1;
               var4 += var6;
               if(var4 >= this.len) {
                  var12 = 26;
               } else {
                  var12 = this.text.charAt(var4);
               }

               if(var12 < 48 || var12 > 57) {
                  if(var12 == 46) {
                     this.matchStat = -1;
                     return null;
                  }

                  if(var12 == 34) {
                     var4 = this.bp;
                     var6 = var13 + 1;
                     var4 += var13;
                     if(var4 >= this.len) {
                        var12 = 26;
                     } else {
                        var12 = this.text.charAt(var4);
                     }

                     var5 = var12;
                     var4 = var6;
                  } else {
                     var6 = var13;
                     var5 = var12;
                     var4 = var6;
                  }

                  if(var1 < 0L) {
                     this.matchStat = -1;
                     return null;
                  }

                  var11 = new Date(var1);
                  break;
               }

               long var9 = (long)(var12 - 48);
               var4 = var13;
               var1 = var1 * 10L + var9;
            }
         }

         if(var5 == 44) {
            this.bp += var4 - 1;
            var4 = this.bp + 1;
            this.bp = var4;
            if(var4 < this.len) {
               var3 = this.text.charAt(var4);
            }

            this.ch = var3;
            this.matchStat = 3;
            this.token = 16;
            return var11;
         } else if(var5 == 125) {
            var6 = this.bp;
            var13 = var4 + 1;
            var12 = this.charAt(var6 + var4);
            if(var12 == 44) {
               this.token = 16;
               this.bp += var13 - 1;
               var4 = this.bp + 1;
               this.bp = var4;
               if(var4 < this.len) {
                  var3 = this.text.charAt(var4);
               }

               this.ch = var3;
            } else if(var12 == 93) {
               this.token = 15;
               this.bp += var13 - 1;
               var4 = this.bp + 1;
               this.bp = var4;
               if(var4 < this.len) {
                  var3 = this.text.charAt(var4);
               }

               this.ch = var3;
            } else if(var12 == 125) {
               this.token = 13;
               this.bp += var13 - 1;
               var4 = this.bp + 1;
               this.bp = var4;
               if(var4 < this.len) {
                  var3 = this.text.charAt(var4);
               }

               this.ch = var3;
            } else {
               if(var12 != 26) {
                  this.matchStat = -1;
                  return null;
               }

               this.token = 20;
               this.bp += var13 - 1;
               this.ch = 26;
            }

            this.matchStat = 4;
            return var11;
         } else {
            this.matchStat = -1;
            return null;
         }
      }
   }

   public final double scanFieldDouble(long var1) {
      this.matchStat = 0;
      int var7 = this.matchFieldHash(var1);
      if(var7 == 0) {
         return 0.0D;
      } else {
         int var8 = this.bp;
         int var10 = var7 + 1;
         char var9 = this.charAt(var8 + var7);
         int var19 = this.bp + var10 - 1;
         boolean var20;
         if(var9 == 45) {
            var20 = true;
         } else {
            var20 = false;
         }

         var8 = var10;
         if(var20) {
            var9 = this.charAt(this.bp + var10);
            var8 = var10 + 1;
         }

         if(var9 >= 48 && var9 <= 57) {
            var10 = var9 - 48;
            int var21 = var8;
            var8 = var10;

            while(true) {
               var10 = this.bp;
               int var11 = var21 + 1;
               char var12 = this.charAt(var10 + var21);
               if(var12 < 48 || var12 > 57) {
                  boolean var22;
                  if(var12 == 46) {
                     var22 = true;
                  } else {
                     var22 = false;
                  }

                  int var27;
                  if(var22) {
                     var10 = this.bp;
                     var21 = var11 + 1;
                     char var23 = this.charAt(var10 + var11);
                     if(var23 < 48 || var23 > 57) {
                        this.matchStat = -1;
                        return 0.0D;
                     }

                     var8 = var8 * 10 + (var23 - 48);
                     byte var25 = 10;
                     var11 = var21;
                     var21 = var25;

                     while(true) {
                        var27 = this.bp;
                        var10 = var11 + 1;
                        var12 = this.charAt(var27 + var11);
                        if(var12 < 48 || var12 > 57) {
                           var11 = var10;
                           break;
                        }

                        var8 = var8 * 10 + (var12 - 48);
                        var21 *= 10;
                        var11 = var10;
                     }
                  } else {
                     var21 = 1;
                  }

                  boolean var26;
                  if(var12 != 101 && var12 != 69) {
                     var26 = false;
                  } else {
                     var26 = true;
                  }

                  int var14 = var8;
                  int var15 = var21;
                  boolean var16 = var20;
                  char var17 = var12;
                  int var13 = var11;
                  boolean var18 = var26;
                  if(var26) {
                     var13 = this.bp;
                     var27 = var11 + 1;
                     char var28 = this.charAt(var13 + var11);
                     if(var28 != 43 && var28 != 45) {
                        var11 = var27;
                        var12 = var28;
                     } else {
                        var13 = this.bp;
                        var11 = var27 + 1;
                        var12 = this.charAt(var13 + var27);
                     }

                     while(true) {
                        var14 = var8;
                        var15 = var21;
                        var16 = var20;
                        var17 = var12;
                        var13 = var11;
                        var18 = var26;
                        if(var12 < 48) {
                           break;
                        }

                        var14 = var8;
                        var15 = var21;
                        var16 = var20;
                        var17 = var12;
                        var13 = var11;
                        var18 = var26;
                        if(var12 > 57) {
                           break;
                        }

                        var13 = this.bp;
                        var27 = var11 + 1;
                        var28 = this.charAt(var13 + var11);
                        var11 = var27;
                        var12 = var28;
                     }
                  }

                  var7 = this.bp + var13 - var19 - 1;
                  double var3;
                  if(!var18 && var7 < 10) {
                     double var5 = (double)var14 / (double)var15;
                     var3 = var5;
                     if(var16) {
                        var3 = -var5;
                     }
                  } else {
                     var3 = Double.parseDouble(this.subString(var19, var7));
                  }

                  if(var17 == 44) {
                     this.bp += var13 - 1;
                     this.next();
                     this.matchStat = 3;
                     this.token = 16;
                     return var3;
                  } else if(var17 == 125) {
                     var8 = this.bp;
                     var7 = var13 + 1;
                     char var24 = this.charAt(var8 + var13);
                     if(var24 == 44) {
                        this.token = 16;
                        this.bp += var7 - 1;
                        this.next();
                     } else if(var24 == 93) {
                        this.token = 15;
                        this.bp += var7 - 1;
                        this.next();
                     } else if(var24 == 125) {
                        this.token = 13;
                        this.bp += var7 - 1;
                        this.next();
                     } else {
                        if(var24 != 26) {
                           this.matchStat = -1;
                           return 0.0D;
                        }

                        this.bp += var7 - 1;
                        this.token = 20;
                        this.ch = 26;
                     }

                     this.matchStat = 4;
                     return var3;
                  } else {
                     this.matchStat = -1;
                     return 0.0D;
                  }
               }

               var8 = var8 * 10 + (var12 - 48);
               var21 = var11;
            }
         } else {
            this.matchStat = -1;
            return 0.0D;
         }
      }
   }

   public final double[] scanFieldDoubleArray(long var1) {
      this.matchStat = 0;
      int var7 = this.matchFieldHash(var1);
      if(var7 == 0) {
         return null;
      } else {
         int var9 = this.bp;
         int var8 = var7 + 1;
         var7 += var9;
         char var24;
         if(var7 >= this.len) {
            var24 = 26;
         } else {
            var24 = this.text.charAt(var7);
         }

         if(var24 != 91) {
            this.matchStat = -1;
            return null;
         } else {
            var7 = this.bp;
            int var10 = var8 + 1;
            var7 += var8;
            char var26;
            if(var7 >= this.len) {
               var26 = 26;
            } else {
               var26 = this.text.charAt(var7);
            }

            double[] var22 = new double[16];
            var7 = 0;

            while(true) {
               int var21 = this.bp + var10 - 1;
               boolean var25;
               if(var26 == 45) {
                  var25 = true;
               } else {
                  var25 = false;
               }

               int var11;
               if(var25) {
                  var9 = this.bp;
                  var11 = var10 + 1;
                  var9 += var10;
                  if(var9 >= this.len) {
                     var26 = 26;
                     var10 = var11;
                  } else {
                     var26 = this.text.charAt(var9);
                     var10 = var11;
                  }
               }

               if(var26 < 48 || var26 > 57) {
                  this.matchStat = -1;
                  return null;
               }

               var11 = var26 - 48;
               var9 = var10;
               var10 = var11;

               while(true) {
                  var11 = this.bp;
                  int var13 = var9 + 1;
                  var9 += var11;
                  char var14;
                  if(var9 >= this.len) {
                     var14 = 26;
                  } else {
                     var14 = this.text.charAt(var9);
                  }

                  if(var14 < 48 || var14 > 57) {
                     boolean var28;
                     if(var14 == 46) {
                        var28 = true;
                     } else {
                        var28 = false;
                     }

                     int var12;
                     char var15;
                     int var16;
                     if(var28) {
                        var9 = this.bp;
                        var11 = var13 + 1;
                        var9 += var13;
                        if(var9 >= this.len) {
                           var26 = 26;
                        } else {
                           var26 = this.text.charAt(var9);
                        }

                        if(var26 < 48 || var26 > 57) {
                           this.matchStat = -1;
                           return null;
                        }

                        var12 = var10 * 10 + (var26 - 48);
                        byte var27 = 10;
                        var9 = var11;
                        var11 = var27;

                        while(true) {
                           var10 = this.bp;
                           var16 = var9 + 1;
                           var9 += var10;
                           if(var9 >= this.len) {
                              var15 = 26;
                           } else {
                              var15 = this.text.charAt(var9);
                           }

                           var9 = var11;
                           var10 = var12;
                           var14 = var15;
                           var13 = var16;
                           if(var15 < 48) {
                              break;
                           }

                           var9 = var11;
                           var10 = var12;
                           var14 = var15;
                           var13 = var16;
                           if(var15 > 57) {
                              break;
                           }

                           var12 = var12 * 10 + (var15 - 48);
                           var11 *= 10;
                           var9 = var16;
                        }
                     } else {
                        var9 = 1;
                     }

                     boolean var29;
                     if(var14 != 101 && var14 != 69) {
                        var29 = false;
                     } else {
                        var29 = true;
                     }

                     var16 = var9;
                     int var17 = var10;
                     double[] var23 = var22;
                     int var18 = var7;
                     boolean var19 = var25;
                     boolean var20 = var29;
                     var12 = var13;
                     if(var29) {
                        int var31 = this.bp;
                        var12 = var13 + 1;
                        var13 += var31;
                        if(var13 >= this.len) {
                           var15 = 26;
                        } else {
                           var15 = this.text.charAt(var13);
                        }

                        char var30;
                        if(var15 != 43 && var15 != 45) {
                           var13 = var12;
                        } else {
                           var31 = this.bp;
                           var13 = var12 + 1;
                           var12 += var31;
                           if(var12 >= this.len) {
                              var30 = 26;
                           } else {
                              var30 = this.text.charAt(var12);
                           }

                           var15 = var30;
                        }

                        while(true) {
                           var16 = var9;
                           var17 = var10;
                           var14 = var15;
                           var23 = var22;
                           var18 = var7;
                           var19 = var25;
                           var20 = var29;
                           var12 = var13;
                           if(var15 < 48) {
                              break;
                           }

                           var16 = var9;
                           var17 = var10;
                           var14 = var15;
                           var23 = var22;
                           var18 = var7;
                           var19 = var25;
                           var20 = var29;
                           var12 = var13;
                           if(var15 > 57) {
                              break;
                           }

                           var12 = this.bp;
                           var31 = var13 + 1;
                           var12 += var13;
                           if(var12 >= this.len) {
                              var13 = var31;
                              var30 = 26;
                           } else {
                              var30 = this.text.charAt(var12);
                              var13 = var31;
                           }

                           var15 = var30;
                        }
                     }

                     var7 = this.bp + var12 - var21 - 1;
                     double var3;
                     if(!var20 && var7 < 10) {
                        double var5 = (double)var17 / (double)var16;
                        var3 = var5;
                        if(var19) {
                           var3 = -var5;
                        }
                     } else {
                        var3 = Double.parseDouble(this.subString(var21, var7));
                     }

                     var22 = var23;
                     if(var18 >= var23.length) {
                        var22 = new double[var23.length * 3 / 2];
                        System.arraycopy(var23, 0, var22, 0, var18);
                     }

                     var11 = var18 + 1;
                     var22[var18] = var3;
                     if(var14 == 44) {
                        var7 = this.bp + var12;
                        if(var7 >= this.len) {
                           var24 = 26;
                        } else {
                           var24 = this.text.charAt(var7);
                        }

                        var8 = var12 + 1;
                     } else {
                        var24 = var14;
                        var8 = var12;
                        if(var14 == 93) {
                           var7 = this.bp;
                           var8 = var12 + 1;
                           var7 += var12;
                           if(var7 >= this.len) {
                              var24 = 26;
                           } else {
                              var24 = this.text.charAt(var7);
                           }

                           var23 = var22;
                           if(var11 != var22.length) {
                              var23 = new double[var11];
                              System.arraycopy(var22, 0, var23, 0, var11);
                           }

                           if(var24 == 44) {
                              this.bp += var8 - 1;
                              this.next();
                              this.matchStat = 3;
                              this.token = 16;
                              return var23;
                           }

                           if(var24 == 125) {
                              var7 = this.bp;
                              var9 = var8 + 1;
                              var7 += var8;
                              if(var7 >= this.len) {
                                 var24 = 26;
                              } else {
                                 var24 = this.text.charAt(var7);
                              }

                              if(var24 == 44) {
                                 this.token = 16;
                                 this.bp += var9 - 1;
                                 this.next();
                              } else if(var24 == 93) {
                                 this.token = 15;
                                 this.bp += var9 - 1;
                                 this.next();
                              } else if(var24 == 125) {
                                 this.token = 13;
                                 this.bp += var9 - 1;
                                 this.next();
                              } else {
                                 if(var24 != 26) {
                                    this.matchStat = -1;
                                    return null;
                                 }

                                 this.bp += var9 - 1;
                                 this.token = 20;
                                 this.ch = 26;
                              }

                              this.matchStat = 4;
                              return var23;
                           }

                           this.matchStat = -1;
                           return null;
                        }
                     }

                     var26 = var24;
                     var10 = var8;
                     var7 = var11;
                     break;
                  }

                  var10 = var10 * 10 + (var14 - 48);
                  var9 = var13;
               }
            }
         }
      }
   }

   public final double[][] scanFieldDoubleArray2(long var1) {
      this.matchStat = 0;
      int var7 = this.matchFieldHash(var1);
      Object var26 = null;
      if(var7 == 0) {
         return (double[][])null;
      } else {
         int var8 = this.bp;
         int var9 = var7 + 1;
         var7 += var8;
         char var28;
         if(var7 >= this.len) {
            var28 = 26;
         } else {
            var28 = this.text.charAt(var7);
         }

         if(var28 != 91) {
            this.matchStat = -1;
            return (double[][])null;
         } else {
            var7 = this.bp;
            var8 = var9 + 1;
            var7 += var9;
            if(var7 >= this.len) {
               var28 = 26;
            } else {
               var28 = this.text.charAt(var7);
            }

            double[][] var24 = new double[16][];
            int var12 = 0;
            char var30 = var28;

            label250:
            while(true) {
               while(var30 != 91) {
                  ;
               }

               var7 = this.bp;
               var9 = var8 + 1;
               var7 += var8;
               char var10;
               if(var7 >= this.len) {
                  var10 = 26;
               } else {
                  var10 = this.text.charAt(var7);
               }

               double[] var27 = new double[16];
               var7 = 0;
               double[][] var25 = var24;
               double[] var38 = var27;
               Object var41 = var26;

               while(true) {
                  int var23 = this.bp + var9 - 1;
                  boolean var29;
                  if(var10 == 45) {
                     var29 = true;
                  } else {
                     var29 = false;
                  }

                  char var11 = var10;
                  int var32 = var9;
                  if(var29) {
                     var32 = this.bp + var9;
                     if(var32 >= this.len) {
                        var11 = 26;
                     } else {
                        var11 = this.text.charAt(var32);
                     }

                     var32 = var9 + 1;
                  }

                  if(var11 < 48 || var11 > 57) {
                     this.matchStat = -1;
                     return (double[][])var41;
                  }

                  int var33 = var11 - 48;
                  var9 = var32;
                  var32 = var33;

                  while(true) {
                     var33 = this.bp;
                     int var14 = var9 + 1;
                     var9 += var33;
                     char var15;
                     if(var9 >= this.len) {
                        var15 = 26;
                     } else {
                        var15 = this.text.charAt(var9);
                     }

                     if(var15 < 48 || var15 > 57) {
                        int var13;
                        char var16;
                        int var17;
                        if(var15 == 46) {
                           var9 = this.bp;
                           var33 = var14 + 1;
                           var9 += var14;
                           if(var9 >= this.len) {
                              var30 = 26;
                           } else {
                              var30 = this.text.charAt(var9);
                           }

                           if(var30 < 48 || var30 > 57) {
                              this.matchStat = -1;
                              return (double[][])var41;
                           }

                           var13 = var32 * 10 + (var30 - 48);
                           byte var34 = 10;
                           var9 = var33;
                           var33 = var34;

                           while(true) {
                              var32 = this.bp;
                              var17 = var9 + 1;
                              var9 += var32;
                              if(var9 >= this.len) {
                                 var16 = 26;
                              } else {
                                 var16 = this.text.charAt(var9);
                              }

                              var9 = var33;
                              var32 = var13;
                              var15 = var16;
                              var14 = var17;
                              if(var16 < 48) {
                                 break;
                              }

                              var9 = var33;
                              var32 = var13;
                              var15 = var16;
                              var14 = var17;
                              if(var16 > 57) {
                                 break;
                              }

                              var13 = var13 * 10 + (var16 - 48);
                              var33 *= 10;
                              var9 = var17;
                           }
                        } else {
                           var9 = 1;
                        }

                        boolean var35;
                        if(var15 != 101 && var15 != 69) {
                           var35 = false;
                        } else {
                           var35 = true;
                        }

                        int var18 = var9;
                        int var19 = var32;
                        var27 = var38;
                        var17 = var12;
                        double[][] var40 = var25;
                        boolean var21 = var35;
                        int var20 = var7;
                        boolean var22 = var29;
                        var13 = var14;
                        if(var35) {
                           var13 = this.bp;
                           int var37 = var14 + 1;
                           var13 += var14;
                           char var36;
                           if(var13 >= this.len) {
                              var36 = 26;
                           } else {
                              var36 = this.text.charAt(var13);
                           }

                           if(var36 != 43 && var36 != 45) {
                              var14 = var37;
                              var16 = var36;
                           } else {
                              var13 = this.bp;
                              var14 = var37 + 1;
                              var13 += var37;
                              if(var13 >= this.len) {
                                 var36 = 26;
                              } else {
                                 var36 = this.text.charAt(var13);
                              }

                              var16 = var36;
                           }

                           while(true) {
                              var18 = var9;
                              var19 = var32;
                              var15 = var16;
                              var27 = var38;
                              var17 = var12;
                              var40 = var25;
                              var21 = var35;
                              var20 = var7;
                              var22 = var29;
                              var13 = var14;
                              if(var16 < 48) {
                                 break;
                              }

                              var18 = var9;
                              var19 = var32;
                              var15 = var16;
                              var27 = var38;
                              var17 = var12;
                              var40 = var25;
                              var21 = var35;
                              var20 = var7;
                              var22 = var29;
                              var13 = var14;
                              if(var16 > 57) {
                                 break;
                              }

                              var13 = this.bp;
                              var37 = var14 + 1;
                              var13 += var14;
                              if(var13 >= this.len) {
                                 var14 = var37;
                                 var36 = 26;
                              } else {
                                 var36 = this.text.charAt(var13);
                                 var14 = var37;
                              }

                              var16 = var36;
                           }
                        }

                        var7 = this.bp + var13 - var23 - 1;
                        double var3;
                        if(!var21 && var7 < 10) {
                           double var5 = (double)var19 / (double)var18;
                           var3 = var5;
                           if(var22) {
                              var3 = -var5;
                           }
                        } else {
                           var3 = Double.parseDouble(this.subString(var23, var7));
                        }

                        var38 = var27;
                        if(var20 >= var27.length) {
                           var38 = new double[var27.length * 3 / 2];
                           System.arraycopy(var27, 0, var38, 0, var20);
                        }

                        var8 = var20 + 1;
                        var38[var20] = var3;
                        if(var15 == 44) {
                           var7 = this.bp + var13;
                           if(var7 >= this.len) {
                              var28 = 26;
                           } else {
                              var28 = this.text.charAt(var7);
                           }

                           ++var13;
                           var10 = var28;
                        } else {
                           if(var15 == 93) {
                              var7 = this.bp;
                              var9 = var13 + 1;
                              var7 += var13;
                              if(var7 >= this.len) {
                                 var28 = 26;
                              } else {
                                 var28 = this.text.charAt(var7);
                              }

                              double[] var39;
                              if(var8 != var38.length) {
                                 var39 = new double[var8];
                                 System.arraycopy(var38, 0, var39, 0, var8);
                              } else {
                                 var39 = var38;
                              }

                              var24 = var40;
                              if(var17 >= var40.length) {
                                 var24 = new double[var40.length * 3 / 2][];
                                 System.arraycopy(var39, 0, var24, 0, var8);
                              }

                              var12 = var17 + 1;
                              var24[var17] = var39;
                              char var31;
                              if(var28 == 44) {
                                 var7 = this.bp + var9;
                                 if(var7 >= this.len) {
                                    var28 = 26;
                                 } else {
                                    var28 = this.text.charAt(var7);
                                 }

                                 ++var9;
                                 var31 = var28;
                                 var7 = var9;
                              } else {
                                 if(var28 == 93) {
                                    var7 = this.bp;
                                    var8 = var9 + 1;
                                    var7 += var9;
                                    if(var7 >= this.len) {
                                       var28 = 26;
                                    } else {
                                       var28 = this.text.charAt(var7);
                                    }

                                    if(var12 != var24.length) {
                                       var25 = new double[var12][];
                                       System.arraycopy(var24, 0, var25, 0, var12);
                                       var24 = var25;
                                    }

                                    if(var28 == 44) {
                                       this.bp += var8 - 1;
                                       this.next();
                                       this.matchStat = 3;
                                       this.token = 16;
                                       return var24;
                                    }

                                    if(var28 == 125) {
                                       var9 = this.bp;
                                       var7 = var8 + 1;
                                       var31 = this.charAt(var9 + var8);
                                       if(var31 == 44) {
                                          this.token = 16;
                                          this.bp += var7 - 1;
                                          this.next();
                                       } else if(var31 == 93) {
                                          this.token = 15;
                                          this.bp += var7 - 1;
                                          this.next();
                                       } else if(var31 == 125) {
                                          this.token = 13;
                                          this.bp += var7 - 1;
                                          this.next();
                                       } else {
                                          if(var31 != 26) {
                                             this.matchStat = -1;
                                             return (double[][])null;
                                          }

                                          this.bp += var7 - 1;
                                          this.token = 20;
                                          this.ch = 26;
                                       }

                                       this.matchStat = 4;
                                       return var24;
                                    }

                                    this.matchStat = -1;
                                    return (double[][])null;
                                 }

                                 var31 = var28;
                                 var7 = var9;
                              }

                              var26 = null;
                              var30 = var31;
                              var8 = var7;
                              continue label250;
                           }

                           var10 = var15;
                        }

                        var41 = null;
                        var9 = var13;
                        var7 = var8;
                        var12 = var17;
                        var25 = var40;
                        break;
                     }

                     var32 = var32 * 10 + (var15 - 48);
                     var9 = var14;
                  }
               }
            }
         }
      }
   }

   public final float scanFieldFloat(long var1) {
      boolean var11 = false;
      this.matchStat = 0;
      int var5 = this.matchFieldHash(var1);
      if(var5 == 0) {
         return 0.0F;
      } else {
         int var6 = this.bp;
         int var8 = var5 + 1;
         char var7 = this.charAt(var6 + var5);
         int var17 = this.bp + var8 - 1;
         boolean var18;
         if(var7 == 45) {
            var18 = true;
         } else {
            var18 = false;
         }

         var6 = var8;
         if(var18) {
            var7 = this.charAt(this.bp + var8);
            var6 = var8 + 1;
         }

         if(var7 >= 48 && var7 <= 57) {
            var8 = var7 - 48;
            int var19 = var6;
            var6 = var8;

            while(true) {
               var8 = this.bp;
               int var9 = var19 + 1;
               char var10 = this.charAt(var8 + var19);
               if(var10 < 48 || var10 > 57) {
                  boolean var20;
                  if(var10 == 46) {
                     var20 = true;
                  } else {
                     var20 = false;
                  }

                  int var25;
                  if(var20) {
                     var8 = this.bp;
                     var19 = var9 + 1;
                     char var21 = this.charAt(var8 + var9);
                     if(var21 < 48 || var21 > 57) {
                        this.matchStat = -1;
                        return 0.0F;
                     }

                     var6 = var6 * 10 + (var21 - 48);
                     byte var23 = 10;
                     var9 = var19;
                     var19 = var23;

                     while(true) {
                        var25 = this.bp;
                        var8 = var9 + 1;
                        var10 = this.charAt(var25 + var9);
                        if(var10 < 48 || var10 > 57) {
                           var9 = var8;
                           break;
                        }

                        var6 = var6 * 10 + (var10 - 48);
                        var19 *= 10;
                        var9 = var8;
                     }
                  } else {
                     var19 = 1;
                  }

                  boolean var24;
                  label103: {
                     if(var10 != 101) {
                        var24 = var11;
                        if(var10 != 69) {
                           break label103;
                        }
                     }

                     var24 = true;
                  }

                  boolean var12 = var24;
                  int var13 = var6;
                  int var14 = var19;
                  boolean var15 = var18;
                  char var16 = var10;
                  int var27 = var9;
                  if(var24) {
                     var25 = this.bp;
                     var27 = var9 + 1;
                     var10 = this.charAt(var25 + var9);
                     char var26;
                     char var28;
                     if(var10 != 43 && var10 != 45) {
                        var9 = var27;
                     } else {
                        var9 = this.bp;
                        var25 = var27 + 1;
                        var26 = this.charAt(var9 + var27);
                        var28 = var26;
                        var9 = var25;
                        var10 = var28;
                     }

                     while(true) {
                        var12 = var24;
                        var13 = var6;
                        var14 = var19;
                        var15 = var18;
                        var16 = var10;
                        var27 = var9;
                        if(var10 < 48) {
                           break;
                        }

                        var12 = var24;
                        var13 = var6;
                        var14 = var19;
                        var15 = var18;
                        var16 = var10;
                        var27 = var9;
                        if(var10 > 57) {
                           break;
                        }

                        var27 = this.bp;
                        var25 = var9 + 1;
                        var26 = this.charAt(var27 + var9);
                        var28 = var26;
                        var9 = var25;
                        var10 = var28;
                     }
                  }

                  var5 = this.bp + var27 - var17 - 1;
                  float var3;
                  if(!var12 && var5 < 10) {
                     float var4 = (float)var13 / (float)var14;
                     var3 = var4;
                     if(var15) {
                        var3 = -var4;
                     }
                  } else {
                     var3 = Float.parseFloat(this.subString(var17, var5));
                  }

                  if(var16 == 44) {
                     this.bp += var27 - 1;
                     this.next();
                     this.matchStat = 3;
                     this.token = 16;
                     return var3;
                  } else if(var16 == 125) {
                     var6 = this.bp;
                     var5 = var27 + 1;
                     char var22 = this.charAt(var6 + var27);
                     if(var22 == 44) {
                        this.token = 16;
                        this.bp += var5 - 1;
                        this.next();
                     } else if(var22 == 93) {
                        this.token = 15;
                        this.bp += var5 - 1;
                        this.next();
                     } else if(var22 == 125) {
                        this.token = 13;
                        this.bp += var5 - 1;
                        this.next();
                     } else {
                        if(var22 != 26) {
                           this.matchStat = -1;
                           return 0.0F;
                        }

                        this.bp += var5 - 1;
                        this.token = 20;
                        this.ch = 26;
                     }

                     this.matchStat = 4;
                     return var3;
                  } else {
                     this.matchStat = -1;
                     return 0.0F;
                  }
               }

               var6 = var6 * 10 + (var10 - 48);
               var19 = var9;
            }
         } else {
            this.matchStat = -1;
            return 0.0F;
         }
      }
   }

   public final float[] scanFieldFloatArray(long var1) {
      this.matchStat = 0;
      int var5 = this.matchFieldHash(var1);
      if(var5 == 0) {
         return null;
      } else {
         int var7 = this.bp;
         int var6 = var5 + 1;
         var5 += var7;
         char var22;
         if(var5 >= this.len) {
            var22 = 26;
         } else {
            var22 = this.text.charAt(var5);
         }

         if(var22 != 91) {
            this.matchStat = -1;
            return null;
         } else {
            var5 = this.bp;
            int var8 = var6 + 1;
            var5 += var6;
            char var24;
            if(var5 >= this.len) {
               var24 = 26;
            } else {
               var24 = this.text.charAt(var5);
            }

            float[] var20 = new float[16];
            var5 = 0;

            while(true) {
               int var19 = this.bp + var8 - 1;
               boolean var23;
               if(var24 == 45) {
                  var23 = true;
               } else {
                  var23 = false;
               }

               int var9;
               if(var23) {
                  var7 = this.bp;
                  var9 = var8 + 1;
                  var7 += var8;
                  if(var7 >= this.len) {
                     var24 = 26;
                     var8 = var9;
                  } else {
                     var24 = this.text.charAt(var7);
                     var8 = var9;
                  }
               }

               if(var24 < 48 || var24 > 57) {
                  this.matchStat = -1;
                  return null;
               }

               var9 = var24 - 48;
               var7 = var8;
               var8 = var9;

               while(true) {
                  var9 = this.bp;
                  int var11 = var7 + 1;
                  var7 += var9;
                  char var12;
                  if(var7 >= this.len) {
                     var12 = 26;
                  } else {
                     var12 = this.text.charAt(var7);
                  }

                  if(var12 < 48 || var12 > 57) {
                     boolean var26;
                     if(var12 == 46) {
                        var26 = true;
                     } else {
                        var26 = false;
                     }

                     int var10;
                     char var13;
                     int var14;
                     if(var26) {
                        var7 = this.bp;
                        var9 = var11 + 1;
                        var7 += var11;
                        if(var7 >= this.len) {
                           var24 = 26;
                        } else {
                           var24 = this.text.charAt(var7);
                        }

                        if(var24 < 48 || var24 > 57) {
                           this.matchStat = -1;
                           return null;
                        }

                        var10 = var8 * 10 + (var24 - 48);
                        byte var25 = 10;
                        var7 = var9;
                        var9 = var25;

                        while(true) {
                           var8 = this.bp;
                           var14 = var7 + 1;
                           var7 += var8;
                           if(var7 >= this.len) {
                              var13 = 26;
                           } else {
                              var13 = this.text.charAt(var7);
                           }

                           var7 = var9;
                           var8 = var10;
                           var12 = var13;
                           var11 = var14;
                           if(var13 < 48) {
                              break;
                           }

                           var7 = var9;
                           var8 = var10;
                           var12 = var13;
                           var11 = var14;
                           if(var13 > 57) {
                              break;
                           }

                           var10 = var10 * 10 + (var13 - 48);
                           var9 *= 10;
                           var7 = var14;
                        }
                     } else {
                        var7 = 1;
                     }

                     boolean var27;
                     if(var12 != 101 && var12 != 69) {
                        var27 = false;
                     } else {
                        var27 = true;
                     }

                     var14 = var7;
                     int var15 = var8;
                     float[] var21 = var20;
                     int var16 = var5;
                     boolean var17 = var23;
                     boolean var18 = var27;
                     var10 = var11;
                     if(var27) {
                        int var29 = this.bp;
                        var10 = var11 + 1;
                        var11 += var29;
                        if(var11 >= this.len) {
                           var13 = 26;
                        } else {
                           var13 = this.text.charAt(var11);
                        }

                        char var28;
                        if(var13 != 43 && var13 != 45) {
                           var11 = var10;
                        } else {
                           var29 = this.bp;
                           var11 = var10 + 1;
                           var10 += var29;
                           if(var10 >= this.len) {
                              var28 = 26;
                           } else {
                              var28 = this.text.charAt(var10);
                           }

                           var13 = var28;
                        }

                        while(true) {
                           var14 = var7;
                           var15 = var8;
                           var12 = var13;
                           var21 = var20;
                           var16 = var5;
                           var17 = var23;
                           var18 = var27;
                           var10 = var11;
                           if(var13 < 48) {
                              break;
                           }

                           var14 = var7;
                           var15 = var8;
                           var12 = var13;
                           var21 = var20;
                           var16 = var5;
                           var17 = var23;
                           var18 = var27;
                           var10 = var11;
                           if(var13 > 57) {
                              break;
                           }

                           var10 = this.bp;
                           var29 = var11 + 1;
                           var10 += var11;
                           if(var10 >= this.len) {
                              var11 = var29;
                              var28 = 26;
                           } else {
                              var28 = this.text.charAt(var10);
                              var11 = var29;
                           }

                           var13 = var28;
                        }
                     }

                     var5 = this.bp + var10 - var19 - 1;
                     float var3;
                     if(!var18 && var5 < 10) {
                        float var4 = (float)var15 / (float)var14;
                        var3 = var4;
                        if(var17) {
                           var3 = -var4;
                        }
                     } else {
                        var3 = Float.parseFloat(this.subString(var19, var5));
                     }

                     var20 = var21;
                     if(var16 >= var21.length) {
                        var20 = new float[var21.length * 3 / 2];
                        System.arraycopy(var21, 0, var20, 0, var16);
                     }

                     var9 = var16 + 1;
                     var20[var16] = var3;
                     if(var12 == 44) {
                        var5 = this.bp + var10;
                        if(var5 >= this.len) {
                           var22 = 26;
                        } else {
                           var22 = this.text.charAt(var5);
                        }

                        var6 = var10 + 1;
                     } else {
                        var22 = var12;
                        var6 = var10;
                        if(var12 == 93) {
                           var5 = this.bp;
                           var6 = var10 + 1;
                           var5 += var10;
                           if(var5 >= this.len) {
                              var22 = 26;
                           } else {
                              var22 = this.text.charAt(var5);
                           }

                           var21 = var20;
                           if(var9 != var20.length) {
                              var21 = new float[var9];
                              System.arraycopy(var20, 0, var21, 0, var9);
                           }

                           if(var22 == 44) {
                              this.bp += var6 - 1;
                              this.next();
                              this.matchStat = 3;
                              this.token = 16;
                              return var21;
                           }

                           if(var22 == 125) {
                              var5 = this.bp;
                              var7 = var6 + 1;
                              var5 += var6;
                              if(var5 >= this.len) {
                                 var22 = 26;
                              } else {
                                 var22 = this.text.charAt(var5);
                              }

                              if(var22 == 44) {
                                 this.token = 16;
                                 this.bp += var7 - 1;
                                 this.next();
                              } else if(var22 == 93) {
                                 this.token = 15;
                                 this.bp += var7 - 1;
                                 this.next();
                              } else if(var22 == 125) {
                                 this.token = 13;
                                 this.bp += var7 - 1;
                                 this.next();
                              } else {
                                 if(var22 != 26) {
                                    this.matchStat = -1;
                                    return null;
                                 }

                                 this.bp += var7 - 1;
                                 this.token = 20;
                                 this.ch = 26;
                              }

                              this.matchStat = 4;
                              return var21;
                           }

                           this.matchStat = -1;
                           return null;
                        }
                     }

                     var24 = var22;
                     var8 = var6;
                     var5 = var9;
                     break;
                  }

                  var8 = var8 * 10 + (var12 - 48);
                  var7 = var11;
               }
            }
         }
      }
   }

   public final float[][] scanFieldFloatArray2(long var1) {
      this.matchStat = 0;
      int var5 = this.matchFieldHash(var1);
      Object var24 = null;
      if(var5 == 0) {
         return (float[][])null;
      } else {
         int var6 = this.bp;
         int var7 = var5 + 1;
         var5 += var6;
         char var26;
         if(var5 >= this.len) {
            var26 = 26;
         } else {
            var26 = this.text.charAt(var5);
         }

         if(var26 != 91) {
            this.matchStat = -1;
            return (float[][])null;
         } else {
            var5 = this.bp;
            var6 = var7 + 1;
            var5 += var7;
            if(var5 >= this.len) {
               var26 = 26;
            } else {
               var26 = this.text.charAt(var5);
            }

            float[][] var22 = new float[16][];
            int var10 = 0;
            char var28 = var26;

            label250:
            while(true) {
               while(var28 != 91) {
                  ;
               }

               var5 = this.bp;
               var7 = var6 + 1;
               var5 += var6;
               char var8;
               if(var5 >= this.len) {
                  var8 = 26;
               } else {
                  var8 = this.text.charAt(var5);
               }

               float[] var25 = new float[16];
               var5 = 0;
               float[][] var23 = var22;
               float[] var36 = var25;
               Object var39 = var24;

               while(true) {
                  int var21 = this.bp + var7 - 1;
                  boolean var27;
                  if(var8 == 45) {
                     var27 = true;
                  } else {
                     var27 = false;
                  }

                  char var9 = var8;
                  int var30 = var7;
                  if(var27) {
                     var30 = this.bp + var7;
                     if(var30 >= this.len) {
                        var9 = 26;
                     } else {
                        var9 = this.text.charAt(var30);
                     }

                     var30 = var7 + 1;
                  }

                  if(var9 < 48 || var9 > 57) {
                     this.matchStat = -1;
                     return (float[][])var39;
                  }

                  int var31 = var9 - 48;
                  var7 = var30;
                  var30 = var31;

                  while(true) {
                     var31 = this.bp;
                     int var12 = var7 + 1;
                     var7 += var31;
                     char var13;
                     if(var7 >= this.len) {
                        var13 = 26;
                     } else {
                        var13 = this.text.charAt(var7);
                     }

                     if(var13 < 48 || var13 > 57) {
                        int var11;
                        char var14;
                        int var15;
                        if(var13 == 46) {
                           var7 = this.bp;
                           var31 = var12 + 1;
                           var7 += var12;
                           if(var7 >= this.len) {
                              var28 = 26;
                           } else {
                              var28 = this.text.charAt(var7);
                           }

                           if(var28 < 48 || var28 > 57) {
                              this.matchStat = -1;
                              return (float[][])var39;
                           }

                           var11 = var30 * 10 + (var28 - 48);
                           byte var32 = 10;
                           var7 = var31;
                           var31 = var32;

                           while(true) {
                              var30 = this.bp;
                              var15 = var7 + 1;
                              var7 += var30;
                              if(var7 >= this.len) {
                                 var14 = 26;
                              } else {
                                 var14 = this.text.charAt(var7);
                              }

                              var7 = var31;
                              var30 = var11;
                              var13 = var14;
                              var12 = var15;
                              if(var14 < 48) {
                                 break;
                              }

                              var7 = var31;
                              var30 = var11;
                              var13 = var14;
                              var12 = var15;
                              if(var14 > 57) {
                                 break;
                              }

                              var11 = var11 * 10 + (var14 - 48);
                              var31 *= 10;
                              var7 = var15;
                           }
                        } else {
                           var7 = 1;
                        }

                        boolean var33;
                        if(var13 != 101 && var13 != 69) {
                           var33 = false;
                        } else {
                           var33 = true;
                        }

                        int var16 = var7;
                        int var17 = var30;
                        var25 = var36;
                        var15 = var10;
                        float[][] var38 = var23;
                        boolean var19 = var33;
                        int var18 = var5;
                        boolean var20 = var27;
                        var11 = var12;
                        if(var33) {
                           var11 = this.bp;
                           int var35 = var12 + 1;
                           var11 += var12;
                           char var34;
                           if(var11 >= this.len) {
                              var34 = 26;
                           } else {
                              var34 = this.text.charAt(var11);
                           }

                           if(var34 != 43 && var34 != 45) {
                              var12 = var35;
                              var14 = var34;
                           } else {
                              var11 = this.bp;
                              var12 = var35 + 1;
                              var11 += var35;
                              if(var11 >= this.len) {
                                 var34 = 26;
                              } else {
                                 var34 = this.text.charAt(var11);
                              }

                              var14 = var34;
                           }

                           while(true) {
                              var16 = var7;
                              var17 = var30;
                              var13 = var14;
                              var25 = var36;
                              var15 = var10;
                              var38 = var23;
                              var19 = var33;
                              var18 = var5;
                              var20 = var27;
                              var11 = var12;
                              if(var14 < 48) {
                                 break;
                              }

                              var16 = var7;
                              var17 = var30;
                              var13 = var14;
                              var25 = var36;
                              var15 = var10;
                              var38 = var23;
                              var19 = var33;
                              var18 = var5;
                              var20 = var27;
                              var11 = var12;
                              if(var14 > 57) {
                                 break;
                              }

                              var11 = this.bp;
                              var35 = var12 + 1;
                              var11 += var12;
                              if(var11 >= this.len) {
                                 var12 = var35;
                                 var34 = 26;
                              } else {
                                 var34 = this.text.charAt(var11);
                                 var12 = var35;
                              }

                              var14 = var34;
                           }
                        }

                        var5 = this.bp + var11 - var21 - 1;
                        float var3;
                        if(!var19 && var5 < 10) {
                           float var4 = (float)var17 / (float)var16;
                           var3 = var4;
                           if(var20) {
                              var3 = -var4;
                           }
                        } else {
                           var3 = Float.parseFloat(this.subString(var21, var5));
                        }

                        var36 = var25;
                        if(var18 >= var25.length) {
                           var36 = new float[var25.length * 3 / 2];
                           System.arraycopy(var25, 0, var36, 0, var18);
                        }

                        var6 = var18 + 1;
                        var36[var18] = var3;
                        if(var13 == 44) {
                           var5 = this.bp + var11;
                           if(var5 >= this.len) {
                              var26 = 26;
                           } else {
                              var26 = this.text.charAt(var5);
                           }

                           ++var11;
                           var8 = var26;
                        } else {
                           if(var13 == 93) {
                              var5 = this.bp;
                              var7 = var11 + 1;
                              var5 += var11;
                              if(var5 >= this.len) {
                                 var26 = 26;
                              } else {
                                 var26 = this.text.charAt(var5);
                              }

                              float[] var37;
                              if(var6 != var36.length) {
                                 var37 = new float[var6];
                                 System.arraycopy(var36, 0, var37, 0, var6);
                              } else {
                                 var37 = var36;
                              }

                              var22 = var38;
                              if(var15 >= var38.length) {
                                 var22 = new float[var38.length * 3 / 2][];
                                 System.arraycopy(var37, 0, var22, 0, var6);
                              }

                              var10 = var15 + 1;
                              var22[var15] = var37;
                              char var29;
                              if(var26 == 44) {
                                 var5 = this.bp + var7;
                                 if(var5 >= this.len) {
                                    var26 = 26;
                                 } else {
                                    var26 = this.text.charAt(var5);
                                 }

                                 ++var7;
                                 var29 = var26;
                                 var5 = var7;
                              } else {
                                 if(var26 == 93) {
                                    var5 = this.bp;
                                    var6 = var7 + 1;
                                    var5 += var7;
                                    if(var5 >= this.len) {
                                       var26 = 26;
                                    } else {
                                       var26 = this.text.charAt(var5);
                                    }

                                    if(var10 != var22.length) {
                                       var23 = new float[var10][];
                                       System.arraycopy(var22, 0, var23, 0, var10);
                                       var22 = var23;
                                    }

                                    if(var26 == 44) {
                                       this.bp += var6 - 1;
                                       this.next();
                                       this.matchStat = 3;
                                       this.token = 16;
                                       return var22;
                                    }

                                    if(var26 == 125) {
                                       var7 = this.bp;
                                       var5 = var6 + 1;
                                       var29 = this.charAt(var7 + var6);
                                       if(var29 == 44) {
                                          this.token = 16;
                                          this.bp += var5 - 1;
                                          this.next();
                                       } else if(var29 == 93) {
                                          this.token = 15;
                                          this.bp += var5 - 1;
                                          this.next();
                                       } else if(var29 == 125) {
                                          this.token = 13;
                                          this.bp += var5 - 1;
                                          this.next();
                                       } else {
                                          if(var29 != 26) {
                                             this.matchStat = -1;
                                             return (float[][])null;
                                          }

                                          this.bp += var5 - 1;
                                          this.token = 20;
                                          this.ch = 26;
                                       }

                                       this.matchStat = 4;
                                       return var22;
                                    }

                                    this.matchStat = -1;
                                    return (float[][])null;
                                 }

                                 var29 = var26;
                                 var5 = var7;
                              }

                              var24 = null;
                              var28 = var29;
                              var6 = var5;
                              continue label250;
                           }

                           var8 = var13;
                        }

                        var39 = null;
                        var7 = var11;
                        var5 = var6;
                        var10 = var15;
                        var23 = var38;
                        break;
                     }

                     var30 = var30 * 10 + (var13 - 48);
                     var7 = var12;
                  }
               }
            }
         }
      }
   }

   public int scanFieldInt(long var1) {
      this.matchStat = 0;
      int var4 = this.matchFieldHash(var1);
      if(var4 == 0) {
         return 0;
      } else {
         int var5 = this.bp;
         int var7 = var4 + 1;
         var4 += var5;
         var5 = this.len;
         char var3 = 26;
         char var10;
         if(var4 >= var5) {
            var10 = 26;
         } else {
            var10 = this.text.charAt(var4);
         }

         boolean var6;
         if(var10 == 34) {
            var6 = true;
         } else {
            var6 = false;
         }

         var4 = var7;
         boolean var8 = var6;
         if(var6) {
            var4 = this.bp + var7;
            if(var4 >= this.len) {
               var10 = 26;
            } else {
               var10 = this.text.charAt(var4);
            }

            var4 = var7 + 1;
            var8 = true;
         }

         boolean var12;
         if(var10 == 45) {
            var12 = true;
         } else {
            var12 = false;
         }

         int var11 = var4;
         if(var12) {
            var5 = this.bp + var4;
            if(var5 >= this.len) {
               var10 = 26;
            } else {
               var10 = this.text.charAt(var5);
            }

            var11 = var4 + 1;
         }

         if(var10 >= 48 && var10 <= 57) {
            var4 = var10 - 48;
            var5 = var11;

            while(true) {
               int var9 = this.bp;
               var11 = var5 + 1;
               var5 += var9;
               if(var5 >= this.len) {
                  var10 = 26;
               } else {
                  var10 = this.text.charAt(var5);
               }

               if(var10 < 48 || var10 > 57) {
                  if(var10 == 46) {
                     this.matchStat = -1;
                     return 0;
                  } else {
                     int var13;
                     if(var10 == 34) {
                        if(!var8) {
                           this.matchStat = -1;
                           return 0;
                        }

                        var13 = this.bp;
                        var5 = var11 + 1;
                        var11 += var13;
                        if(var11 >= this.len) {
                           byte var14 = 26;
                           var11 = var5;
                           var10 = (char)var14;
                        } else {
                           char var15 = this.text.charAt(var11);
                           var11 = var5;
                           var10 = var15;
                        }
                     }

                     if(var4 < 0) {
                        this.matchStat = -1;
                        return 0;
                     } else {
                        for(; var10 != 44; ++var11) {
                           if(var10 > 32 || var10 != 32 && var10 != 10 && var10 != 13 && var10 != 9 && var10 != 12 && var10 != 8) {
                              if(var10 == 125) {
                                 var13 = this.bp;
                                 var5 = var11 + 1;
                                 char var16 = this.charAt(var13 + var11);
                                 if(var16 == 44) {
                                    this.token = 16;
                                    this.bp += var5 - 1;
                                    var5 = this.bp + 1;
                                    this.bp = var5;
                                    if(var5 < this.len) {
                                       var3 = this.text.charAt(var5);
                                    }

                                    this.ch = var3;
                                 } else if(var16 == 93) {
                                    this.token = 15;
                                    this.bp += var5 - 1;
                                    var5 = this.bp + 1;
                                    this.bp = var5;
                                    if(var5 < this.len) {
                                       var3 = this.text.charAt(var5);
                                    }

                                    this.ch = var3;
                                 } else if(var16 == 125) {
                                    this.token = 13;
                                    this.bp += var5 - 1;
                                    var5 = this.bp + 1;
                                    this.bp = var5;
                                    if(var5 < this.len) {
                                       var3 = this.text.charAt(var5);
                                    }

                                    this.ch = var3;
                                 } else {
                                    if(var16 != 26) {
                                       this.matchStat = -1;
                                       return 0;
                                    }

                                    this.token = 20;
                                    this.bp += var5 - 1;
                                    this.ch = 26;
                                 }

                                 this.matchStat = 4;
                                 var5 = var4;
                                 if(var12) {
                                    var5 = -var4;
                                 }

                                 return var5;
                              } else {
                                 this.matchStat = -1;
                                 return 0;
                              }
                           }

                           var5 = this.bp + var11;
                           if(var5 >= this.len) {
                              var10 = 26;
                           } else {
                              var10 = this.text.charAt(var5);
                           }
                        }

                        this.bp += var11 - 1;
                        var5 = this.bp + 1;
                        this.bp = var5;
                        if(var5 < this.len) {
                           var3 = this.text.charAt(var5);
                        }

                        this.ch = var3;
                        this.matchStat = 3;
                        this.token = 16;
                        var5 = var4;
                        if(var12) {
                           var5 = -var4;
                        }

                        return var5;
                     }
                  }
               }

               var4 = var4 * 10 + (var10 - 48);
               var5 = var11;
            }
         } else {
            this.matchStat = -1;
            return 0;
         }
      }
   }

   public final int[] scanFieldIntArray(long var1) {
      this.matchStat = 0;
      int var3 = this.matchFieldHash(var1);
      if(var3 == 0) {
         return null;
      } else {
         int var4 = this.bp;
         int var5 = var3 + 1;
         var3 += var4;
         char var12;
         if(var3 >= this.len) {
            var12 = 26;
         } else {
            var12 = this.text.charAt(var3);
         }

         if(var12 != 91) {
            this.matchStat = -1;
            return null;
         } else {
            var3 = this.bp;
            var4 = var5 + 1;
            var3 += var5;
            if(var3 >= this.len) {
               var12 = 26;
            } else {
               var12 = this.text.charAt(var3);
            }

            int[] var10 = new int[16];
            int var7;
            int[] var11;
            char var13;
            if(var12 == 93) {
               var3 = this.bp + var4;
               if(var3 >= this.len) {
                  var12 = 26;
               } else {
                  var12 = this.text.charAt(var3);
               }

               ++var4;
               var7 = 0;
            } else {
               var5 = 0;
               var11 = var10;

               while(true) {
                  int var6;
                  boolean var8;
                  if(var12 == 45) {
                     var3 = this.bp;
                     var6 = var4 + 1;
                     var3 += var4;
                     if(var3 >= this.len) {
                        var12 = 26;
                     } else {
                        var12 = this.text.charAt(var3);
                     }

                     var8 = true;
                     var4 = var6;
                  } else {
                     var8 = false;
                  }

                  int var9;
                  label110: {
                     Object var14;
                     if(var12 >= 48) {
                        if(var12 <= 57) {
                           var3 = var12 - 48;

                           while(true) {
                              var7 = this.bp;
                              var6 = var4 + 1;
                              var4 += var7;
                              if(var4 >= this.len) {
                                 var13 = 26;
                              } else {
                                 var13 = this.text.charAt(var4);
                              }

                              if(var13 < 48 || var13 > 57) {
                                 var10 = var11;
                                 if(var5 >= var11.length) {
                                    var10 = new int[var11.length * 3 / 2];
                                    System.arraycopy(var11, 0, var10, 0, var5);
                                 }

                                 var7 = var5 + 1;
                                 var9 = var3;
                                 if(var8) {
                                    var9 = -var3;
                                 }
                                 break label110;
                              }

                              var3 = var3 * 10 + (var13 - 48);
                              var4 = var6;
                           }
                        }

                        var14 = null;
                     } else {
                        var14 = null;
                     }

                     this.matchStat = -1;
                     return (int[])var14;
                  }

                  var10[var5] = var9;
                  if(var13 == 44) {
                     var3 = this.bp + var6;
                     if(var3 >= this.len) {
                        var12 = 26;
                     } else {
                        var12 = this.text.charAt(var3);
                     }

                     var5 = var6 + 1;
                  } else {
                     var12 = var13;
                     var5 = var6;
                     if(var13 == 93) {
                        var3 = this.bp;
                        var4 = var6 + 1;
                        var3 += var6;
                        if(var3 >= this.len) {
                           var12 = 26;
                        } else {
                           var12 = this.text.charAt(var3);
                        }
                        break;
                     }
                  }

                  var4 = var5;
                  var5 = var7;
                  var11 = var10;
               }
            }

            var11 = var10;
            if(var7 != var10.length) {
               var11 = new int[var7];
               System.arraycopy(var10, 0, var11, 0, var7);
            }

            if(var12 == 44) {
               this.bp += var4 - 1;
               this.next();
               this.matchStat = 3;
               this.token = 16;
               return var11;
            } else if(var12 == 125) {
               var5 = this.bp;
               var3 = var4 + 1;
               var13 = this.charAt(var5 + var4);
               if(var13 == 44) {
                  this.token = 16;
                  this.bp += var3 - 1;
                  this.next();
               } else if(var13 == 93) {
                  this.token = 15;
                  this.bp += var3 - 1;
                  this.next();
               } else if(var13 == 125) {
                  this.token = 13;
                  this.bp += var3 - 1;
                  this.next();
               } else {
                  if(var13 != 26) {
                     this.matchStat = -1;
                     return null;
                  }

                  this.bp += var3 - 1;
                  this.token = 20;
                  this.ch = 26;
               }

               this.matchStat = 4;
               return var11;
            } else {
               this.matchStat = -1;
               return null;
            }
         }
      }
   }

   public long scanFieldLong(long var1) {
      boolean var7 = false;
      this.matchStat = 0;
      int var4 = this.matchFieldHash(var1);
      if(var4 == 0) {
         return 0L;
      } else {
         int var5 = this.bp;
         int var6 = var4 + 1;
         var4 += var5;
         char var12;
         if(var4 >= this.len) {
            var12 = 26;
         } else {
            var12 = this.text.charAt(var4);
         }

         boolean var8;
         if(var12 == 34) {
            var8 = true;
         } else {
            var8 = false;
         }

         var4 = var6;
         if(var8) {
            var4 = this.bp + var6;
            if(var4 >= this.len) {
               var12 = 26;
            } else {
               var12 = this.text.charAt(var4);
            }

            var4 = var6 + 1;
         }

         if(var12 == 45) {
            var7 = true;
         }

         var6 = var4;
         if(var7) {
            var5 = this.bp + var4;
            if(var5 >= this.len) {
               var12 = 26;
            } else {
               var12 = this.text.charAt(var5);
            }

            var6 = var4 + 1;
         }

         if(var12 >= 48 && var12 <= 57) {
            var1 = (long)(var12 - 48);

            while(true) {
               var4 = this.bp;
               var5 = var6 + 1;
               var4 += var6;
               char var13;
               if(var4 >= this.len) {
                  var13 = 26;
               } else {
                  var13 = this.text.charAt(var4);
               }

               long var10;
               if(var13 < 48 || var13 > 57) {
                  if(var13 == 46) {
                     this.matchStat = -1;
                     return 0L;
                  } else {
                     char var9 = var13;
                     var6 = var5;
                     if(var13 == 34) {
                        if(!var8) {
                           this.matchStat = -1;
                           return 0L;
                        }

                        var4 = this.bp + var5;
                        if(var4 >= this.len) {
                           var13 = 26;
                        } else {
                           var13 = this.text.charAt(var4);
                        }

                        var6 = var5 + 1;
                        var9 = var13;
                     }

                     if(var1 < 0L) {
                        this.matchStat = -1;
                        return 0L;
                     } else {
                        char var3;
                        if(var9 == 44) {
                           this.bp += var6 - 1;
                           var4 = this.bp + 1;
                           this.bp = var4;
                           if(var4 >= this.len) {
                              var3 = 26;
                           } else {
                              var3 = this.text.charAt(var4);
                           }

                           this.ch = var3;
                           this.matchStat = 3;
                           this.token = 16;
                           var10 = var1;
                           if(var7) {
                              var10 = -var1;
                           }

                           return var10;
                        } else if(var9 == 125) {
                           var5 = this.bp;
                           var4 = var6 + 1;
                           var12 = this.charAt(var5 + var6);
                           if(var12 == 44) {
                              this.token = 16;
                              this.bp += var4 - 1;
                              var4 = this.bp + 1;
                              this.bp = var4;
                              if(var4 >= this.len) {
                                 var3 = 26;
                              } else {
                                 var3 = this.text.charAt(var4);
                              }

                              this.ch = var3;
                           } else if(var12 == 93) {
                              this.token = 15;
                              this.bp += var4 - 1;
                              var4 = this.bp + 1;
                              this.bp = var4;
                              if(var4 >= this.len) {
                                 var3 = 26;
                              } else {
                                 var3 = this.text.charAt(var4);
                              }

                              this.ch = var3;
                           } else if(var12 == 125) {
                              this.token = 13;
                              this.bp += var4 - 1;
                              var4 = this.bp + 1;
                              this.bp = var4;
                              if(var4 >= this.len) {
                                 var3 = 26;
                              } else {
                                 var3 = this.text.charAt(var4);
                              }

                              this.ch = var3;
                           } else {
                              if(var12 != 26) {
                                 this.matchStat = -1;
                                 return 0L;
                              }

                              this.token = 20;
                              this.bp += var4 - 1;
                              this.ch = 26;
                           }

                           this.matchStat = 4;
                           var10 = var1;
                           if(var7) {
                              var10 = -var1;
                           }

                           return var10;
                        } else {
                           this.matchStat = -1;
                           return 0L;
                        }
                     }
                  }
               }

               var10 = (long)(var13 - 48);
               var1 = var1 * 10L + var10;
               var6 = var5;
            }
         } else {
            this.matchStat = -1;
            return 0L;
         }
      }
   }

   public String scanFieldString(long var1) {
      this.matchStat = 0;
      int var4 = this.matchFieldHash(var1);
      if(var4 == 0) {
         return null;
      } else {
         int var5 = this.bp;
         int var8 = var4 + 1;
         var4 += var5;
         StringBuilder var15;
         if(var4 >= this.len) {
            var15 = new StringBuilder();
            var15.append("unclosed str, ");
            var15.append(this.info());
            throw new JSONException(var15.toString());
         } else if(this.text.charAt(var4) != 34) {
            this.matchStat = -1;
            return this.stringDefaultValue;
         } else {
            int var9 = this.bp + var8;
            var4 = this.text.indexOf(34, var9);
            if(var4 == -1) {
               var15 = new StringBuilder();
               var15.append("unclosed str, ");
               var15.append(this.info());
               throw new JSONException(var15.toString());
            } else {
               String var11;
               if(V6) {
                  var11 = this.text.substring(var9, var4);
               } else {
                  var5 = var4 - var9;
                  var11 = new String(this.sub_chars(this.bp + var8, var5), 0, var5);
               }

               var5 = var4;
               String var10 = var11;
               if(var11.indexOf(92) != -1) {
                  boolean var14 = false;

                  while(true) {
                     int var6 = var4 - 1;

                     int var7;
                     for(var7 = 0; var6 >= 0 && this.text.charAt(var6) == 92; var14 = true) {
                        ++var7;
                        --var6;
                     }

                     if(var7 % 2 == 0) {
                        var6 = var4 - var9;
                        char[] var12 = this.sub_chars(this.bp + var8, var6);
                        if(var14) {
                           var10 = readString(var12, var6);
                           var5 = var4;
                        } else {
                           var11 = new String(var12, 0, var6);
                           var5 = var4;
                           var10 = var11;
                           if(var11.indexOf(92) != -1) {
                              var10 = readString(var12, var6);
                              var5 = var4;
                           }
                        }
                        break;
                     }

                     var4 = this.text.indexOf(34, var4 + 1);
                  }
               }

               ++var5;
               var4 = this.len;
               char var3 = 26;
               char var13;
               if(var5 >= var4) {
                  var13 = 26;
               } else {
                  var13 = this.text.charAt(var5);
               }

               if(var13 == 44) {
                  this.bp = var5;
                  var4 = this.bp + 1;
                  this.bp = var4;
                  if(var4 < this.len) {
                     var3 = this.text.charAt(var4);
                  }

                  this.ch = var3;
                  this.matchStat = 3;
                  this.token = 16;
                  return var10;
               } else if(var13 == 125) {
                  ++var5;
                  if(var5 >= this.len) {
                     var13 = 26;
                  } else {
                     var13 = this.text.charAt(var5);
                  }

                  if(var13 == 44) {
                     this.token = 16;
                     this.bp = var5;
                     this.next();
                  } else if(var13 == 93) {
                     this.token = 15;
                     this.bp = var5;
                     this.next();
                  } else if(var13 == 125) {
                     this.token = 13;
                     this.bp = var5;
                     this.next();
                  } else {
                     if(var13 != 26) {
                        this.matchStat = -1;
                        return this.stringDefaultValue;
                     }

                     this.token = 20;
                     this.bp = var5;
                     this.ch = 26;
                  }

                  this.matchStat = 4;
                  return var10;
               } else {
                  this.matchStat = -1;
                  return this.stringDefaultValue;
               }
            }
         }
      }
   }

   public long scanFieldSymbol(long var1) {
      this.matchStat = 0;
      int var4 = this.matchFieldHash(var1);
      if(var4 == 0) {
         return 0L;
      } else {
         int var6 = this.bp;
         int var5 = var4 + 1;
         var4 += var6;
         var6 = this.len;
         char var3 = 26;
         char var7;
         if(var4 >= var6) {
            var7 = 26;
         } else {
            var7 = this.text.charAt(var4);
         }

         if(var7 != 34) {
            this.matchStat = -1;
            return 0L;
         } else {
            var1 = -3750763034362895579L;
            var4 = this.bp;
            var4 = var5;

            while(true) {
               var6 = this.bp;
               var5 = var4 + 1;
               var4 += var6;
               if(var4 >= this.len) {
                  var7 = 26;
               } else {
                  var7 = this.text.charAt(var4);
               }

               if(var7 == 34) {
                  var4 = this.bp;
                  var6 = var5 + 1;
                  var4 += var5;
                  if(var4 >= this.len) {
                     var7 = 26;
                  } else {
                     var7 = this.text.charAt(var4);
                  }

                  if(var7 == 44) {
                     this.bp += var6 - 1;
                     var4 = this.bp + 1;
                     this.bp = var4;
                     if(var4 < this.len) {
                        var3 = this.text.charAt(var4);
                     }

                     this.ch = var3;
                     this.matchStat = 3;
                     return var1;
                  }

                  if(var7 == 125) {
                     var4 = this.bp;
                     var5 = var6 + 1;
                     var4 += var6;
                     if(var4 >= this.len) {
                        var7 = 26;
                     } else {
                        var7 = this.text.charAt(var4);
                     }

                     if(var7 == 44) {
                        this.token = 16;
                        this.bp += var5 - 1;
                        this.next();
                     } else if(var7 == 93) {
                        this.token = 15;
                        this.bp += var5 - 1;
                        this.next();
                     } else if(var7 == 125) {
                        this.token = 13;
                        this.bp += var5 - 1;
                        this.next();
                     } else {
                        if(var7 != 26) {
                           this.matchStat = -1;
                           return 0L;
                        }

                        this.token = 20;
                        this.bp += var5 - 1;
                        this.ch = 26;
                     }

                     this.matchStat = 4;
                     return var1;
                  }

                  this.matchStat = -1;
                  return 0L;
               }

               var1 = 1099511628211L * (var1 ^ (long)var7);
               if(var7 == 92) {
                  this.matchStat = -1;
                  return 0L;
               }

               var4 = var5;
            }
         }
      }
   }

   public boolean scanISO8601DateIfMatch(boolean var1) {
      return this.scanISO8601DateIfMatch(var1, this.len - this.bp);
   }

   public boolean scanISO8601DateIfMatch(boolean var1, int var2) {
      char var18;
      char var19;
      char var22;
      int var34;
      int var35;
      int var40;
      if(!var1 && var2 > 13) {
         var18 = this.charAt(this.bp);
         var19 = this.charAt(this.bp + 1);
         char var20 = this.charAt(this.bp + 2);
         char var21 = this.charAt(this.bp + 3);
         var22 = this.charAt(this.bp + 4);
         char var23 = this.charAt(this.bp + 5);
         char var24 = this.charAt(this.bp + var2 - 1);
         char var25 = this.charAt(this.bp + var2 - 2);
         if(var18 == 47 && var19 == 68 && var20 == 97 && var21 == 116 && var22 == 101 && var23 == 40 && var24 == 47 && var25 == 41) {
            var40 = 6;

            for(var34 = -1; var40 < var2; var34 = var35) {
               var21 = this.charAt(this.bp + var40);
               if(var21 == 43) {
                  var35 = var40;
               } else {
                  if(var21 < 48) {
                     break;
                  }

                  var35 = var34;
                  if(var21 > 57) {
                     break;
                  }
               }

               ++var40;
            }

            if(var34 == -1) {
               return false;
            }

            var2 = this.bp + 6;
            long var26 = Long.parseLong(this.subString(var2, var34 - var2));
            this.calendar = Calendar.getInstance(this.timeZone, this.locale);
            this.calendar.setTimeInMillis(var26);
            this.token = 5;
            return true;
         }
      }

      char var3;
      char var4;
      char var5;
      char var6;
      char var7;
      char var8;
      char var9;
      char var10;
      char var11;
      char var29;
      if(var2 != 8 && var2 != 14) {
         label470: {
            if(var2 == 16) {
               var18 = this.charAt(this.bp + 10);
               if(var18 == 84 || var18 == 32) {
                  break label470;
               }
            }

            if(var2 != 17 || this.charAt(this.bp + 6) == 45) {
               if(var2 < 9) {
                  return false;
               }

               byte var33;
               label502: {
                  var4 = this.charAt(this.bp);
                  var3 = this.charAt(this.bp + 1);
                  var6 = this.charAt(this.bp + 2);
                  var5 = this.charAt(this.bp + 3);
                  char var17 = this.charAt(this.bp + 4);
                  var9 = this.charAt(this.bp + 5);
                  var7 = var9;
                  char var15 = this.charAt(this.bp + 6);
                  var11 = this.charAt(this.bp + 7);
                  char var14 = var11;
                  var8 = this.charAt(this.bp + 8);
                  char var16 = this.charAt(this.bp + 9);
                  char var12;
                  char var13;
                  if((var17 != 45 || var11 != 45) && (var17 != 47 || var11 != 47)) {
                     label487: {
                        byte var32;
                        label390: {
                           label494: {
                              if(var17 == 45 && var15 == 45) {
                                 if(var8 == 32) {
                                    var33 = 8;
                                    break label390;
                                 }

                                 var33 = 9;
                              } else {
                                 if(var6 == 46 && var9 == 46 || var6 == 45 && var9 == 45) {
                                    var9 = var4;
                                    var11 = var3;
                                    var3 = var5;
                                    var4 = var17;
                                    var33 = 10;
                                    var5 = var15;
                                    var6 = var14;
                                    var7 = var8;
                                    var8 = var16;
                                    var10 = var11;
                                    break label502;
                                 }

                                 if(var17 != 24180 && var17 != '\ub144') {
                                    return false;
                                 }

                                 if(var11 == 26376 || var11 == '\uc6d4') {
                                    if(var16 != 26085 && var16 != '\uc77c') {
                                       if(this.charAt(this.bp + 10) != 26085 && this.charAt(this.bp + 10) != '\uc77c') {
                                          return false;
                                       }

                                       var33 = 11;
                                       break label487;
                                    }

                                    var32 = 48;
                                    var33 = 10;
                                    var10 = var9;
                                    var7 = var15;
                                    var9 = (char)var32;
                                    break label494;
                                 }

                                 if(var15 != 26376 && var15 != '\uc6d4') {
                                    return false;
                                 }

                                 if(var8 == 26085 || var8 == '\uc77c') {
                                    var33 = 10;
                                    break label390;
                                 }

                                 if(var16 != 26085 && var16 != '\uc77c') {
                                    return false;
                                 }

                                 var33 = 10;
                              }

                              var7 = var9;
                              var10 = 48;
                              var9 = var11;
                           }

                           var11 = var8;
                           var8 = var5;
                           var12 = var3;
                           var5 = var4;
                           var13 = var6;
                           var3 = var10;
                           var4 = var7;
                           var6 = var12;
                           var7 = var13;
                           var10 = var11;
                           break label502;
                        }

                        var10 = var11;
                        var32 = 48;
                        var8 = var5;
                        var7 = var6;
                        var5 = var4;
                        var4 = var9;
                        byte var31 = 48;
                        var6 = var3;
                        var3 = (char)var31;
                        var9 = (char)var32;
                        break label502;
                     }
                  } else {
                     var33 = 10;
                  }

                  var10 = var16;
                  var9 = var8;
                  var8 = var3;
                  var13 = var4;
                  var11 = var6;
                  var12 = var5;
                  var3 = var7;
                  var4 = var15;
                  var5 = var13;
                  var6 = var8;
                  var7 = var11;
                  var8 = var12;
               }

               if(!checkDate(var5, var6, var7, var8, var3, var4, var9, var10)) {
                  return false;
               }

               this.setCalendar(var5, var6, var7, var8, var3, var4, var9, var10);
               var3 = this.charAt(this.bp + var33);
               if(var3 != 84 && (var3 != 32 || var1)) {
                  if(var3 != 34 && var3 != 26 && var3 != 26085 && var3 != '\uc77c') {
                     if(var3 != 43 && var3 != 45) {
                        return false;
                     }

                     if(this.len == var33 + 6) {
                        if(this.charAt(this.bp + var33 + 3) == 58 && this.charAt(this.bp + var33 + 4) == 48) {
                           if(this.charAt(this.bp + var33 + 5) != 48) {
                              return false;
                           }

                           this.setTime('0', '0', '0', '0', '0', '0');
                           this.calendar.set(14, 0);
                           this.setTimeZone(var3, this.charAt(this.bp + var33 + 1), this.charAt(this.bp + var33 + 2));
                           return true;
                        }

                        return false;
                     }

                     return false;
                  }

                  this.calendar.set(11, 0);
                  this.calendar.set(12, 0);
                  this.calendar.set(13, 0);
                  this.calendar.set(14, 0);
                  var2 = this.bp + var33;
                  this.bp = var2;
                  this.ch = this.charAt(var2);
                  this.token = 5;
                  return true;
               }

               var35 = var33 + 9;
               if(var2 < var35) {
                  return false;
               }

               if(this.charAt(this.bp + var33 + 3) != 58) {
                  return false;
               }

               if(this.charAt(this.bp + var33 + 6) != 58) {
                  return false;
               }

               var3 = this.charAt(this.bp + var33 + 1);
               var4 = this.charAt(this.bp + var33 + 2);
               var5 = this.charAt(this.bp + var33 + 4);
               var6 = this.charAt(this.bp + var33 + 5);
               var7 = this.charAt(this.bp + var33 + 7);
               var8 = this.charAt(this.bp + var33 + 8);
               if(!checkTime(var3, var4, var5, var6, var7, var8)) {
                  return false;
               }

               this.setTime(var3, var4, var5, var6, var7, var8);
               var19 = this.charAt(this.bp + var33 + 9);
               String[] var28;
               TimeZone var41;
               if(var19 == 46) {
                  var35 = var33 + 11;
                  if(var2 < var35) {
                     return false;
                  }

                  var19 = this.charAt(this.bp + var33 + 10);
                  if(var19 >= 48) {
                     if(var19 > 57) {
                        return false;
                     }

                     byte var30;
                     label332: {
                        var34 = var19 - 48;
                        if(var2 > var35) {
                           var29 = this.charAt(this.bp + var33 + 11);
                           if(var29 >= 48 && var29 <= 57) {
                              var34 = var34 * 10 + (var29 - 48);
                              var30 = 2;
                              break label332;
                           }
                        }

                        var30 = 1;
                     }

                     byte var36 = var30;
                     int var38 = var34;
                     if(var30 == 2) {
                        var22 = this.charAt(this.bp + var33 + 12);
                        var36 = var30;
                        var38 = var34;
                        if(var22 >= 48) {
                           var36 = var30;
                           var38 = var34;
                           if(var22 <= 57) {
                              var38 = var34 * 10 + (var22 - 48);
                              var36 = 3;
                           }
                        }
                     }

                     this.calendar.set(14, var38);
                     var3 = this.charAt(this.bp + var33 + 10 + var36);
                     if(var3 != 43 && var3 != 45) {
                        if(var3 == 90) {
                           if(this.calendar.getTimeZone().getRawOffset() != 0) {
                              var28 = TimeZone.getAvailableIDs(0);
                              if(var28.length > 0) {
                                 var41 = TimeZone.getTimeZone(var28[0]);
                                 this.calendar.setTimeZone(var41);
                              }
                           }

                           var30 = 1;
                        } else {
                           var30 = 0;
                        }
                     } else {
                        var4 = this.charAt(this.bp + var33 + 10 + var36 + 1);
                        if(var4 < 48) {
                           return false;
                        }

                        if(var4 > 49) {
                           return false;
                        }

                        var5 = this.charAt(this.bp + var33 + 10 + var36 + 2);
                        if(var5 < 48) {
                           return false;
                        }

                        if(var5 > 57) {
                           return false;
                        }

                        var29 = this.charAt(this.bp + var33 + 10 + var36 + 3);
                        if(var29 == 58) {
                           if(this.charAt(this.bp + var33 + 10 + var36 + 4) != 48) {
                              return false;
                           }

                           if(this.charAt(this.bp + var33 + 10 + var36 + 5) != 48) {
                              return false;
                           }

                           var30 = 6;
                        } else if(var29 == 48) {
                           if(this.charAt(this.bp + var33 + 10 + var36 + 4) != 48) {
                              return false;
                           }

                           var30 = 5;
                        } else {
                           var30 = 3;
                        }

                        this.setTimeZone(var3, var4, var5);
                     }

                     var34 = this.bp;
                     var2 = var33 + 10 + var36 + var30;
                     var18 = this.charAt(var34 + var2);
                     if(var18 != 26 && var18 != 34) {
                        return false;
                     }

                     var2 += this.bp;
                     this.bp = var2;
                     this.ch = this.charAt(var2);
                     this.token = 5;
                     return true;
                  }

                  return false;
               }

               this.calendar.set(14, 0);
               var2 = this.bp + var35;
               this.bp = var2;
               this.ch = this.charAt(var2);
               this.token = 5;
               if(var19 == 90 && this.calendar.getTimeZone().getRawOffset() != 0) {
                  var28 = TimeZone.getAvailableIDs(0);
                  if(var28.length > 0) {
                     var41 = TimeZone.getTimeZone(var28[0]);
                     this.calendar.setTimeZone(var41);
                  }
               }

               return true;
            }
         }
      }

      var35 = 0;
      if(var1) {
         return false;
      } else {
         var8 = this.charAt(this.bp);
         var9 = this.charAt(this.bp + 1);
         var10 = this.charAt(this.bp + 2);
         var11 = this.charAt(this.bp + 3);
         var5 = this.charAt(this.bp + 4);
         var6 = this.charAt(this.bp + 5);
         var4 = this.charAt(this.bp + 6);
         var7 = this.charAt(this.bp + 7);
         var3 = this.charAt(this.bp + 8);
         boolean var37;
         if(var5 == 45 && var7 == 45) {
            var37 = true;
         } else {
            var37 = false;
         }

         boolean var39;
         if(var37 && var2 == 16) {
            var39 = true;
         } else {
            var39 = false;
         }

         if(var37 && var2 == 17) {
            var37 = true;
         } else {
            var37 = false;
         }

         if(var37 || var39) {
            var7 = this.charAt(this.bp + 9);
            var5 = var6;
            var6 = var4;
            var4 = var3;
         }

         if(!checkDate(var8, var9, var10, var11, var5, var6, var4, var7)) {
            return false;
         } else {
            this.setCalendar(var8, var9, var10, var11, var5, var6, var4, var7);
            if(var2 != 8) {
               var9 = this.charAt(this.bp + 9);
               var10 = this.charAt(this.bp + 10);
               var4 = this.charAt(this.bp + 11);
               var5 = this.charAt(this.bp + 12);
               var7 = this.charAt(this.bp + 13);
               if((!var37 || var10 != 84 || var7 != 58 || this.charAt(this.bp + 16) != 90) && (!var39 || var10 != 32 && var10 != 84 || var7 != 58)) {
                  var8 = var5;
                  var6 = var4;
                  var4 = var3;
                  var5 = var10;
                  var3 = var9;
               } else {
                  var9 = this.charAt(this.bp + 14);
                  var6 = this.charAt(this.bp + 15);
                  var3 = var5;
                  var7 = 48;
                  var8 = 48;
                  var5 = var9;
               }

               if(!checkTime(var4, var3, var5, var6, var8, var7)) {
                  return false;
               }

               if(var2 == 17 && !var37) {
                  var29 = this.charAt(this.bp + 14);
                  var18 = this.charAt(this.bp + 15);
                  var19 = this.charAt(this.bp + 16);
                  if(var29 < 48) {
                     return false;
                  }

                  if(var29 > 57) {
                     return false;
                  }

                  if(var18 < 48) {
                     return false;
                  }

                  if(var18 > 57) {
                     return false;
                  }

                  if(var19 < 48) {
                     return false;
                  }

                  if(var19 > 57) {
                     return false;
                  }

                  var2 = (var29 - 48) * 100 + (var18 - 48) * 10 + (var19 - 48);
               } else {
                  var2 = 0;
               }

               var40 = (var5 - 48) * 10 + (var6 - 48);
               var34 = (var8 - 48) * 10 + (var7 - 48);
               var35 = var3 - 48 + (var4 - 48) * 10;
            } else {
               var40 = 0;
               var34 = 0;
               var2 = 0;
            }

            this.calendar.set(11, var35);
            this.calendar.set(12, var40);
            this.calendar.set(13, var34);
            this.calendar.set(14, var2);
            this.token = 5;
            return true;
         }
      }
   }

   public final long scanLongValue() {
      this.np = 0;
      long var6;
      StringBuilder var10;
      boolean var11;
      if(this.ch == 45) {
         var6 = Long.MIN_VALUE;
         ++this.np;
         int var2 = this.bp + 1;
         this.bp = var2;
         if(var2 >= this.len) {
            var10 = new StringBuilder();
            var10.append("syntax error, ");
            var10.append(this.info());
            throw new JSONException(var10.toString());
         }

         this.ch = this.text.charAt(var2);
         var11 = true;
      } else {
         var6 = -9223372036854775807L;
         var11 = false;
      }

      long var4;
      long var8;
      for(var4 = 0L; this.ch >= 48 && this.ch <= 57; var4 -= var8) {
         char var3 = this.ch;
         if(var4 < -922337203685477580L) {
            var10 = new StringBuilder();
            var10.append("error long value, ");
            var10.append(var4);
            var10.append(", ");
            var10.append(this.info());
            throw new JSONException(var10.toString());
         }

         var4 *= 10L;
         var8 = (long)(var3 - 48);
         if(var4 < var6 + var8) {
            var10 = new StringBuilder();
            var10.append("error long value, ");
            var10.append(var4);
            var10.append(", ");
            var10.append(this.info());
            throw new JSONException(var10.toString());
         }

         ++this.np;
         int var12 = this.bp + 1;
         this.bp = var12;
         char var1;
         if(var12 >= this.len) {
            var1 = 26;
         } else {
            var1 = this.text.charAt(var12);
         }

         this.ch = var1;
      }

      var6 = var4;
      if(!var11) {
         var6 = -var4;
      }

      return var6;
   }

   public final void scanNumber() {
      this.np = this.bp;
      this.exp = false;
      char var1;
      int var2;
      if(this.ch == 45) {
         ++this.sp;
         var2 = this.bp + 1;
         this.bp = var2;
         if(var2 >= this.len) {
            var1 = 26;
         } else {
            var1 = this.text.charAt(var2);
         }

         this.ch = var1;
      }

      for(; this.ch >= 48 && this.ch <= 57; this.ch = var1) {
         ++this.sp;
         var2 = this.bp + 1;
         this.bp = var2;
         if(var2 >= this.len) {
            var1 = 26;
         } else {
            var1 = this.text.charAt(var2);
         }
      }

      this.isDouble = false;
      if(this.ch == 46) {
         ++this.sp;
         var2 = this.bp + 1;
         this.bp = var2;
         if(var2 >= this.len) {
            var1 = 26;
         } else {
            var1 = this.text.charAt(var2);
         }

         this.ch = var1;

         for(this.isDouble = true; this.ch >= 48 && this.ch <= 57; this.ch = var1) {
            ++this.sp;
            var2 = this.bp + 1;
            this.bp = var2;
            if(var2 >= this.len) {
               var1 = 26;
            } else {
               var1 = this.text.charAt(var2);
            }
         }
      }

      if(this.ch == 76) {
         ++this.sp;
         this.next();
      } else if(this.ch == 83) {
         ++this.sp;
         this.next();
      } else if(this.ch == 66) {
         ++this.sp;
         this.next();
      } else if(this.ch == 70) {
         ++this.sp;
         this.next();
         this.isDouble = true;
      } else if(this.ch == 68) {
         ++this.sp;
         this.next();
         this.isDouble = true;
      } else if(this.ch == 101 || this.ch == 69) {
         ++this.sp;
         var2 = this.bp + 1;
         this.bp = var2;
         if(var2 >= this.len) {
            var1 = 26;
         } else {
            var1 = this.text.charAt(var2);
         }

         this.ch = var1;
         if(this.ch == 43 || this.ch == 45) {
            ++this.sp;
            var2 = this.bp + 1;
            this.bp = var2;
            if(var2 >= this.len) {
               var1 = 26;
            } else {
               var1 = this.text.charAt(var2);
            }

            this.ch = var1;
         }

         for(; this.ch >= 48 && this.ch <= 57; this.ch = var1) {
            ++this.sp;
            var2 = this.bp + 1;
            this.bp = var2;
            if(var2 >= this.len) {
               var1 = 26;
            } else {
               var1 = this.text.charAt(var2);
            }
         }

         if(this.ch == 68 || this.ch == 70) {
            ++this.sp;
            this.next();
         }

         this.exp = true;
         this.isDouble = true;
      }

      if(this.isDouble) {
         this.token = 3;
      } else {
         this.token = 2;
      }
   }

   public final Number scanNumberValue() {
      // $FF: Couldn't be decompiled
   }

   public final void scanString() {
      char var9 = this.ch;
      int var2 = this.bp + 1;
      int var3 = this.text.indexOf(var9, var2);
      if(var3 == -1) {
         StringBuilder var14 = new StringBuilder();
         var14.append("unclosed str, ");
         var14.append(this.info());
         throw new JSONException(var14.toString());
      } else {
         var2 = var3 - var2;
         char[] var12 = this.sub_chars(this.bp + 1, var2);
         byte var6 = 0;

         int var4;
         boolean var10;
         char[] var13;
         for(var10 = false; var2 > 0 && var12[var2 - 1] == 92; var12 = var13) {
            var4 = var2 - 2;

            int var5;
            for(var5 = 1; var4 >= 0 && var12[var4] == 92; --var4) {
               ++var5;
            }

            if(var5 % 2 == 0) {
               break;
            }

            int var7 = this.text.indexOf(var9, var3 + 1);
            var4 = var7 - var3 + var2;
            var13 = var12;
            if(var4 >= var12.length) {
               int var8 = var12.length * 3 / 2;
               var5 = var8;
               if(var8 < var4) {
                  var5 = var4;
               }

               var13 = new char[var5];
               System.arraycopy(var12, 0, var13, 0, var12.length);
            }

            this.text.getChars(var3, var7, var13, var2);
            var3 = var7;
            var2 = var4;
            var10 = true;
         }

         boolean var11 = var10;
         if(!var10) {
            var4 = var6;

            while(true) {
               var11 = var10;
               if(var4 >= var2) {
                  break;
               }

               if(var12[var4] == 92) {
                  var10 = true;
               }

               ++var4;
            }
         }

         this.sbuf = var12;
         this.sp = var2;
         this.np = this.bp;
         this.hasSpecial = var11;
         this.bp = var3 + 1;
         var2 = this.bp;
         char var1;
         if(var2 >= this.len) {
            var1 = 26;
         } else {
            var1 = this.text.charAt(var2);
         }

         this.ch = var1;
         this.token = 4;
      }
   }

   public String scanStringValue(char var1) {
      int var6 = this.bp + 1;
      int var3 = this.text.indexOf(var1, var6);
      if(var3 == -1) {
         StringBuilder var10 = new StringBuilder();
         var10.append("unclosed str, ");
         var10.append(this.info());
         throw new JSONException(var10.toString());
      } else {
         int var4;
         String var7;
         if(V6) {
            var7 = this.text.substring(var6, var3);
         } else {
            var4 = var3 - var6;
            var7 = new String(this.sub_chars(this.bp + 1, var4), 0, var4);
         }

         var4 = var3;
         String var8 = var7;
         int var9;
         if(var7.indexOf(92) != -1) {
            while(true) {
               var4 = var3 - 1;

               int var5;
               for(var5 = 0; var4 >= 0 && this.text.charAt(var4) == 92; --var4) {
                  ++var5;
               }

               if(var5 % 2 == 0) {
                  var9 = var3 - var6;
                  var8 = readString(this.sub_chars(this.bp + 1, var9), var9);
                  var4 = var3;
                  break;
               }

               var3 = this.text.indexOf(var1, var3 + 1);
            }
         }

         this.bp = var4 + 1;
         var9 = this.bp;
         char var2;
         if(var9 >= this.len) {
            var2 = 26;
         } else {
            var2 = this.text.charAt(var9);
         }

         this.ch = var2;
         return var8;
      }
   }

   public final String scanSymbol(SymbolTable var1) {
      while(this.ch == 32 || this.ch == 10 || this.ch == 13 || this.ch == 9 || this.ch == 12 || this.ch == 8) {
         this.next();
      }

      if(this.ch == 34) {
         return this.scanSymbol(var1, '\"');
      } else if(this.ch == 39) {
         return this.scanSymbol(var1, '\'');
      } else if(this.ch == 125) {
         this.next();
         this.token = 13;
         return null;
      } else if(this.ch == 44) {
         this.next();
         this.token = 16;
         return null;
      } else if(this.ch == 26) {
         this.token = 20;
         return null;
      } else {
         return this.scanSymbolUnQuoted(var1);
      }
   }

   public String scanSymbol(SymbolTable var1, char var2) {
      int var4 = this.bp + 1;
      int var6 = this.text.indexOf(var2, var4);
      if(var6 == -1) {
         StringBuilder var14 = new StringBuilder();
         var14.append("unclosed str, ");
         var14.append(this.info());
         throw new JSONException(var14.toString());
      } else {
         int var5 = var6 - var4;
         char[] var10 = this.sub_chars(this.bp + 1, var5);

         int var7;
         char[] var11;
         boolean var15;
         for(var15 = false; var5 > 0 && var10[var5 - 1] == 92; var10 = var11) {
            var7 = var5 - 2;

            int var8;
            for(var8 = 1; var7 >= 0 && var10[var7] == 92; --var7) {
               ++var8;
            }

            if(var8 % 2 == 0) {
               break;
            }

            var8 = this.text.indexOf(var2, var6 + 1);
            var4 = var8 - var6 + var5;
            var11 = var10;
            if(var4 >= var10.length) {
               int var9 = var10.length * 3 / 2;
               var7 = var9;
               if(var9 < var4) {
                  var7 = var4;
               }

               var11 = new char[var7];
               System.arraycopy(var10, 0, var11, 0, var10.length);
            }

            this.text.getChars(var6, var8, var11, var5);
            var6 = var8;
            boolean var16 = true;
            var5 = var4;
            var15 = var16;
         }

         String var12;
         int var13;
         if(!var15) {
            var13 = 0;

            for(var7 = 0; var13 < var5; ++var13) {
               char var17 = var10[var13];
               var7 = var7 * 31 + var17;
               if(var17 == 92) {
                  var15 = true;
               }
            }

            if(var15) {
               var12 = readString(var10, var5);
            } else if(var5 < 20) {
               var12 = var1.addSymbol(var10, 0, var5, var7);
            } else {
               var12 = new String(var10, 0, var5);
            }
         } else {
            var12 = readString(var10, var5);
         }

         this.bp = var6 + 1;
         var13 = this.bp;
         char var3;
         if(var13 >= this.len) {
            var3 = 26;
         } else {
            var3 = this.text.charAt(var13);
         }

         this.ch = var3;
         return var12;
      }
   }

   public final String scanSymbolUnQuoted(SymbolTable var1) {
      char var3 = this.ch;
      boolean var2;
      if(this.ch < firstIdentifierFlags.length && !firstIdentifierFlags[var3]) {
         var2 = false;
      } else {
         var2 = true;
      }

      if(!var2) {
         StringBuilder var4 = new StringBuilder();
         var4.append("illegal identifier : ");
         var4.append(this.ch);
         var4.append(", ");
         var4.append(this.info());
         throw new JSONException(var4.toString());
      } else {
         this.np = this.bp;
         this.sp = 1;
         int var5 = var3;

         while(true) {
            var3 = this.next();
            if(var3 < identifierFlags.length && !identifierFlags[var3]) {
               this.ch = this.charAt(this.bp);
               this.token = 18;
               if(this.sp == 4 && this.text.startsWith("null", this.np)) {
                  return null;
               }

               return var1.addSymbol(this.text, this.np, this.sp, var5);
            }

            var5 = var5 * 31 + var3;
            ++this.sp;
         }
      }
   }

   protected void setTime(char var1, char var2, char var3, char var4, char var5, char var6) {
      this.calendar.set(11, (var1 - 48) * 10 + (var2 - 48));
      this.calendar.set(12, (var3 - 48) * 10 + (var4 - 48));
      this.calendar.set(13, (var5 - 48) * 10 + (var6 - 48));
   }

   protected void setTimeZone(char var1, char var2, char var3) {
      int var6 = ((var2 - 48) * 10 + (var3 - 48)) * 3600 * 1000;
      int var5 = var6;
      if(var1 == 45) {
         var5 = -var6;
      }

      if(this.calendar.getTimeZone().getRawOffset() != var5) {
         String[] var4 = TimeZone.getAvailableIDs(var5);
         if(var4.length > 0) {
            TimeZone var7 = TimeZone.getTimeZone(var4[0]);
            this.calendar.setTimeZone(var7);
         }
      }

   }

   protected void skipComment() {
      this.next();
      if(this.ch != 47) {
         if(this.ch == 42) {
            this.next();

            while(this.ch != 26) {
               if(this.ch == 42) {
                  this.next();
                  if(this.ch == 47) {
                     this.next();
                     return;
                  }
               } else {
                  this.next();
               }
            }

         } else {
            throw new JSONException("invalid comment");
         }
      } else {
         do {
            this.next();
         } while(this.ch != 10);

         this.next();
      }
   }

   final void skipWhitespace() {
      while(true) {
         if(this.ch <= 47) {
            if(this.ch == 32 || this.ch == 13 || this.ch == 10 || this.ch == 9 || this.ch == 12 || this.ch == 8) {
               this.next();
               continue;
            }

            if(this.ch == 47) {
               this.skipComment();
               continue;
            }
         }

         return;
      }
   }

   public final String stringVal() {
      return this.hasSpecial?readString(this.sbuf, this.sp):this.subString(this.np + 1, this.sp);
   }

   final char[] sub_chars(int var1, int var2) {
      if(var2 < this.sbuf.length) {
         this.text.getChars(var1, var2 + var1, this.sbuf, 0);
         return this.sbuf;
      } else {
         char[] var3 = new char[var2];
         this.sbuf = var3;
         this.text.getChars(var1, var2 + var1, var3, 0);
         return var3;
      }
   }

   public final int token() {
      return this.token;
   }
}
