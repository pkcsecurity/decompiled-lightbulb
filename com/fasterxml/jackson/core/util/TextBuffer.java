package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.util.BufferRecycler;
import java.math.BigDecimal;
import java.util.ArrayList;

public final class TextBuffer {

   static final int MAX_SEGMENT_LEN = 262144;
   static final int MIN_SEGMENT_LEN = 1000;
   static final char[] NO_CHARS = new char[0];
   private final BufferRecycler _allocator;
   private char[] _currentSegment;
   private int _currentSize;
   private boolean _hasSegments = false;
   private char[] _inputBuffer;
   private int _inputLen;
   private int _inputStart;
   private char[] _resultArray;
   private String _resultString;
   private int _segmentSize;
   private ArrayList<char[]> _segments;


   public TextBuffer(BufferRecycler var1) {
      this._allocator = var1;
   }

   private char[] _charArray(int var1) {
      return new char[var1];
   }

   private char[] buildResultArray() {
      if(this._resultString != null) {
         return this._resultString.toCharArray();
      } else {
         char[] var5;
         if(this._inputStart >= 0) {
            if(this._inputLen < 1) {
               return NO_CHARS;
            } else {
               var5 = this._charArray(this._inputLen);
               System.arraycopy(this._inputBuffer, this._inputStart, var5, 0, this._inputLen);
               return var5;
            }
         } else {
            int var1 = this.size();
            if(var1 < 1) {
               return NO_CHARS;
            } else {
               var5 = this._charArray(var1);
               int var3;
               if(this._segments != null) {
                  int var4 = this._segments.size();
                  int var2 = 0;
                  var1 = 0;

                  while(true) {
                     var3 = var1;
                     if(var2 >= var4) {
                        break;
                     }

                     char[] var6 = (char[])this._segments.get(var2);
                     var3 = var6.length;
                     System.arraycopy(var6, 0, var5, var1, var3);
                     var1 += var3;
                     ++var2;
                  }
               } else {
                  var3 = 0;
               }

               System.arraycopy(this._currentSegment, 0, var5, var3, this._currentSize);
               return var5;
            }
         }
      }
   }

   private void clearSegments() {
      this._hasSegments = false;
      this._segments.clear();
      this._segmentSize = 0;
      this._currentSize = 0;
   }

   private void expand(int var1) {
      if(this._segments == null) {
         this._segments = new ArrayList();
      }

      char[] var4 = this._currentSegment;
      this._hasSegments = true;
      this._segments.add(var4);
      this._segmentSize += var4.length;
      int var3 = var4.length;
      int var2 = var3 >> 1;
      if(var2 >= var1) {
         var1 = var2;
      }

      var4 = this._charArray(Math.min(262144, var3 + var1));
      this._currentSize = 0;
      this._currentSegment = var4;
   }

   private char[] findBuffer(int var1) {
      return this._allocator != null?this._allocator.allocCharBuffer(BufferRecycler.CharBufferType.TEXT_BUFFER, var1):new char[Math.max(var1, 1000)];
   }

   private void unshare(int var1) {
      int var2 = this._inputLen;
      this._inputLen = 0;
      char[] var4 = this._inputBuffer;
      this._inputBuffer = null;
      int var3 = this._inputStart;
      this._inputStart = -1;
      var1 += var2;
      if(this._currentSegment == null || var1 > this._currentSegment.length) {
         this._currentSegment = this.findBuffer(var1);
      }

      if(var2 > 0) {
         System.arraycopy(var4, var3, this._currentSegment, 0, var2);
      }

      this._segmentSize = 0;
      this._currentSize = var2;
   }

   public void append(char var1) {
      if(this._inputStart >= 0) {
         this.unshare(16);
      }

      this._resultString = null;
      this._resultArray = null;
      char[] var4 = this._currentSegment;
      char[] var3 = var4;
      if(this._currentSize >= var4.length) {
         this.expand(1);
         var3 = this._currentSegment;
      }

      int var2 = this._currentSize;
      this._currentSize = var2 + 1;
      var3[var2] = var1;
   }

