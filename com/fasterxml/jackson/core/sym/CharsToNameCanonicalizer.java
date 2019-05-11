package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;

public final class CharsToNameCanonicalizer {

   protected static final int DEFAULT_TABLE_SIZE = 64;
   public static final int HASH_MULT = 33;
   static final int MAX_COLL_CHAIN_FOR_REUSE = 63;
   static final int MAX_COLL_CHAIN_LENGTH = 255;
   static final int MAX_ENTRIES_FOR_REUSE = 12000;
   protected static final int MAX_TABLE_SIZE = 65536;
   static final CharsToNameCanonicalizer sBootstrapSymbolTable = new CharsToNameCanonicalizer();
   protected CharsToNameCanonicalizer.Bucket[] _buckets;
   protected final boolean _canonicalize;
   protected boolean _dirty;
   private final int _hashSeed;
   protected int _indexMask;
   protected final boolean _intern;
   protected int _longestCollisionList;
   protected CharsToNameCanonicalizer _parent;
   protected int _size;
   protected int _sizeThreshold;
   protected String[] _symbols;


   private CharsToNameCanonicalizer() {
      this._canonicalize = true;
      this._intern = true;
      this._dirty = true;
      this._hashSeed = 0;
      this._longestCollisionList = 0;
      this.initTables(64);
   }

   private CharsToNameCanonicalizer(CharsToNameCanonicalizer var1, boolean var2, boolean var3, String[] var4, CharsToNameCanonicalizer.Bucket[] var5, int var6, int var7, int var8) {
      this._parent = var1;
      this._canonicalize = var2;
      this._intern = var3;
      this._symbols = var4;
      this._buckets = var5;
      this._size = var6;
      this._hashSeed = var7;
      var6 = var4.length;
      this._sizeThreshold = _thresholdSize(var6);
      this._indexMask = var6 - 1;
      this._longestCollisionList = var8;
      this._dirty = false;
   }

   private static int _thresholdSize(int var0) {
      return var0 - (var0 >> 2);
   }

   private void copyArrays() {
      String[] var2 = this._symbols;
      int var1 = var2.length;
      this._symbols = new String[var1];
      System.arraycopy(var2, 0, this._symbols, 0, var1);
      CharsToNameCanonicalizer.Bucket[] var3 = this._buckets;
      var1 = var3.length;
      this._buckets = new CharsToNameCanonicalizer.Bucket[var1];
      System.arraycopy(var3, 0, this._buckets, 0, var1);
   }

   public static CharsToNameCanonicalizer createRoot() {
      long var0 = System.currentTimeMillis();
      return createRoot((int)var0 + (int)(var0 >>> 32) | 1);
   }

   protected static CharsToNameCanonicalizer createRoot(int var0) {
      return sBootstrapSymbolTable.makeOrphan(var0);
   }

   private void initTables(int var1) {
      this._symbols = new String[var1];
      this._buckets = new CharsToNameCanonicalizer.Bucket[var1 >> 1];
      this._indexMask = var1 - 1;
      this._size = 0;
      this._longestCollisionList = 0;
      this._sizeThreshold = _thresholdSize(var1);
   }

   private CharsToNameCanonicalizer makeOrphan(int var1) {
      return new CharsToNameCanonicalizer((CharsToNameCanonicalizer)null, true, true, this._symbols, this._buckets, this._size, var1, this._longestCollisionList);
   }

   private void mergeChild(CharsToNameCanonicalizer param1) {
      // $FF: Couldn't be decompiled
   }

   private void rehash() {
      int var7 = this._symbols.length;
      int var1 = var7 + var7;
      byte var6 = 0;
      if(var1 > 65536) {
         this._size = 0;
         Arrays.fill(this._symbols, (Object)null);
         Arrays.fill(this._buckets, (Object)null);
         this._dirty = true;
      } else {
         String[] var8 = this._symbols;
         CharsToNameCanonicalizer.Bucket[] var9 = this._buckets;
         this._symbols = new String[var1];
         this._buckets = new CharsToNameCanonicalizer.Bucket[var1 >> 1];
         this._indexMask = var1 - 1;
         this._sizeThreshold = _thresholdSize(var1);
         int var3 = 0;
         int var2 = 0;

         int var4;
         String var10;
         CharsToNameCanonicalizer.Bucket var13;
         for(var1 = 0; var3 < var7; var1 = var4) {
            var10 = var8[var3];
            int var5 = var2;
            var4 = var1;
            if(var10 != null) {
               var5 = var2 + 1;
               var2 = this._hashToIndex(this.calcHash(var10));
               if(this._symbols[var2] == null) {
                  this._symbols[var2] = var10;
                  var4 = var1;
               } else {
                  var2 >>= 1;
                  var13 = new CharsToNameCanonicalizer.Bucket(var10, this._buckets[var2]);
                  this._buckets[var2] = var13;
                  var4 = Math.max(var1, var13.length());
               }
            }

            ++var3;
            var2 = var5;
         }

         var4 = var1;
         var3 = var2;

         for(var1 = var6; var1 < var7 >> 1; var4 = var2) {
            CharsToNameCanonicalizer.Bucket var11 = var9[var1];

            for(var2 = var4; var11 != null; var11 = var11.getNext()) {
               ++var3;
               var10 = var11.getSymbol();
               var4 = this._hashToIndex(this.calcHash(var10));
               if(this._symbols[var4] == null) {
                  this._symbols[var4] = var10;
               } else {
                  var4 >>= 1;
                  var13 = new CharsToNameCanonicalizer.Bucket(var10, this._buckets[var4]);
                  this._buckets[var4] = var13;
                  var2 = Math.max(var2, var13.length());
               }
            }

            ++var1;
         }

         this._longestCollisionList = var4;
         if(var3 != this._size) {
            StringBuilder var12 = new StringBuilder();
            var12.append("Internal error on SymbolTable.rehash(): had ");
            var12.append(this._size);
            var12.append(" entries; now have ");
            var12.append(var3);
            var12.append(".");
            throw new Error(var12.toString());
         }
      }
   }

