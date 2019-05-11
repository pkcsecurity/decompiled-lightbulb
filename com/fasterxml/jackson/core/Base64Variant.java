package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import java.io.Serializable;
import java.util.Arrays;

public final class Base64Variant implements Serializable {

   public static final int BASE64_VALUE_INVALID = -1;
   public static final int BASE64_VALUE_PADDING = -2;
   private static final int INT_SPACE = 32;
   static final char PADDING_CHAR_NONE = '\u0000';
   private static final long serialVersionUID = 1L;
   private final transient int[] _asciiToBase64;
   private final transient byte[] _base64ToAsciiB;
   private final transient char[] _base64ToAsciiC;
   protected final transient int _maxLineLength;
   protected final String _name;
   protected final transient char _paddingChar;
   protected final transient boolean _usesPadding;


   public Base64Variant(Base64Variant var1, String var2, int var3) {
      this(var1, var2, var1._usesPadding, var1._paddingChar, var3);
   }

   public Base64Variant(Base64Variant var1, String var2, boolean var3, char var4, int var5) {
      this._asciiToBase64 = new int[128];
      this._base64ToAsciiC = new char[64];
      this._base64ToAsciiB = new byte[64];
      this._name = var2;
      byte[] var7 = var1._base64ToAsciiB;
      System.arraycopy(var7, 0, this._base64ToAsciiB, 0, var7.length);
      char[] var8 = var1._base64ToAsciiC;
      System.arraycopy(var8, 0, this._base64ToAsciiC, 0, var8.length);
      int[] var6 = var1._asciiToBase64;
      System.arraycopy(var6, 0, this._asciiToBase64, 0, var6.length);
      this._usesPadding = var3;
      this._paddingChar = var4;
      this._maxLineLength = var5;
   }

   public Base64Variant(String var1, String var2, boolean var3, char var4, int var5) {
      this._asciiToBase64 = new int[128];
      this._base64ToAsciiC = new char[64];
      this._base64ToAsciiB = new byte[64];
      this._name = var1;
      this._usesPadding = var3;
      this._paddingChar = var4;
      this._maxLineLength = var5;
      int var6 = var2.length();
      if(var6 != 64) {
         StringBuilder var9 = new StringBuilder();
         var9.append("Base64Alphabet length must be exactly 64 (was ");
         var9.append(var6);
         var9.append(")");
         throw new IllegalArgumentException(var9.toString());
      } else {
         char[] var8 = this._base64ToAsciiC;
         var5 = 0;
         var2.getChars(0, var6, var8, 0);
         Arrays.fill(this._asciiToBase64, -1);

         while(var5 < var6) {
            char var7 = this._base64ToAsciiC[var5];
            this._base64ToAsciiB[var5] = (byte)var7;
            this._asciiToBase64[var7] = var5++;
         }

         if(var3) {
            this._asciiToBase64[var4] = -2;
         }

      }
   }

   protected void _reportBase64EOF() throws IllegalArgumentException {
      throw new IllegalArgumentException("Unexpected end-of-String in base64 content");
   }

   protected void _reportInvalidBase64(char var1, int var2, String var3) throws IllegalArgumentException {
      StringBuilder var4;
      String var6;
      if(var1 <= 32) {
         var4 = new StringBuilder();
         var4.append("Illegal white space character (code 0x");
         var4.append(Integer.toHexString(var1));
         var4.append(") as character #");
         var4.append(var2 + 1);
         var4.append(" of 4-char base64 unit: can only used between units");
         var6 = var4.toString();
      } else if(this.usesPaddingChar(var1)) {
         var4 = new StringBuilder();
         var4.append("Unexpected padding character (\'");
         var4.append(this.getPaddingChar());
         var4.append("\') as character #");
         var4.append(var2 + 1);
         var4.append(" of 4-char base64 unit: padding only legal as 3rd or 4th character");
         var6 = var4.toString();
      } else if(Character.isDefined(var1) && !Character.isISOControl(var1)) {
         var4 = new StringBuilder();
         var4.append("Illegal character \'");
         var4.append(var1);
         var4.append("\' (code 0x");
         var4.append(Integer.toHexString(var1));
         var4.append(") in base64 content");
         var6 = var4.toString();
      } else {
         var4 = new StringBuilder();
         var4.append("Illegal character (code 0x");
         var4.append(Integer.toHexString(var1));
         var4.append(") in base64 content");
         var6 = var4.toString();
      }

      String var5 = var6;
      if(var3 != null) {
         StringBuilder var7 = new StringBuilder();
         var7.append(var6);
         var7.append(": ");
         var7.append(var3);
         var5 = var7.toString();
      }

      throw new IllegalArgumentException(var5);
   }