   public void append(String var1, int var2, int var3) {
      if(this._inputStart >= 0) {
         this.unshare(var3);
      }

      this._resultString = null;
      this._resultArray = null;
      char[] var7 = this._currentSegment;
      int var6 = var7.length - this._currentSize;
      if(var6 >= var3) {
         var1.getChars(var2, var2 + var3, var7, this._currentSize);
         this._currentSize += var3;
      } else {
         int var4 = var2;
         int var5 = var3;
         if(var6 > 0) {
            var4 = var2 + var6;
            var1.getChars(var2, var4, var7, this._currentSize);
            var5 = var3 - var6;
         }

         while(true) {
            this.expand(var5);
            var3 = Math.min(this._currentSegment.length, var5);
            var2 = var4 + var3;
            var1.getChars(var4, var2, this._currentSegment, 0);
            this._currentSize += var3;
            var5 -= var3;
            if(var5 <= 0) {
               return;
            }

            var4 = var2;
         }
      }
   }

   public void append(char[] var1, int var2, int var3) {
      if(this._inputStart >= 0) {
         this.unshare(var3);
      }

      this._resultString = null;
      this._resultArray = null;
      char[] var7 = this._currentSegment;
      int var6 = var7.length - this._currentSize;
      if(var6 >= var3) {
         System.arraycopy(var1, var2, var7, this._currentSize, var3);
         this._currentSize += var3;
      } else {
         int var5 = var2;
         int var4 = var3;
         if(var6 > 0) {
            System.arraycopy(var1, var2, var7, this._currentSize, var6);
            var5 = var2 + var6;
            var4 = var3 - var6;
         }

         do {
            this.expand(var4);
            var2 = Math.min(this._currentSegment.length, var4);
            System.arraycopy(var1, var5, this._currentSegment, 0, var2);
            this._currentSize += var2;
            var5 += var2;
            var2 = var4 - var2;
            var4 = var2;
         } while(var2 > 0);

      }
   }

   public char[] contentsAsArray() {
      char[] var2 = this._resultArray;
      char[] var1 = var2;
      if(var2 == null) {
         var1 = this.buildResultArray();
         this._resultArray = var1;
      }

      return var1;
   }

   public BigDecimal contentsAsDecimal() throws NumberFormatException {
      return this._resultArray != null?new BigDecimal(this._resultArray):(this._inputStart >= 0?new BigDecimal(this._inputBuffer, this._inputStart, this._inputLen):(this._segmentSize == 0?new BigDecimal(this._currentSegment, 0, this._currentSize):new BigDecimal(this.contentsAsArray())));
   }

   public double contentsAsDouble() throws NumberFormatException {
      return NumberInput.parseDouble(this.contentsAsString());
   }

   public String contentsAsString() {
      if(this._resultString == null) {
         if(this._resultArray != null) {
            this._resultString = new String(this._resultArray);
         } else if(this._inputStart >= 0) {
            if(this._inputLen < 1) {
               this._resultString = "";
               return "";
            }

            this._resultString = new String(this._inputBuffer, this._inputStart, this._inputLen);
         } else {
            int var1 = this._segmentSize;
            int var2 = this._currentSize;
            if(var1 == 0) {
               String var3;
               if(var2 == 0) {
                  var3 = "";
               } else {
                  var3 = new String(this._currentSegment, 0, var2);
               }

               this._resultString = var3;
            } else {
               StringBuilder var5 = new StringBuilder(var1 + var2);
               if(this._segments != null) {
                  var2 = this._segments.size();

                  for(var1 = 0; var1 < var2; ++var1) {
                     char[] var4 = (char[])this._segments.get(var1);
                     var5.append(var4, 0, var4.length);
                  }
               }

               var5.append(this._currentSegment, 0, this._currentSize);
               this._resultString = var5.toString();
            }
         }
      }

      return this._resultString;
   }

   public char[] emptyAndGetCurrentSegment() {
      this._inputStart = -1;
      this._currentSize = 0;
      this._inputLen = 0;
      this._inputBuffer = null;
      this._resultString = null;
      this._resultArray = null;
      if(this._hasSegments) {
         this.clearSegments();
      }

      char[] var2 = this._currentSegment;
      char[] var1 = var2;
      if(var2 == null) {
         var1 = this.findBuffer(0);
         this._currentSegment = var1;
      }

      return var1;
   }

