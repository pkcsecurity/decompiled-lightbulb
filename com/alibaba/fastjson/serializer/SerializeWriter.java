package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;

public final class SerializeWriter extends Writer {

   public static final char[] DIGITS;
   static final char[] DigitOnes = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
   static final char[] DigitTens = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'};
   static final char[] ascii_chars = new char[]{'0', '0', '0', '1', '0', '2', '0', '3', '0', '4', '0', '5', '0', '6', '0', '7', '0', '8', '0', '9', '0', 'A', '0', 'B', '0', 'C', '0', 'D', '0', 'E', '0', 'F', '1', '0', '1', '1', '1', '2', '1', '3', '1', '4', '1', '5', '1', '6', '1', '7', '1', '8', '1', '9', '1', 'A', '1', 'B', '1', 'C', '1', 'D', '1', 'E', '1', 'F', '2', '0', '2', '1', '2', '2', '2', '3', '2', '4', '2', '5', '2', '6', '2', '7', '2', '8', '2', '9', '2', 'A', '2', 'B', '2', 'C', '2', 'D', '2', 'E', '2', 'F'};
   private static final ThreadLocal<char[]> bufLocal = new ThreadLocal();
   static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
   static final char[] replaceChars = new char[93];
   static final int[] sizeTable = new int[]{9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};
   static final byte[] specicalFlags_doubleQuotes = new byte[161];
   static final byte[] specicalFlags_singleQuotes = new byte[161];
   protected char[] buf;
   protected int count;
   protected int features;
   protected final Writer writer;


   static {
      specicalFlags_doubleQuotes[0] = 4;
      specicalFlags_doubleQuotes[1] = 4;
      specicalFlags_doubleQuotes[2] = 4;
      specicalFlags_doubleQuotes[3] = 4;
      specicalFlags_doubleQuotes[4] = 4;
      specicalFlags_doubleQuotes[5] = 4;
      specicalFlags_doubleQuotes[6] = 4;
      specicalFlags_doubleQuotes[7] = 4;
      specicalFlags_doubleQuotes[8] = 1;
      specicalFlags_doubleQuotes[9] = 1;
      specicalFlags_doubleQuotes[10] = 1;
      specicalFlags_doubleQuotes[11] = 4;
      specicalFlags_doubleQuotes[12] = 1;
      specicalFlags_doubleQuotes[13] = 1;
      specicalFlags_doubleQuotes[34] = 1;
      specicalFlags_doubleQuotes[92] = 1;
      specicalFlags_singleQuotes[0] = 4;
      specicalFlags_singleQuotes[1] = 4;
      specicalFlags_singleQuotes[2] = 4;
      specicalFlags_singleQuotes[3] = 4;
      specicalFlags_singleQuotes[4] = 4;
      specicalFlags_singleQuotes[5] = 4;
      specicalFlags_singleQuotes[6] = 4;
      specicalFlags_singleQuotes[7] = 4;
      specicalFlags_singleQuotes[8] = 1;
      specicalFlags_singleQuotes[9] = 1;
      specicalFlags_singleQuotes[10] = 1;
      specicalFlags_singleQuotes[11] = 4;
      specicalFlags_singleQuotes[12] = 1;
      specicalFlags_singleQuotes[13] = 1;
      specicalFlags_singleQuotes[92] = 1;
      specicalFlags_singleQuotes[39] = 1;

      int var0;
      for(var0 = 14; var0 <= 31; ++var0) {
         specicalFlags_doubleQuotes[var0] = 4;
         specicalFlags_singleQuotes[var0] = 4;
      }

      for(var0 = 127; var0 < 160; ++var0) {
         specicalFlags_doubleQuotes[var0] = 4;
         specicalFlags_singleQuotes[var0] = 4;
      }

      replaceChars[0] = 48;
      replaceChars[1] = 49;
      replaceChars[2] = 50;
      replaceChars[3] = 51;
      replaceChars[4] = 52;
      replaceChars[5] = 53;
      replaceChars[6] = 54;
      replaceChars[7] = 55;
      replaceChars[8] = 98;
      replaceChars[9] = 116;
      replaceChars[10] = 110;
      replaceChars[11] = 118;
      replaceChars[12] = 102;
      replaceChars[13] = 114;
      replaceChars[34] = 34;
      replaceChars[39] = 39;
      replaceChars[47] = 47;
      replaceChars[92] = 92;
      DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   }

   public SerializeWriter() {
      this((Writer)null);
   }

   public SerializeWriter(int var1) {
      this((Writer)null, var1);
   }

   public SerializeWriter(Writer var1) {
      this.writer = var1;
      this.features = JSON.DEFAULT_GENERATE_FEATURE;
      this.buf = (char[])bufLocal.get();
      if(bufLocal != null) {
         bufLocal.set((Object)null);
      }

      if(this.buf == null) {
         this.buf = new char[1024];
      }

   }