   public int _hashToIndex(int var1) {
      return var1 + (var1 >>> 15) & this._indexMask;
   }

   public int bucketCount() {
      return this._symbols.length;
   }

   public int calcHash(String var1) {
      int var4 = var1.length();
      int var2 = this._hashSeed;

      int var3;
      for(var3 = 0; var3 < var4; ++var3) {
         var2 = var2 * 33 + var1.charAt(var3);
      }

      var3 = var2;
      if(var2 == 0) {
         var3 = 1;
      }

      return var3;
   }

   public int calcHash(char[] var1, int var2, int var3) {
      var2 = this._hashSeed;

      for(int var4 = 0; var4 < var3; ++var4) {
         var2 = var2 * 33 + var1[var4];
      }

      var3 = var2;
      if(var2 == 0) {
         var3 = 1;
      }

      return var3;
   }

   public int collisionCount() {
      CharsToNameCanonicalizer.Bucket[] var5 = this._buckets;
      int var4 = var5.length;
      int var1 = 0;

      int var2;
      int var3;
      for(var2 = 0; var1 < var4; var2 = var3) {
         CharsToNameCanonicalizer.Bucket var6 = var5[var1];
         var3 = var2;
         if(var6 != null) {
            var3 = var2 + var6.length();
         }

         ++var1;
      }

      return var2;
   }

   public String findSymbol(char[] var1, int var2, int var3, int var4) {
      if(var3 < 1) {
         return "";
      } else if(!this._canonicalize) {
         return new String(var1, var2, var3);
      } else {
         int var6 = this._hashToIndex(var4);
         String var7 = this._symbols[var6];
         CharsToNameCanonicalizer.Bucket var9;
         if(var7 != null) {
            if(var7.length() == var3) {
               var4 = 0;

               while(var7.charAt(var4) == var1[var2 + var4]) {
                  int var5 = var4 + 1;
                  var4 = var5;
                  if(var5 >= var3) {
                     var4 = var5;
                     break;
                  }
               }

               if(var4 == var3) {
                  return var7;
               }
            }

            var9 = this._buckets[var6 >> 1];
            if(var9 != null) {
               var7 = var9.find(var1, var2, var3);
               if(var7 != null) {
                  return var7;
               }
            }
         }

         if(!this._dirty) {
            this.copyArrays();
            this._dirty = true;
            var4 = var6;
         } else {
            var4 = var6;
            if(this._size >= this._sizeThreshold) {
               this.rehash();
               var4 = this._hashToIndex(this.calcHash(var1, var2, var3));
            }
         }

         var7 = new String(var1, var2, var3);
         String var8 = var7;
         if(this._intern) {
            var8 = InternCache.instance.intern(var7);
         }

         ++this._size;
         if(this._symbols[var4] == null) {
            this._symbols[var4] = var8;
            return var8;
         } else {
            var2 = var4 >> 1;
            var9 = new CharsToNameCanonicalizer.Bucket(var8, this._buckets[var2]);
            this._buckets[var2] = var9;
            this._longestCollisionList = Math.max(var9.length(), this._longestCollisionList);
            if(this._longestCollisionList > 255) {
               this.reportTooManyCollisions(255);
            }

            return var8;
         }
      }
   }

   public int hashSeed() {
      return this._hashSeed;
   }

   public CharsToNameCanonicalizer makeChild(boolean param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public int maxCollisionLength() {
      return this._longestCollisionList;
   }

   public boolean maybeDirty() {
      return this._dirty;
   }

   public void release() {
      if(this.maybeDirty()) {
         if(this._parent != null) {
            this._parent.mergeChild(this);
            this._dirty = false;
         }

      }
   }

   protected void reportTooManyCollisions(int var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("Longest collision chain in symbol table (of size ");
      var2.append(this._size);
      var2.append(") now exceeds maximum, ");
      var2.append(var1);
      var2.append(" -- suspect a DoS attack based on hash collisions");
      throw new IllegalStateException(var2.toString());
   }

   public int size() {
      return this._size;
   }

   static final class Bucket {

      private final int _length;
      private final CharsToNameCanonicalizer.Bucket _next;
      private final String _symbol;


      public Bucket(String var1, CharsToNameCanonicalizer.Bucket var2) {
         this._symbol = var1;
         this._next = var2;
         int var3 = 1;
         if(var2 != null) {
            var3 = 1 + var2._length;
         }

         this._length = var3;
      }

      public String find(char[] var1, int var2, int var3) {
         String var7 = this._symbol;
         CharsToNameCanonicalizer.Bucket var6 = this._next;

         while(true) {
            if(var7.length() == var3) {
               int var4 = 0;

               while(var7.charAt(var4) == var1[var2 + var4]) {
                  int var5 = var4 + 1;
                  var4 = var5;
                  if(var5 >= var3) {
                     var4 = var5;
                     break;
                  }
               }

               if(var4 == var3) {
                  return var7;
               }
            }

            if(var6 == null) {
               return null;
            }

            var7 = var6.getSymbol();
            var6 = var6.getNext();
         }
      }

      public CharsToNameCanonicalizer.Bucket getNext() {
         return this._next;
      }

      public String getSymbol() {
         return this._symbol;
      }

      public int length() {
         return this._length;
      }
   }
}