   public void decode(String var1, ByteArrayBuilder var2) throws IllegalArgumentException {
      int var6 = var1.length();
      int var4 = 0;

      while(var4 < var6) {
         while(true) {
            int var5 = var4 + 1;
            char var3 = var1.charAt(var4);
            if(var5 >= var6) {
               return;
            }

            if(var3 > 32) {
               int var7 = this.decodeBase64Char(var3);
               if(var7 < 0) {
                  this._reportInvalidBase64(var3, 0, (String)null);
               }

               if(var5 >= var6) {
                  this._reportBase64EOF();
               }

               var4 = var5 + 1;
               var3 = var1.charAt(var5);
               var5 = this.decodeBase64Char(var3);
               if(var5 < 0) {
                  this._reportInvalidBase64(var3, 1, (String)null);
               }

               var7 = var7 << 6 | var5;
               if(var4 >= var6) {
                  if(!this.usesPadding()) {
                     var2.append(var7 >> 4);
                     return;
                  }

                  this._reportBase64EOF();
               }

               var5 = var4 + 1;
               var3 = var1.charAt(var4);
               var4 = this.decodeBase64Char(var3);
               if(var4 < 0) {
                  if(var4 != -2) {
                     this._reportInvalidBase64(var3, 2, (String)null);
                  }

                  if(var5 >= var6) {
                     this._reportBase64EOF();
                  }

                  var4 = var5 + 1;
                  var3 = var1.charAt(var5);
                  if(!this.usesPaddingChar(var3)) {
                     StringBuilder var8 = new StringBuilder();
                     var8.append("expected padding character \'");
                     var8.append(this.getPaddingChar());
                     var8.append("\'");
                     this._reportInvalidBase64(var3, 3, var8.toString());
                  }

                  var2.append(var7 >> 4);
               } else {
                  var4 |= var7 << 6;
                  if(var5 >= var6) {
                     if(!this.usesPadding()) {
                        var2.appendTwoBytes(var4 >> 2);
                        return;
                     }

                     this._reportBase64EOF();
                  }

                  var3 = var1.charAt(var5);
                  var7 = this.decodeBase64Char(var3);
                  if(var7 < 0) {
                     if(var7 != -2) {
                        this._reportInvalidBase64(var3, 3, (String)null);
                     }

                     var2.appendTwoBytes(var4 >> 2);
                  } else {
                     var2.appendThreeBytes(var4 << 6 | var7);
                  }

                  var4 = var5 + 1;
               }
               break;
            }

            var4 = var5;
         }
      }

   }

   public byte[] decode(String var1) throws IllegalArgumentException {
      ByteArrayBuilder var2 = new ByteArrayBuilder();
      this.decode(var1, var2);
      return var2.toByteArray();
   }

   public int decodeBase64Byte(byte var1) {
      return var1 <= 127?this._asciiToBase64[var1]:-1;
   }

   public int decodeBase64Char(char var1) {
      return var1 <= 127?this._asciiToBase64[var1]:-1;
   }

   public int decodeBase64Char(int var1) {
      return var1 <= 127?this._asciiToBase64[var1]:-1;
   }

   public String encode(byte[] var1) {
      return this.encode(var1, false);
   }

   public String encode(byte[] var1, boolean var2) {
      int var6 = var1.length;
      StringBuilder var8 = new StringBuilder((var6 >> 2) + var6 + (var6 >> 3));
      if(var2) {
         var8.append('\"');
      }

      int var4 = this.getMaxLineLength() >> 2;

      int var3;
      int var5;
      for(var3 = 0; var3 <= var6 - 3; var3 = var5) {
         int var7 = var3 + 1;
         byte var9 = var1[var3];
         var5 = var7 + 1;
         this.encodeBase64Chunk(var8, (var9 << 8 | var1[var7] & 255) << 8 | var1[var5] & 255);
         --var4;
         var3 = var4;
         if(var4 <= 0) {
            var8.append('\\');
            var8.append('n');
            var3 = this.getMaxLineLength() >> 2;
         }

         ++var5;
         var4 = var3;
      }

      var6 -= var3;
      if(var6 > 0) {
         var5 = var1[var3] << 16;
         var4 = var5;
         if(var6 == 2) {
            var4 = var5 | (var1[var3 + 1] & 255) << 8;
         }

         this.encodeBase64Partial(var8, var4, var6);
      }

      if(var2) {
         var8.append('\"');
      }

      return var8.toString();
   }

   public byte encodeBase64BitsAsByte(int var1) {
      return this._base64ToAsciiB[var1];
   }

   public char encodeBase64BitsAsChar(int var1) {
      return this._base64ToAsciiC[var1];
   }