   public void ensureNotShared() {
      if(this._inputStart >= 0) {
         this.unshare(16);
      }

   }

   public char[] expandCurrentSegment() {
      char[] var3 = this._currentSegment;
      int var2 = var3.length;
      int var1;
      if(var2 == 262144) {
         var1 = 262145;
      } else {
         var1 = Math.min(262144, (var2 >> 1) + var2);
      }

      this._currentSegment = this._charArray(var1);
      System.arraycopy(var3, 0, this._currentSegment, 0, var2);
      return this._currentSegment;
   }

   public char[] finishCurrentSegment() {
      if(this._segments == null) {
         this._segments = new ArrayList();
      }

      this._hasSegments = true;
      this._segments.add(this._currentSegment);
      int var1 = this._currentSegment.length;
      this._segmentSize += var1;
      char[] var2 = this._charArray(Math.min(var1 + (var1 >> 1), 262144));
      this._currentSize = 0;
      this._currentSegment = var2;
      return var2;
   }

   public char[] getCurrentSegment() {
      if(this._inputStart >= 0) {
         this.unshare(1);
      } else {
         char[] var1 = this._currentSegment;
         if(var1 == null) {
            this._currentSegment = this.findBuffer(0);
         } else if(this._currentSize >= var1.length) {
            this.expand(1);
         }
      }

      return this._currentSegment;
   }

   public int getCurrentSegmentSize() {
      return this._currentSize;
   }

   public char[] getTextBuffer() {
      if(this._inputStart >= 0) {
         return this._inputBuffer;
      } else if(this._resultArray != null) {
         return this._resultArray;
      } else if(this._resultString != null) {
         char[] var1 = this._resultString.toCharArray();
         this._resultArray = var1;
         return var1;
      } else {
         return !this._hasSegments?this._currentSegment:this.contentsAsArray();
      }
   }

   public int getTextOffset() {
      return this._inputStart >= 0?this._inputStart:0;
   }

   public boolean hasTextAsCharacters() {
      return this._inputStart < 0?(this._resultArray != null?true:this._resultString == null):true;
   }

   public void releaseBuffers() {
      if(this._allocator == null) {
         this.resetWithEmpty();
      } else {
         if(this._currentSegment != null) {
            this.resetWithEmpty();
            char[] var1 = this._currentSegment;
            this._currentSegment = null;
            this._allocator.releaseCharBuffer(BufferRecycler.CharBufferType.TEXT_BUFFER, var1);
         }

      }
   }

   public void resetWithCopy(char[] var1, int var2, int var3) {
      this._inputBuffer = null;
      this._inputStart = -1;
      this._inputLen = 0;
      this._resultString = null;
      this._resultArray = null;
      if(this._hasSegments) {
         this.clearSegments();
      } else if(this._currentSegment == null) {
         this._currentSegment = this.findBuffer(var3);
      }

      this._segmentSize = 0;
      this._currentSize = 0;
      this.append(var1, var2, var3);
   }

   public void resetWithEmpty() {
      this._inputStart = -1;
      this._currentSize = 0;
      this._inputLen = 0;
      this._inputBuffer = null;
      this._resultString = null;
      this._resultArray = null;
      if(this._hasSegments) {
         this.clearSegments();
      }

   }

   public void resetWithShared(char[] var1, int var2, int var3) {
      this._resultString = null;
      this._resultArray = null;
      this._inputBuffer = var1;
      this._inputStart = var2;
      this._inputLen = var3;
      if(this._hasSegments) {
         this.clearSegments();
      }

   }

   public void resetWithString(String var1) {
      this._inputBuffer = null;
      this._inputStart = -1;
      this._inputLen = 0;
      this._resultString = var1;
      this._resultArray = null;
      if(this._hasSegments) {
         this.clearSegments();
      }

      this._currentSize = 0;
   }

   public void setCurrentLength(int var1) {
      this._currentSize = var1;
   }

   public int size() {
      return this._inputStart >= 0?this._inputLen:(this._resultArray != null?this._resultArray.length:(this._resultString != null?this._resultString.length():this._segmentSize + this._currentSize));
   }

   public String toString() {
      return this.contentsAsString();
   }
}