   public SerializeWriter(Writer var1, int var2) {
      this.writer = var1;
      if(var2 <= 0) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Negative initial size: ");
         var3.append(var2);
         throw new IllegalArgumentException(var3.toString());
      } else {
         this.buf = new char[var2];
      }
   }

   public SerializeWriter(Writer var1, int var2, SerializerFeature[] var3) {
      this.writer = var1;
      this.buf = (char[])bufLocal.get();
      if(this.buf != null) {
         bufLocal.set((Object)null);
      }

      if(this.buf == null) {
         this.buf = new char[1024];
      }

      int var6 = var3.length;
      byte var5 = 0;
      int var4 = var2;

      for(var2 = var5; var2 < var6; ++var2) {
         var4 |= var3[var2].mask;
      }

      this.features = var4;
   }

   public SerializeWriter(SerializerFeature ... var1) {
      this((Writer)null, 0, var1);
   }

   protected static void getChars(long var0, int var2, char[] var3) {
      byte var4;
      if(var0 < 0L) {
         var4 = 45;
         var0 = -var0;
      } else {
         var4 = 0;
      }

      int var5;
      while(var0 > 2147483647L) {
         long var8 = var0 / 100L;
         var5 = (int)(var0 - ((var8 << 6) + (var8 << 5) + (var8 << 2)));
         --var2;
         var3[var2] = DigitOnes[var5];
         --var2;
         var3[var2] = DigitTens[var5];
         var0 = var8;
      }

      var5 = (int)var0;

      while(true) {
         int var6 = var5;
         int var7 = var2;
         if(var5 < 65536) {
            while(true) {
               var2 = '\ucccd' * var6 >>> 19;
               --var7;
               var3[var7] = digits[var6 - ((var2 << 3) + (var2 << 1))];
               if(var2 == 0) {
                  if(var4 != 0) {
                     var3[var7 - 1] = (char)var4;
                  }

                  return;
               }

               var6 = var2;
            }
         }

         var6 = var5 / 100;
         var5 -= (var6 << 6) + (var6 << 5) + (var6 << 2);
         --var2;
         var3[var2] = DigitOnes[var5];
         --var2;
         var3[var2] = DigitTens[var5];
         var5 = var6;
      }
   }

   private void writeKeyWithDoubleQuoteIfHasSpecial(String var1) {
      int var7 = var1.length();
      int var2 = this.count;
      boolean var3 = true;
      int var5 = var2 + var7 + 1;
      var2 = this.buf.length;
      byte var4 = 0;
      int var10;
      if(var5 > var2) {
         if(this.writer != null) {
            if(var7 == 0) {
               this.write(34);
               this.write(34);
               this.write(58);
               return;
            }

            var2 = 0;

            boolean var14;
            while(true) {
               if(var2 >= var7) {
                  var14 = false;
                  break;
               }

               char var15 = var1.charAt(var2);
               if(var15 < specicalFlags_doubleQuotes.length && specicalFlags_doubleQuotes[var15] != 0) {
                  var14 = var3;
                  break;
               }

               ++var2;
            }

            var10 = var4;
            if(var14) {
               this.write(34);
               var10 = var4;
            }

            for(; var10 < var7; ++var10) {
               char var13 = var1.charAt(var10);
               if(var13 < specicalFlags_doubleQuotes.length && specicalFlags_doubleQuotes[var13] != 0) {
                  this.write(92);
                  this.write(replaceChars[var13]);
               } else {
                  this.write(var13);
               }
            }

            if(var14) {
               this.write(34);
            }

            this.write(58);
            return;
         }

         this.expandCapacity(var5);
      }

      char[] var9;
      if(var7 == 0) {
         if(this.count + 3 > this.buf.length) {
            this.expandCapacity(this.count + 3);
         }

         var9 = this.buf;
         var2 = this.count;
         this.count = var2 + 1;
         var9[var2] = 34;
         var9 = this.buf;
         var2 = this.count;
         this.count = var2 + 1;
         var9[var2] = 34;
         var9 = this.buf;
         var2 = this.count;
         this.count = var2 + 1;
         var9[var2] = 58;
      } else {
         int var6 = this.count;
         int var11 = var6 + var7;
         var1.getChars(0, var7, this.buf, var6);
         this.count = var5;
         var2 = var6;
         boolean var16 = false;
         var10 = var5;

         for(boolean var12 = var16; var2 < var11; ++var2) {
            char var8 = this.buf[var2];
            if(var8 < specicalFlags_doubleQuotes.length && specicalFlags_doubleQuotes[var8] != 0) {
               if(!var12) {
                  var10 += 3;
                  if(var10 > this.buf.length) {
                     this.expandCapacity(var10);
                  }

                  this.count = var10;
                  var9 = this.buf;
                  var5 = var2 + 1;
                  System.arraycopy(var9, var5, this.buf, var2 + 3, var11 - var2 - 1);
                  System.arraycopy(this.buf, 0, this.buf, 1, var2);
                  this.buf[var6] = 34;
                  this.buf[var5] = 92;
                  var9 = this.buf;
                  var2 = var5 + 1;
                  var9[var2] = replaceChars[var8];
                  var11 += 2;
                  this.buf[this.count - 2] = 34;
                  var12 = true;
               } else {
                  ++var10;
                  if(var10 > this.buf.length) {
                     this.expandCapacity(var10);
                  }

                  this.count = var10;
                  var9 = this.buf;
                  var7 = var2 + 1;
                  System.arraycopy(var9, var7, this.buf, var2 + 2, var11 - var2);
                  this.buf[var2] = 92;
                  this.buf[var7] = replaceChars[var8];
                  ++var11;
                  var2 = var7;
               }
            }
         }

         this.buf[this.count - 1] = 58;
      }
   }

   private void writeKeyWithSingleQuoteIfHasSpecial(String var1) {
      int var7 = var1.length();
      int var2 = this.count;
      boolean var3 = true;
      int var5 = var2 + var7 + 1;
      var2 = this.buf.length;
      byte var4 = 0;
      int var10;
      if(var5 > var2) {
         if(this.writer != null) {
            if(var7 == 0) {
               this.write(39);
               this.write(39);
               this.write(58);
               return;
            }

            var2 = 0;

            boolean var14;
            while(true) {
               if(var2 >= var7) {
                  var14 = false;
                  break;
               }

               char var15 = var1.charAt(var2);
               if(var15 < specicalFlags_singleQuotes.length && specicalFlags_singleQuotes[var15] != 0) {
                  var14 = var3;
                  break;
               }

               ++var2;
            }

            var10 = var4;
            if(var14) {
               this.write(39);
               var10 = var4;
            }

            for(; var10 < var7; ++var10) {
               char var13 = var1.charAt(var10);
               if(var13 < specicalFlags_singleQuotes.length && specicalFlags_singleQuotes[var13] != 0) {
                  this.write(92);
                  this.write(replaceChars[var13]);
               } else {
                  this.write(var13);
               }
            }

            if(var14) {
               this.write(39);
            }

            this.write(58);
            return;
         }

         this.expandCapacity(var5);
      }

      char[] var9;
      if(var7 == 0) {
         if(this.count + 3 > this.buf.length) {
            this.expandCapacity(this.count + 3);
         }

         var9 = this.buf;
         var2 = this.count;
         this.count = var2 + 1;
         var9[var2] = 39;
         var9 = this.buf;
         var2 = this.count;
         this.count = var2 + 1;
         var9[var2] = 39;
         var9 = this.buf;
         var2 = this.count;
         this.count = var2 + 1;
         var9[var2] = 58;
      } else {
         int var6 = this.count;
         int var11 = var6 + var7;
         var1.getChars(0, var7, this.buf, var6);
         this.count = var5;
         var2 = var6;
         boolean var16 = false;
         var10 = var5;

         for(boolean var12 = var16; var2 < var11; ++var2) {
            char var8 = this.buf[var2];
            if(var8 < specicalFlags_singleQuotes.length && specicalFlags_singleQuotes[var8] != 0) {
               if(!var12) {
                  var10 += 3;
                  if(var10 > this.buf.length) {
                     this.expandCapacity(var10);
                  }

                  this.count = var10;
                  var9 = this.buf;
                  var5 = var2 + 1;
                  System.arraycopy(var9, var5, this.buf, var2 + 3, var11 - var2 - 1);
                  System.arraycopy(this.buf, 0, this.buf, 1, var2);
                  this.buf[var6] = 39;
                  this.buf[var5] = 92;
                  var9 = this.buf;
                  var2 = var5 + 1;
                  var9[var2] = replaceChars[var8];
                  var11 += 2;
                  this.buf[this.count - 2] = 39;
                  var12 = true;
               } else {
                  ++var10;
                  if(var10 > this.buf.length) {
                     this.expandCapacity(var10);
                  }

                  this.count = var10;
                  var9 = this.buf;
                  var7 = var2 + 1;
                  System.arraycopy(var9, var7, this.buf, var2 + 2, var11 - var2);
                  this.buf[var2] = 92;
                  this.buf[var7] = replaceChars[var8];
                  ++var11;
                  var2 = var7;
               }
            }
         }

         this.buf[var10 - 1] = 58;
      }
   }

   public SerializeWriter append(char var1) {
      this.write(var1);
      return this;
   }

   public SerializeWriter append(CharSequence var1) {
      String var2;
      if(var1 == null) {
         var2 = "null";
      } else {
         var2 = var1.toString();
      }

      this.write(var2, 0, var2.length());
      return this;
   }

   public SerializeWriter append(CharSequence var1, int var2, int var3) {
      Object var4 = var1;
      if(var1 == null) {
         var4 = "null";
      }

      String var5 = ((CharSequence)var4).subSequence(var2, var3).toString();
      this.write(var5, 0, var5.length());
      return this;
   }

   public void close() {
      if(this.writer != null && this.count > 0) {
         this.flush();
      }

      if(this.buf.length <= 8192) {
         bufLocal.set(this.buf);
      }

      this.buf = null;
   }

   public void config(SerializerFeature var1, boolean var2) {
      int var3;
      if(var2) {
         var3 = this.features;
         this.features = var1.mask | var3;
      } else {
         var3 = this.features;
         this.features = ~var1.mask & var3;
      }
   }

   protected void expandCapacity(int var1) {
      int var2 = this.buf.length * 3 / 2 + 1;
      if(var2 >= var1) {
         var1 = var2;
      }

      char[] var3 = new char[var1];
      System.arraycopy(this.buf, 0, var3, 0, this.count);
      this.buf = var3;
   }

   public void flush() {
      if(this.writer != null) {
         try {
            this.writer.write(this.buf, 0, this.count);
            this.writer.flush();
            this.count = 0;
         } catch (IOException var2) {
            throw new JSONException(var2.getMessage(), var2);
         }
      }
   }

   public boolean isEnabled(SerializerFeature var1) {
      int var2 = this.features;
      return (var1.mask & var2) != 0;
   }

   public byte[] toBytes(String var1) {
      if(this.writer != null) {
         throw new UnsupportedOperationException("writer not null");
      } else {
         String var2 = var1;
         if(var1 == null) {
            var2 = "UTF-8";
         }

         try {
            byte[] var4 = (new String(this.buf, 0, this.count)).getBytes(var2);
            return var4;
         } catch (UnsupportedEncodingException var3) {
            throw new JSONException("toBytes error", var3);
         }
      }
   }

   public String toString() {
      return new String(this.buf, 0, this.count);
   }

   public void write(int var1) {
      int var3 = this.count + 1;
      int var2 = var3;
      if(var3 > this.buf.length) {
         if(this.writer == null) {
            this.expandCapacity(var3);
            var2 = var3;
         } else {
            this.flush();
            var2 = 1;
         }
      }

      this.buf[this.count] = (char)var1;
      this.count = var2;
   }

   public void write(String var1) {
      if(var1 == null) {
         this.writeNull();
      } else {
         this.write(var1, 0, var1.length());
      }
   }

   public void write(String var1, int var2, int var3) {
      int var7 = this.count + var3;
      int var4 = var7;
      int var5 = var2;
      int var6 = var3;
      if(var7 > this.buf.length) {
         var4 = var2;
         var5 = var3;
         if(this.writer == null) {
            this.expandCapacity(var7);
            var4 = var7;
            var5 = var2;
            var6 = var3;
         } else {
            while(true) {
               var2 = this.buf.length - this.count;
               var3 = var4 + var2;
               var1.getChars(var4, var3, this.buf, this.count);
               this.count = this.buf.length;
               this.flush();
               var2 = var5 - var2;
               if(var2 <= this.buf.length) {
                  var4 = var2;
                  var5 = var3;
                  var6 = var2;
                  break;
               }

               var4 = var3;
               var5 = var2;
            }
         }
      }

      var1.getChars(var5, var6 + var5, this.buf, this.count);
      this.count = var4;
   }

   public void write(boolean var1) {
      String var2;
      if(var1) {
         var2 = "true";
      } else {
         var2 = "false";
      }

      this.write(var2);
   }

   public void write(char[] var1, int var2, int var3) {
      if(var2 >= 0 && var2 <= var1.length && var3 >= 0) {
         int var4 = var2 + var3;
         if(var4 <= var1.length && var4 >= 0) {
            if(var3 == 0) {
               return;
            }

            int var7 = this.count + var3;
            int var6 = var7;
            int var5 = var2;
            var4 = var3;
            if(var7 > this.buf.length) {
               var4 = var2;
               var5 = var3;
               if(this.writer == null) {
                  this.expandCapacity(var7);
                  var6 = var7;
                  var5 = var2;
                  var4 = var3;
               } else {
                  do {
                     var3 = this.buf.length - this.count;
                     System.arraycopy(var1, var4, this.buf, this.count, var3);
                     this.count = this.buf.length;
                     this.flush();
                     var2 = var5 - var3;
                     var3 += var4;
                     var4 = var3;
                     var5 = var2;
                  } while(var2 > this.buf.length);

                  var6 = var2;
                  var4 = var2;
                  var5 = var3;
               }
            }

            System.arraycopy(var1, var5, this.buf, this.count, var4);
            this.count = var6;
            return;
         }
      }

      throw new IndexOutOfBoundsException();
   }

   public void writeByteArray(byte[] var1) {
      int var8 = var1.length;
      int var4 = this.features;
      int var7 = SerializerFeature.UseSingleQuotes.mask;
      byte var6 = 0;
      byte var5 = 0;
      boolean var17;
      if((var4 & var7) != 0) {
         var17 = true;
      } else {
         var17 = false;
      }

      byte var2;
      if(var17) {
         var2 = 39;
      } else {
         var2 = 34;
      }

      if(var8 == 0) {
         String var16;
         if(var17) {
            var16 = "\'\'";
         } else {
            var16 = "\"\"";
         }

         this.write(var16);
      } else {
         char[] var13 = JSONLexer.CA;
         int var9 = var8 / 3 * 3;
         var7 = var8 - 1;
         var4 = var7 / 3;
         int var11 = this.count;
         int var10 = this.count + (var4 + 1 << 2) + 2;
         byte var20;
         if(var10 > this.buf.length) {
            if(this.writer != null) {
               this.write(var2);

               int var19;
               for(var4 = 0; var4 < var9; ++var4) {
                  var19 = var4 + 1;
                  byte var21 = var1[var4];
                  var4 = var19 + 1;
                  var19 = (var21 & 255) << 16 | (var1[var19] & 255) << 8 | var1[var4] & 255;
                  this.write(var13[var19 >>> 18 & 63]);
                  this.write(var13[var19 >>> 12 & 63]);
                  this.write(var13[var19 >>> 6 & 63]);
                  this.write(var13[var19 & 63]);
               }

               var19 = var8 - var9;
               if(var19 > 0) {
                  var20 = var1[var9];
                  var4 = var5;
                  if(var19 == 2) {
                     var4 = (var1[var7] & 255) << 2;
                  }

                  var4 |= (var20 & 255) << 10;
                  this.write(var13[var4 >> 12]);
                  this.write(var13[var4 >>> 6 & 63]);
                  char var22;
                  if(var19 == 2) {
                     var22 = var13[var4 & 63];
                  } else {
                     var22 = 61;
                  }

                  this.write(var22);
                  this.write(61);
               }

               this.write(var2);
               return;
            }

            this.expandCapacity(var10);
         }

         this.count = var10;
         char[] var14 = this.buf;
         var4 = var11 + 1;
         var14[var11] = (char)var2;

         int var18;
         int var23;
         for(var18 = 0; var18 < var9; var4 = var23 + 1) {
            var11 = var18 + 1;
            byte var12 = var1[var18];
            var18 = var11 + 1;
            var11 = (var12 & 255) << 16 | (var1[var11] & 255) << 8 | var1[var18] & 255;
            var14 = this.buf;
            var23 = var4 + 1;
            var14[var4] = var13[var11 >>> 18 & 63];
            var14 = this.buf;
            var4 = var23 + 1;
            var14[var23] = var13[var11 >>> 12 & 63];
            var14 = this.buf;
            var23 = var4 + 1;
            var14[var4] = var13[var11 >>> 6 & 63];
            this.buf[var23] = var13[var11 & 63];
            ++var18;
         }

         var18 = var8 - var9;
         if(var18 > 0) {
            var20 = var1[var9];
            var4 = var6;
            if(var18 == 2) {
               var4 = (var1[var7] & 255) << 2;
            }

            var4 |= (var20 & 255) << 10;
            this.buf[var10 - 5] = var13[var4 >> 12];
            this.buf[var10 - 4] = var13[var4 >>> 6 & 63];
            char[] var15 = this.buf;
            char var3;
            if(var18 == 2) {
               var3 = var13[var4 & 63];
            } else {
               var3 = 61;
            }

            var15[var10 - 3] = var3;
            this.buf[var10 - 2] = 61;
         }

         this.buf[var10 - 1] = (char)var2;
      }
   }

   public void writeFieldName(String var1, boolean var2) {
      if((this.features & SerializerFeature.UseSingleQuotes.mask) != 0) {
         if((this.features & SerializerFeature.QuoteFieldNames.mask) != 0) {
            this.writeStringWithSingleQuote(var1);
            this.write(58);
         } else {
            this.writeKeyWithSingleQuoteIfHasSpecial(var1);
         }
      } else if((this.features & SerializerFeature.QuoteFieldNames.mask) != 0) {
         this.writeStringWithDoubleQuote(var1, ':', var2);
      } else {
         this.writeKeyWithDoubleQuoteIfHasSpecial(var1);
      }
   }

   public void writeInt(int var1) {
      if(var1 == Integer.MIN_VALUE) {
         this.write("-2147483648");
      } else {
         int var2;
         if(var1 < 0) {
            var2 = -var1;
         } else {
            var2 = var1;
         }

         int var3;
         for(var3 = 0; var2 > sizeTable[var3]; ++var3) {
            ;
         }

         ++var3;
         var2 = var3;
         if(var1 < 0) {
            var2 = var3 + 1;
         }

         var3 = this.count + var2;
         if(var3 > this.buf.length) {
            if(this.writer != null) {
               char[] var4 = new char[var2];
               getChars((long)var1, var2, var4);
               this.write(var4, 0, var4.length);
               return;
            }

            this.expandCapacity(var3);
         }

         getChars((long)var1, var3, this.buf);
         this.count = var3;
      }
   }

   public void writeLong(long var1) {
      if(var1 == Long.MIN_VALUE) {
         this.write("-9223372036854775808");
      } else {
         long var5;
         if(var1 < 0L) {
            var5 = -var1;
         } else {
            var5 = var1;
         }

         int var4 = 1;
         long var7 = 10L;

         while(true) {
            if(var4 >= 19) {
               var4 = 0;
               break;
            }

            if(var5 < var7) {
               break;
            }

            var7 *= 10L;
            ++var4;
         }

         int var3 = var4;
         if(var4 == 0) {
            var3 = 19;
         }

         var4 = var3;
         if(var1 < 0L) {
            var4 = var3 + 1;
         }

         var3 = this.count + var4;
         if(var3 > this.buf.length) {
            if(this.writer != null) {
               char[] var9 = new char[var4];
               getChars(var1, var4, var9);
               this.write(var9, 0, var9.length);
               return;
            }

            this.expandCapacity(var3);
         }

         getChars(var1, var3, this.buf);
         this.count = var3;
      }
   }

   public void writeNull() {
      this.write("null");
   }

   public void writeString(String var1) {
      if((this.features & SerializerFeature.UseSingleQuotes.mask) != 0) {
         this.writeStringWithSingleQuote(var1);
      } else {
         this.writeStringWithDoubleQuote(var1, '\u0000', true);
      }
   }

   protected void writeStringWithDoubleQuote(String var1, char var2, boolean var3) {
      if(var1 == null) {
         this.writeNull();
         if(var2 != 0) {
            this.write(var2);
         }

      } else {
         int var7 = var1.length();
         int var6 = this.count + var7 + 2;
         int var5 = var6;
         if(var2 != 0) {
            var5 = var6 + 1;
         }

         int var8 = this.buf.length;
         byte var22 = 0;
         if(var5 > var8) {
            if(this.writer != null) {
               this.write(34);

               for(var5 = var22; var5 < var1.length(); ++var5) {
                  char var24 = var1.charAt(var5);
                  if((var24 >= specicalFlags_doubleQuotes.length || specicalFlags_doubleQuotes[var24] == 0) && (var24 != 47 || (this.features & SerializerFeature.WriteSlashAsSpecial.mask) == 0)) {
                     this.write(var24);
                  } else {
                     this.write(92);
                     this.write(replaceChars[var24]);
                  }
               }

               this.write(34);
               if(var2 != 0) {
                  this.write(var2);
               }

               return;
            }

            this.expandCapacity(var5);
         }

         int var17 = this.count + 1;
         int var19 = var17 + var7;
         this.buf[this.count] = 34;
         var1.getChars(0, var7, this.buf, var17);
         this.count = var5;
         if(var3) {
            var7 = var5;
            var5 = var17;
            int var11 = 0;
            var6 = -1;
            char var23 = 0;

            int var9;
            int var15;
            for(var9 = -1; var5 < var19; var9 = var15) {
               int var10;
               int var12;
               int var13;
               char var14;
               label182: {
                  char var18;
                  label181: {
                     label180: {
                        var18 = this.buf[var5];
                        if(var18 == 8232) {
                           var9 = var11 + 1;
                           var11 = var7 + 4;
                           var7 = var9;
                           var8 = var11;
                           var10 = var6;
                           if(var6 == -1) {
                              var8 = var11;
                              var7 = var9;
                              break label180;
                           }
                        } else if(var18 >= 93) {
                           var10 = var11;
                           var12 = var7;
                           var13 = var6;
                           var14 = var23;
                           var15 = var9;
                           if(var18 < 127) {
                              break label182;
                           }

                           var10 = var11;
                           var12 = var7;
                           var13 = var6;
                           var14 = var23;
                           var15 = var9;
                           if(var18 >= 160) {
                              break label182;
                           }

                           var10 = var6;
                           if(var6 == -1) {
                              var10 = var5;
                           }

                           var6 = var11 + 1;
                           var8 = var7 + 4;
                           var7 = var6;
                        } else {
                           boolean var16;
                           label164: {
                              if(var18 != 32) {
                                 label162: {
                                    label200: {
                                       if(var18 == 47) {
                                          var10 = this.features;
                                          if((SerializerFeature.WriteSlashAsSpecial.mask & var10) != 0) {
                                             break label200;
                                          }
                                       }

                                       if(var18 > 35 && var18 != 92 || var18 > 31 && var18 != 92 && var18 != 34) {
                                          break label162;
                                       }
                                    }

                                    var16 = true;
                                    break label164;
                                 }
                              }

                              var16 = false;
                           }

                           var10 = var11;
                           var12 = var7;
                           var13 = var6;
                           var14 = var23;
                           var15 = var9;
                           if(!var16) {
                              break label182;
                           }

                           ++var11;
                           var9 = var7;
                           if(var18 < specicalFlags_doubleQuotes.length) {
                              var9 = var7;
                              if(specicalFlags_doubleQuotes[var18] == 4) {
                                 var9 = var7 + 4;
                              }
                           }

                           var7 = var11;
                           var8 = var9;
                           var10 = var6;
                           if(var6 == -1) {
                              var7 = var11;
                              var8 = var9;
                              break label180;
                           }
                        }

                        var6 = var10;
                        break label181;
                     }

                     var6 = var5;
                  }

                  var15 = var5;
                  var14 = var18;
                  var10 = var7;
                  var12 = var8;
                  var13 = var6;
               }

               ++var5;
               var11 = var10;
               var7 = var12;
               var6 = var13;
               var23 = var14;
            }

            if(var11 > 0) {
               var5 = var7 + var11;
               if(var5 > this.buf.length) {
                  this.expandCapacity(var5);
               }

               this.count = var5;
               if(var11 == 1) {
                  char[] var21;
                  if(var23 == 8232) {
                     var5 = var9 + 1;
                     System.arraycopy(this.buf, var5, this.buf, var9 + 6, var19 - var9 - 1);
                     this.buf[var9] = 92;
                     this.buf[var5] = 117;
                     var21 = this.buf;
                     ++var5;
                     var21[var5] = 50;
                     var21 = this.buf;
                     ++var5;
                     var21[var5] = 48;
                     var21 = this.buf;
                     ++var5;
                     var21[var5] = 50;
                     this.buf[var5 + 1] = 56;
                  } else if(var23 < specicalFlags_doubleQuotes.length && specicalFlags_doubleQuotes[var23] == 4) {
                     var5 = var9 + 1;
                     System.arraycopy(this.buf, var5, this.buf, var9 + 6, var19 - var9 - 1);
                     this.buf[var9] = 92;
                     var21 = this.buf;
                     var6 = var5 + 1;
                     var21[var5] = 117;
                     var21 = this.buf;
                     var5 = var6 + 1;
                     var21[var6] = DIGITS[var23 >>> 12 & 15];
                     var21 = this.buf;
                     var6 = var5 + 1;
                     var21[var5] = DIGITS[var23 >>> 8 & 15];
                     this.buf[var6] = DIGITS[var23 >>> 4 & 15];
                     this.buf[var6 + 1] = DIGITS[var23 & 15];
                  } else {
                     var5 = var9 + 1;
                     System.arraycopy(this.buf, var5, this.buf, var9 + 2, var19 - var9 - 1);
                     this.buf[var9] = 92;
                     this.buf[var5] = replaceChars[var23];
                  }
               } else if(var11 > 1) {
                  var7 = var6 - var17;
                  var5 = var6;

                  for(var6 = var7; var6 < var1.length(); ++var6) {
                     char var4 = var1.charAt(var6);
                     char[] var20;
                     if((var4 >= specicalFlags_doubleQuotes.length || specicalFlags_doubleQuotes[var4] == 0) && (var4 != 47 || (this.features & SerializerFeature.WriteSlashAsSpecial.mask) == 0)) {
                        if(var4 == 8232) {
                           var20 = this.buf;
                           var7 = var5 + 1;
                           var20[var5] = 92;
                           var20 = this.buf;
                           var5 = var7 + 1;
                           var20[var7] = 117;
                           var20 = this.buf;
                           var7 = var5 + 1;
                           var20[var5] = DIGITS[var4 >>> 12 & 15];
                           var20 = this.buf;
                           var5 = var7 + 1;
                           var20[var7] = DIGITS[var4 >>> 8 & 15];
                           var20 = this.buf;
                           var7 = var5 + 1;
                           var20[var5] = DIGITS[var4 >>> 4 & 15];
                           var20 = this.buf;
                           var5 = var7 + 1;
                           var20[var7] = DIGITS[var4 & 15];
                        } else {
                           this.buf[var5] = var4;
                           ++var5;
                        }
                     } else {
                        var20 = this.buf;
                        var7 = var5 + 1;
                        var20[var5] = 92;
                        if(specicalFlags_doubleQuotes[var4] == 4) {
                           var20 = this.buf;
                           var5 = var7 + 1;
                           var20[var7] = 117;
                           var20 = this.buf;
                           var7 = var5 + 1;
                           var20[var5] = DIGITS[var4 >>> 12 & 15];
                           var20 = this.buf;
                           var5 = var7 + 1;
                           var20[var7] = DIGITS[var4 >>> 8 & 15];
                           var20 = this.buf;
                           var7 = var5 + 1;
                           var20[var5] = DIGITS[var4 >>> 4 & 15];
                           var20 = this.buf;
                           var5 = var7 + 1;
                           var20[var7] = DIGITS[var4 & 15];
                        } else {
                           var20 = this.buf;
                           var5 = var7 + 1;
                           var20[var7] = replaceChars[var4];
                        }
                     }
                  }
               }
            }
         }

         if(var2 != 0) {
            this.buf[this.count - 2] = 34;
            this.buf[this.count - 1] = var2;
         } else {
            this.buf[this.count - 1] = 34;
         }
      }
   }

   protected void writeStringWithSingleQuote(String var1) {
      int var6 = 0;
      int var2 = 0;
      if(var1 == null) {
         var2 = this.count + 4;
         if(var2 > this.buf.length) {
            this.expandCapacity(var2);
         }

         "null".getChars(0, 4, this.buf, this.count);
         this.count = var2;
      } else {
         int var3 = var1.length();
         int var12 = this.count + var3 + 2;
         char var14;
         if(var12 > this.buf.length) {
            if(this.writer != null) {
               this.write(39);

               for(; var2 < var1.length(); ++var2) {
                  var14 = var1.charAt(var2);
                  if(var14 > 13 && var14 != 92 && var14 != 39 && (var14 != 47 || (this.features & SerializerFeature.WriteSlashAsSpecial.mask) == 0)) {
                     this.write(var14);
                  } else {
                     this.write(92);
                     this.write(replaceChars[var14]);
                  }
               }

               this.write(39);
               return;
            }

            this.expandCapacity(var12);
         }

         int var9 = this.count + 1;
         int var11 = var9 + var3;
         this.buf[this.count] = 39;
         var1.getChars(0, var3, this.buf, var9);
         this.count = var12;
         var2 = var9;
         int var4 = -1;

         char var5;
         for(var14 = 0; var2 < var11; var14 = var5) {
            int var7;
            int var8;
            label98: {
               char var10 = this.buf[var2];
               if(var10 > 13 && var10 != 92 && var10 != 39) {
                  var8 = var6;
                  var7 = var4;
                  var5 = var14;
                  if(var10 != 47) {
                     break label98;
                  }

                  var8 = var6;
                  var7 = var4;
                  var5 = var14;
                  if((this.features & SerializerFeature.WriteSlashAsSpecial.mask) == 0) {
                     break label98;
                  }
               }

               var8 = var6 + 1;
               var7 = var2;
               var5 = var10;
            }

            ++var2;
            var6 = var8;
            var4 = var7;
         }

         var2 = var12 + var6;
         if(var2 > this.buf.length) {
            this.expandCapacity(var2);
         }

         this.count = var2;
         char[] var13;
         if(var6 == 1) {
            var13 = this.buf;
            var2 = var4 + 1;
            System.arraycopy(var13, var2, this.buf, var4 + 2, var11 - var4 - 1);
            this.buf[var4] = 92;
            this.buf[var2] = replaceChars[var14];
         } else if(var6 > 1) {
            var13 = this.buf;
            var2 = var4 + 1;
            System.arraycopy(var13, var2, this.buf, var4 + 2, var11 - var4 - 1);
            this.buf[var4] = 92;
            this.buf[var2] = replaceChars[var14];
            var3 = var11 + 1;

            for(var2 -= 2; var2 >= var9; var3 = var4) {
               label80: {
                  var5 = this.buf[var2];
                  if(var5 > 13 && var5 != 92 && var5 != 39) {
                     var4 = var3;
                     if(var5 != 47) {
                        break label80;
                     }

                     var4 = var3;
                     if((this.features & SerializerFeature.WriteSlashAsSpecial.mask) == 0) {
                        break label80;
                     }
                  }

                  var13 = this.buf;
                  var4 = var2 + 1;
                  System.arraycopy(var13, var4, this.buf, var2 + 2, var3 - var2 - 1);
                  this.buf[var2] = 92;
                  this.buf[var4] = replaceChars[var5];
                  var4 = var3 + 1;
               }

               --var2;
            }
         }

         this.buf[this.count - 1] = 39;
      }
   }

   public void writeTo(OutputStream var1, String var2) throws IOException {
      this.writeTo(var1, Charset.forName(var2));
   }

   public void writeTo(OutputStream var1, Charset var2) throws IOException {
      if(this.writer != null) {
         throw new UnsupportedOperationException("writer not null");
      } else {
         var1.write((new String(this.buf, 0, this.count)).getBytes(var2.name()));
      }
   }

   public void writeTo(Writer var1) throws IOException {
      if(this.writer != null) {
         throw new UnsupportedOperationException("writer not null");
      } else {
         var1.write(this.buf, 0, this.count);
      }
   }
}