   public int encodeBase64Chunk(int var1, byte[] var2, int var3) {
      int var4 = var3 + 1;
      var2[var3] = this._base64ToAsciiB[var1 >> 18 & 63];
      var3 = var4 + 1;
      var2[var4] = this._base64ToAsciiB[var1 >> 12 & 63];
      var4 = var3 + 1;
      var2[var3] = this._base64ToAsciiB[var1 >> 6 & 63];
      var2[var4] = this._base64ToAsciiB[var1 & 63];
      return var4 + 1;
   }

   public int encodeBase64Chunk(int var1, char[] var2, int var3) {
      int var4 = var3 + 1;
      var2[var3] = this._base64ToAsciiC[var1 >> 18 & 63];
      var3 = var4 + 1;
      var2[var4] = this._base64ToAsciiC[var1 >> 12 & 63];
      var4 = var3 + 1;
      var2[var3] = this._base64ToAsciiC[var1 >> 6 & 63];
      var2[var4] = this._base64ToAsciiC[var1 & 63];
      return var4 + 1;
   }

   public void encodeBase64Chunk(StringBuilder var1, int var2) {
      var1.append(this._base64ToAsciiC[var2 >> 18 & 63]);
      var1.append(this._base64ToAsciiC[var2 >> 12 & 63]);
      var1.append(this._base64ToAsciiC[var2 >> 6 & 63]);
      var1.append(this._base64ToAsciiC[var2 & 63]);
   }

   public int encodeBase64Partial(int var1, int var2, byte[] var3, int var4) {
      int var8 = var4 + 1;
      var3[var4] = this._base64ToAsciiB[var1 >> 18 & 63];
      int var7 = var8 + 1;
      var3[var8] = this._base64ToAsciiB[var1 >> 12 & 63];
      if(this._usesPadding) {
         byte var6 = (byte)this._paddingChar;
         var4 = var7 + 1;
         byte var5;
         if(var2 == 2) {
            var5 = this._base64ToAsciiB[var1 >> 6 & 63];
         } else {
            var5 = var6;
         }

         var3[var7] = var5;
         var3[var4] = var6;
         return var4 + 1;
      } else {
         var4 = var7;
         if(var2 == 2) {
            var3[var7] = this._base64ToAsciiB[var1 >> 6 & 63];
            var4 = var7 + 1;
         }

         return var4;
      }
   }

   public int encodeBase64Partial(int var1, int var2, char[] var3, int var4) {
      int var7 = var4 + 1;
      var3[var4] = this._base64ToAsciiC[var1 >> 18 & 63];
      int var6 = var7 + 1;
      var3[var7] = this._base64ToAsciiC[var1 >> 12 & 63];
      if(this._usesPadding) {
         var4 = var6 + 1;
         char var5;
         if(var2 == 2) {
            var5 = this._base64ToAsciiC[var1 >> 6 & 63];
         } else {
            var5 = this._paddingChar;
         }

         var3[var6] = var5;
         var3[var4] = this._paddingChar;
         return var4 + 1;
      } else {
         var4 = var6;
         if(var2 == 2) {
            var3[var6] = this._base64ToAsciiC[var1 >> 6 & 63];
            var4 = var6 + 1;
         }

         return var4;
      }
   }

   public void encodeBase64Partial(StringBuilder var1, int var2, int var3) {
      var1.append(this._base64ToAsciiC[var2 >> 18 & 63]);
      var1.append(this._base64ToAsciiC[var2 >> 12 & 63]);
      if(this._usesPadding) {
         char var4;
         if(var3 == 2) {
            var4 = this._base64ToAsciiC[var2 >> 6 & 63];
         } else {
            var4 = this._paddingChar;
         }

         var1.append(var4);
         var1.append(this._paddingChar);
      } else {
         if(var3 == 2) {
            var1.append(this._base64ToAsciiC[var2 >> 6 & 63]);
         }

      }
   }

   public boolean equals(Object var1) {
      return var1 == this;
   }

   public int getMaxLineLength() {
      return this._maxLineLength;
   }

   public String getName() {
      return this._name;
   }

   public byte getPaddingByte() {
      return (byte)this._paddingChar;
   }

   public char getPaddingChar() {
      return this._paddingChar;
   }

   public int hashCode() {
      return this._name.hashCode();
   }

   protected Object readResolve() {
      return Base64Variants.valueOf(this._name);
   }

   public String toString() {
      return this._name;
   }

   public boolean usesPadding() {
      return this._usesPadding;
   }

   public boolean usesPaddingChar(char var1) {
      return var1 == this._paddingChar;
   }

   public boolean usesPaddingChar(int var1) {
      return var1 == this._paddingChar;
   }
}
