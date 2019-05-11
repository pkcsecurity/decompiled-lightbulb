package com.facebook.react.common;


public class LongArray {

   private static final double INNER_ARRAY_GROWTH_FACTOR = 1.8D;
   private long[] mArray;
   private int mLength;


   private LongArray(int var1) {
      this.mArray = new long[var1];
      this.mLength = 0;
   }

   public static LongArray createWithInitialCapacity(int var0) {
      return new LongArray(var0);
   }

   private void growArrayIfNeeded() {
      if(this.mLength == this.mArray.length) {
         long[] var1 = new long[Math.max(this.mLength + 1, (int)((double)this.mLength * 1.8D))];
         System.arraycopy(this.mArray, 0, var1, 0, this.mLength);
         this.mArray = var1;
      }

   }

   public void add(long var1) {
      this.growArrayIfNeeded();
      long[] var4 = this.mArray;
      int var3 = this.mLength;
      this.mLength = var3 + 1;
      var4[var3] = var1;
   }

   public void dropTail(int var1) {
      if(var1 > this.mLength) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Trying to drop ");
         var2.append(var1);
         var2.append(" items from array of length ");
         var2.append(this.mLength);
         throw new IndexOutOfBoundsException(var2.toString());
      } else {
         this.mLength -= var1;
      }
   }

   public long get(int var1) {
      if(var1 >= this.mLength) {
         StringBuilder var2 = new StringBuilder();
         var2.append("");
         var2.append(var1);
         var2.append(" >= ");
         var2.append(this.mLength);
         throw new IndexOutOfBoundsException(var2.toString());
      } else {
         return this.mArray[var1];
      }
   }

   public boolean isEmpty() {
      return this.mLength == 0;
   }

   public void set(int var1, long var2) {
      if(var1 >= this.mLength) {
         StringBuilder var4 = new StringBuilder();
         var4.append("");
         var4.append(var1);
         var4.append(" >= ");
         var4.append(this.mLength);
         throw new IndexOutOfBoundsException(var4.toString());
      } else {
         this.mArray[var1] = var2;
      }
   }

   public int size() {
      return this.mLength;
   }
}
