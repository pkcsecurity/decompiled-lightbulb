package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.sym.Name;
import com.fasterxml.jackson.core.sym.Name1;
import com.fasterxml.jackson.core.sym.Name2;
import com.fasterxml.jackson.core.sym.Name3;
import com.fasterxml.jackson.core.sym.NameN;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public final class BytesToNameCanonicalizer {

   protected static final int DEFAULT_TABLE_SIZE = 64;
   static final int INITIAL_COLLISION_LEN = 32;
   static final int LAST_VALID_BUCKET = 254;
   static final int MAX_COLL_CHAIN_FOR_REUSE = 63;
   static final int MAX_COLL_CHAIN_LENGTH = 255;
   static final int MAX_ENTRIES_FOR_REUSE = 6000;
   protected static final int MAX_TABLE_SIZE = 65536;
   static final int MIN_HASH_SIZE = 16;
   private static final int MULT = 33;
   private static final int MULT2 = 65599;
   private static final int MULT3 = 31;
   protected int _collCount;
   protected int _collEnd;
   protected BytesToNameCanonicalizer.Bucket[] _collList;
   private boolean _collListShared;
   protected int _count;
   private final int _hashSeed;
   protected final boolean _intern;
   protected int _longestCollisionList;
   protected int[] _mainHash;
   protected int _mainHashMask;
   private boolean _mainHashShared;
   protected Name[] _mainNames;
   private boolean _mainNamesShared;
   private transient boolean _needRehash;
   protected final BytesToNameCanonicalizer _parent;
   protected final AtomicReference<BytesToNameCanonicalizer.TableInfo> _tableInfo;


   private BytesToNameCanonicalizer(int var1, boolean var2, int var3) {
      this._parent = null;
      this._hashSeed = var3;
      this._intern = var2;
      var3 = 16;
      int var4;
      if(var1 < 16) {
         var4 = var3;
      } else if((var1 - 1 & var1) != 0) {
         while(true) {
            var4 = var3;
            if(var3 >= var1) {
               break;
            }

            var3 += var3;
         }
      } else {
         var4 = var1;
      }

      this._tableInfo = new AtomicReference(this.initTableInfo(var4));
   }

   private BytesToNameCanonicalizer(BytesToNameCanonicalizer var1, boolean var2, int var3, BytesToNameCanonicalizer.TableInfo var4) {
      this._parent = var1;
      this._hashSeed = var3;
      this._intern = var2;
      this._tableInfo = null;
      this._count = var4.count;
      this._mainHashMask = var4.mainHashMask;
      this._mainHash = var4.mainHash;
      this._mainNames = var4.mainNames;
      this._collList = var4.collList;
      this._collCount = var4.collCount;
      this._collEnd = var4.collEnd;
      this._longestCollisionList = var4.longestCollisionList;
      this._needRehash = false;
      this._mainHashShared = true;
      this._mainNamesShared = true;
      this._collListShared = true;
   }

   private void _addSymbol(int var1, Name var2) {
      if(this._mainHashShared) {
         this.unshareMain();
      }

      if(this._needRehash) {
         this.rehash();
      }

      ++this._count;
      int var4 = this._mainHashMask & var1;
      int var3;
      if(this._mainNames[var4] == null) {
         this._mainHash[var4] = var1 << 8;
         if(this._mainNamesShared) {
            this.unshareNames();
         }

         this._mainNames[var4] = var2;
      } else {
         if(this._collListShared) {
            this.unshareCollision();
         }

         ++this._collCount;
         int var5 = this._mainHash[var4];
         var1 = var5 & 255;
         if(var1 == 0) {
            if(this._collEnd <= 254) {
               var3 = this._collEnd++;
               var1 = var3;
               if(var3 >= this._collList.length) {
                  this.expandCollision();
                  var1 = var3;
               }
            } else {
               var1 = this.findBestBucket();
            }

            this._mainHash[var4] = var5 & -256 | var1 + 1;
         } else {
            --var1;
         }

         BytesToNameCanonicalizer.Bucket var6 = new BytesToNameCanonicalizer.Bucket(var2, this._collList[var1]);
         this._collList[var1] = var6;
         this._longestCollisionList = Math.max(var6.length(), this._longestCollisionList);
         if(this._longestCollisionList > 255) {
            this.reportTooManyCollisions(255);
         }
      }

      var1 = this._mainHash.length;
      if(this._count > var1 >> 1) {
         var3 = var1 >> 2;
         if(this._count > var1 - var3) {
            this._needRehash = true;
            return;
         }

         if(this._collCount >= var3) {
            this._needRehash = true;
         }
      }

   }

   protected static int[] calcQuads(byte[] var0) {
      int var5 = var0.length;
      int[] var6 = new int[(var5 + 3) / 4];

      int var2;
      for(int var1 = 0; var1 < var5; var1 = var2 + 1) {
         int var3 = var0[var1] & 255;
         int var4 = var1 + 1;
         var2 = var4;
         var1 = var3;
         if(var4 < var5) {
            var3 = var3 << 8 | var0[var4] & 255;
            ++var4;
            var2 = var4;
            var1 = var3;
            if(var4 < var5) {
               var3 = var3 << 8 | var0[var4] & 255;
               ++var4;
               var2 = var4;
               var1 = var3;
               if(var4 < var5) {
                  var1 = var3 << 8 | var0[var4] & 255;
                  var2 = var4;
               }
            }
         }

         var6[var2 >> 2] = var1;
      }

      return var6;
   }

   private static Name constructName(int var0, String var1, int var2, int var3) {
      return (Name)(var3 == 0?new Name1(var1, var0, var2):new Name2(var1, var0, var2, var3));
   }

   private static Name constructName(int var0, String var1, int[] var2, int var3) {
      int var4 = 0;
      if(var3 < 4) {
         switch(var3) {
         case 1:
            return new Name1(var1, var0, var2[0]);
         case 2:
            return new Name2(var1, var0, var2[0], var2[1]);
         case 3:
            return new Name3(var1, var0, var2[0], var2[1], var2[2]);
         }
      }

      int[] var5;
      for(var5 = new int[var3]; var4 < var3; ++var4) {
         var5[var4] = var2[var4];
      }

      return new NameN(var1, var0, var5, var3);
   }

   public static BytesToNameCanonicalizer createRoot() {
      long var0 = System.currentTimeMillis();
      return createRoot((int)var0 + (int)(var0 >>> 32) | 1);
   }

   protected static BytesToNameCanonicalizer createRoot(int var0) {
      return new BytesToNameCanonicalizer(64, true, var0);
   }

   private void expandCollision() {
      BytesToNameCanonicalizer.Bucket[] var2 = this._collList;
      int var1 = var2.length;
      this._collList = new BytesToNameCanonicalizer.Bucket[var1 + var1];
      System.arraycopy(var2, 0, this._collList, 0, var1);
   }

   private int findBestBucket() {
      BytesToNameCanonicalizer.Bucket[] var7 = this._collList;
      int var6 = this._collEnd;
      int var2 = Integer.MAX_VALUE;
      int var3 = -1;

      int var4;
      for(int var1 = 0; var1 < var6; var2 = var4) {
         int var5 = var7[var1].length();
         var4 = var2;
         if(var5 < var2) {
            if(var5 == 1) {
               return var1;
            }

            var3 = var1;
            var4 = var5;
         }

         ++var1;
      }

      return var3;
   }

   public static Name getEmptyName() {
      return Name1.getEmptyName();
   }

   private BytesToNameCanonicalizer.TableInfo initTableInfo(int var1) {
      return new BytesToNameCanonicalizer.TableInfo(0, var1 - 1, new int[var1], new Name[var1], (BytesToNameCanonicalizer.Bucket[])null, 0, 0, 0);
   }

   private void mergeChild(BytesToNameCanonicalizer.TableInfo var1) {
      int var2 = var1.count;
      BytesToNameCanonicalizer.TableInfo var4 = (BytesToNameCanonicalizer.TableInfo)this._tableInfo.get();
      if(var2 > var4.count) {
         BytesToNameCanonicalizer.TableInfo var3;
         label14: {
            if(var2 <= 6000) {
               var3 = var1;
               if(var1.longestCollisionList <= 63) {
                  break label14;
               }
            }

            var3 = this.initTableInfo(64);
         }

         this._tableInfo.compareAndSet(var4, var3);
      }
   }

   private void nukeSymbols() {
      this._count = 0;
      this._longestCollisionList = 0;
      Arrays.fill(this._mainHash, 0);
      Arrays.fill(this._mainNames, (Object)null);
      Arrays.fill(this._collList, (Object)null);
      this._collCount = 0;
      this._collEnd = 0;
   }

   private void rehash() {
      byte var4 = 0;
      this._needRehash = false;
      this._mainNamesShared = false;
      int var5 = this._mainHash.length;
      int var1 = var5 + var5;
      if(var1 > 65536) {
         this.nukeSymbols();
      } else {
         this._mainHash = new int[var1];
         this._mainHashMask = var1 - 1;
         Name[] var9 = this._mainNames;
         this._mainNames = new Name[var1];
         int var2 = 0;

         int var3;
         int var6;
         for(var1 = 0; var2 < var5; var1 = var3) {
            Name var10 = var9[var2];
            var3 = var1;
            if(var10 != null) {
               var3 = var1 + 1;
               var1 = var10.hashCode();
               var6 = this._mainHashMask & var1;
               this._mainNames[var6] = var10;
               this._mainHash[var6] = var1 << 8;
            }

            ++var2;
         }

         var6 = this._collEnd;
         if(var6 == 0) {
            this._longestCollisionList = 0;
         } else {
            this._collCount = 0;
            this._collEnd = 0;
            this._collListShared = false;
            BytesToNameCanonicalizer.Bucket[] var15 = this._collList;
            this._collList = new BytesToNameCanonicalizer.Bucket[var15.length];
            var3 = 0;

            int var12;
            for(var2 = var4; var2 < var6; ++var2) {
               for(BytesToNameCanonicalizer.Bucket var13 = var15[var2]; var13 != null; var1 = var12) {
                  var12 = var1 + 1;
                  Name var11 = var13._name;
                  var1 = var11.hashCode();
                  int var7 = this._mainHashMask & var1;
                  int var8 = this._mainHash[var7];
                  if(this._mainNames[var7] == null) {
                     this._mainHash[var7] = var1 << 8;
                     this._mainNames[var7] = var11;
                  } else {
                     ++this._collCount;
                     var1 = var8 & 255;
                     if(var1 == 0) {
                        if(this._collEnd <= 254) {
                           var5 = this._collEnd++;
                           var1 = var5;
                           if(var5 >= this._collList.length) {
                              this.expandCollision();
                              var1 = var5;
                           }
                        } else {
                           var1 = this.findBestBucket();
                        }

                        this._mainHash[var7] = var8 & -256 | var1 + 1;
                     } else {
                        --var1;
                     }

                     BytesToNameCanonicalizer.Bucket var16 = new BytesToNameCanonicalizer.Bucket(var11, this._collList[var1]);
                     this._collList[var1] = var16;
                     var3 = Math.max(var3, var16.length());
                  }

                  var13 = var13._next;
               }
            }

            this._longestCollisionList = var3;
            if(var1 != this._count) {
               StringBuilder var14 = new StringBuilder();
               var14.append("Internal error: count after rehash ");
               var14.append(var1);
               var14.append("; should be ");
               var14.append(this._count);
               throw new RuntimeException(var14.toString());
            }
         }
      }
   }

   private void unshareCollision() {
      BytesToNameCanonicalizer.Bucket[] var2 = this._collList;
      if(var2 == null) {
         this._collList = new BytesToNameCanonicalizer.Bucket[32];
      } else {
         int var1 = var2.length;
         this._collList = new BytesToNameCanonicalizer.Bucket[var1];
         System.arraycopy(var2, 0, this._collList, 0, var1);
      }

      this._collListShared = false;
   }

   private void unshareMain() {
      int[] var2 = this._mainHash;
      int var1 = this._mainHash.length;
      this._mainHash = new int[var1];
      System.arraycopy(var2, 0, this._mainHash, 0, var1);
      this._mainHashShared = false;
   }

   private void unshareNames() {
      Name[] var2 = this._mainNames;
      int var1 = var2.length;
      this._mainNames = new Name[var1];
      System.arraycopy(var2, 0, this._mainNames, 0, var1);
      this._mainNamesShared = false;
   }

   public Name addName(String var1, int var2, int var3) {
      String var5 = var1;
      if(this._intern) {
         var5 = InternCache.instance.intern(var1);
      }

      int var4;
      if(var3 == 0) {
         var4 = this.calcHash(var2);
      } else {
         var4 = this.calcHash(var2, var3);
      }

      Name var6 = constructName(var4, var5, var2, var3);
      this._addSymbol(var4, var6);
      return var6;
   }

   public Name addName(String var1, int[] var2, int var3) {
      String var5 = var1;
      if(this._intern) {
         var5 = InternCache.instance.intern(var1);
      }

      int var4;
      if(var3 < 3) {
         if(var3 == 1) {
            var4 = this.calcHash(var2[0]);
         } else {
            var4 = this.calcHash(var2[0], var2[1]);
         }
      } else {
         var4 = this.calcHash(var2, var3);
      }

      Name var6 = constructName(var4, var5, var2, var3);
      this._addSymbol(var4, var6);
      return var6;
   }

   public int bucketCount() {
      return this._mainHash.length;
   }

   public int calcHash(int var1) {
      var1 ^= this._hashSeed;
      var1 += var1 >>> 15;
      return var1 ^ var1 >>> 9;
   }

   public int calcHash(int var1, int var2) {
      var1 = (var1 ^ var1 >>> 15) + var2 * 33 ^ this._hashSeed;
      return var1 + (var1 >>> 7);
   }

   public int calcHash(int[] var1, int var2) {
      int var3 = 3;
      if(var2 < 3) {
         throw new IllegalArgumentException();
      } else {
         int var4 = var1[0] ^ this._hashSeed;
         var4 = ((var4 + (var4 >>> 9)) * 33 + var1[1]) * 65599;
         var4 = var4 + (var4 >>> 15) ^ var1[2];

         for(var4 >>>= 17; var3 < var2; ++var3) {
            var4 = var4 * 31 ^ var1[var3];
            var4 += var4 >>> 3;
            var4 ^= var4 << 7;
         }

         var2 = var4 + (var4 >>> 15);
         return var2 << 9 ^ var2;
      }
   }

   public int collisionCount() {
      return this._collCount;
   }

   public Name findName(int var1) {
      int var2 = this.calcHash(var1);
      int var3 = this._mainHashMask & var2;
      int var4 = this._mainHash[var3];
      if((var4 >> 8 ^ var2) << 8 == 0) {
         Name var5 = this._mainNames[var3];
         if(var5 == null) {
            return null;
         }

         if(var5.equals(var1)) {
            return var5;
         }
      } else if(var4 == 0) {
         return null;
      }

      var3 = var4 & 255;
      if(var3 > 0) {
         BytesToNameCanonicalizer.Bucket var6 = this._collList[var3 - 1];
         if(var6 != null) {
            return var6.find(var2, var1, 0);
         }
      }

      return null;
   }

   public Name findName(int var1, int var2) {
      int var3;
      if(var2 == 0) {
         var3 = this.calcHash(var1);
      } else {
         var3 = this.calcHash(var1, var2);
      }

      int var4 = this._mainHashMask & var3;
      int var5 = this._mainHash[var4];
      if((var5 >> 8 ^ var3) << 8 == 0) {
         Name var6 = this._mainNames[var4];
         if(var6 == null) {
            return null;
         }

         if(var6.equals(var1, var2)) {
            return var6;
         }
      } else if(var5 == 0) {
         return null;
      }

      var4 = var5 & 255;
      if(var4 > 0) {
         BytesToNameCanonicalizer.Bucket var7 = this._collList[var4 - 1];
         if(var7 != null) {
            return var7.find(var3, var1, var2);
         }
      }

      return null;
   }

   public Name findName(int[] var1, int var2) {
      int var4;
      if(var2 < 3) {
         byte var7 = 0;
         var4 = var1[0];
         if(var2 < 2) {
            var2 = var7;
         } else {
            var2 = var1[1];
         }

         return this.findName(var4, var2);
      } else {
         int var3 = this.calcHash(var1, var2);
         var4 = this._mainHashMask & var3;
         int var5 = this._mainHash[var4];
         if((var5 >> 8 ^ var3) << 8 == 0) {
            Name var6 = this._mainNames[var4];
            if(var6 == null || var6.equals(var1, var2)) {
               return var6;
            }
         } else if(var5 == 0) {
            return null;
         }

         var4 = var5 & 255;
         if(var4 > 0) {
            BytesToNameCanonicalizer.Bucket var8 = this._collList[var4 - 1];
            if(var8 != null) {
               return var8.find(var3, var1, var2);
            }
         }

         return null;
      }
   }

   public int hashSeed() {
      return this._hashSeed;
   }

   public BytesToNameCanonicalizer makeChild(boolean var1, boolean var2) {
      return new BytesToNameCanonicalizer(this, var2, this._hashSeed, (BytesToNameCanonicalizer.TableInfo)this._tableInfo.get());
   }

   public int maxCollisionLength() {
      return this._longestCollisionList;
   }

   public boolean maybeDirty() {
      return this._mainHashShared ^ true;
   }

   public void release() {
      if(this._parent != null && this.maybeDirty()) {
         this._parent.mergeChild(new BytesToNameCanonicalizer.TableInfo(this));
         this._mainHashShared = true;
         this._mainNamesShared = true;
         this._collListShared = true;
      }

   }

   protected void reportTooManyCollisions(int var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("Longest collision chain in symbol table (of size ");
      var2.append(this._count);
      var2.append(") now exceeds maximum, ");
      var2.append(var1);
      var2.append(" -- suspect a DoS attack based on hash collisions");
      throw new IllegalStateException(var2.toString());
   }

   public int size() {
      return this._tableInfo != null?((BytesToNameCanonicalizer.TableInfo)this._tableInfo.get()).count:this._count;
   }

   static final class TableInfo {

      public final int collCount;
      public final int collEnd;
      public final BytesToNameCanonicalizer.Bucket[] collList;
      public final int count;
      public final int longestCollisionList;
      public final int[] mainHash;
      public final int mainHashMask;
      public final Name[] mainNames;


      public TableInfo(int var1, int var2, int[] var3, Name[] var4, BytesToNameCanonicalizer.Bucket[] var5, int var6, int var7, int var8) {
         this.count = var1;
         this.mainHashMask = var2;
         this.mainHash = var3;
         this.mainNames = var4;
         this.collList = var5;
         this.collCount = var6;
         this.collEnd = var7;
         this.longestCollisionList = var8;
      }

      public TableInfo(BytesToNameCanonicalizer var1) {
         this.count = var1._count;
         this.mainHashMask = var1._mainHashMask;
         this.mainHash = var1._mainHash;
         this.mainNames = var1._mainNames;
         this.collList = var1._collList;
         this.collCount = var1._collCount;
         this.collEnd = var1._collEnd;
         this.longestCollisionList = var1._longestCollisionList;
      }
   }

   static final class Bucket {

      private final int _length;
      protected final Name _name;
      protected final BytesToNameCanonicalizer.Bucket _next;


      Bucket(Name var1, BytesToNameCanonicalizer.Bucket var2) {
         this._name = var1;
         this._next = var2;
         int var3 = 1;
         if(var2 != null) {
            var3 = 1 + var2._length;
         }

         this._length = var3;
      }

      public Name find(int var1, int var2, int var3) {
         if(this._name.hashCode() == var1 && this._name.equals(var2, var3)) {
            return this._name;
         } else {
            for(BytesToNameCanonicalizer.Bucket var4 = this._next; var4 != null; var4 = var4._next) {
               Name var5 = var4._name;
               if(var5.hashCode() == var1 && var5.equals(var2, var3)) {
                  return var5;
               }
            }

            return null;
         }
      }

      public Name find(int var1, int[] var2, int var3) {
         if(this._name.hashCode() == var1 && this._name.equals(var2, var3)) {
            return this._name;
         } else {
            for(BytesToNameCanonicalizer.Bucket var4 = this._next; var4 != null; var4 = var4._next) {
               Name var5 = var4._name;
               if(var5.hashCode() == var1 && var5.equals(var2, var3)) {
                  return var5;
               }
            }

            return null;
         }
      }

      public int length() {
         return this._length;
      }
   }
}
