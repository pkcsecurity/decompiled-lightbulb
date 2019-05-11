package com.facebook.react.flat;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.flat.FlatShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import javax.annotation.Nullable;

final class MoveProxy {

   private ReactShadowNode[] mChildren = new ReactShadowNodeImpl[4];
   private int[] mMapping = new int[8];
   @Nullable
   private ReadableArray mMoveTo;
   private int mSize;


   private static int k(int var0) {
      return var0 * 2;
   }

   private int moveFromToIndex(int var1) {
      return this.mMapping[k(var1)];
   }

   private int moveFromToValue(int var1) {
      return this.mMapping[v(var1)];
   }

   private static int moveToToIndex(int var0) {
      return var0;
   }

   private int moveToToValue(int var1) {
      return ((ReadableArray)Assertions.assumeNotNull(this.mMoveTo)).getInt(var1);
   }

   private void setKeyValue(int var1, int var2, int var3) {
      this.mMapping[k(var1)] = var2;
      this.mMapping[v(var1)] = var3;
   }

   private void setSize(int var1) {
      for(int var2 = var1; var2 < this.mSize; ++var2) {
         this.mChildren[var2] = null;
      }

      this.mSize = var1;
   }

   private static int v(int var0) {
      return var0 * 2 + 1;
   }

   public ReactShadowNode getChildMoveTo(int var1) {
      return this.mChildren[moveToToIndex(var1)];
   }

   public int getMoveFrom(int var1) {
      return this.moveFromToValue(var1);
   }

   public int getMoveTo(int var1) {
      return this.moveToToValue(var1);
   }

   public void setChildMoveFrom(int var1, ReactShadowNode var2) {
      this.mChildren[this.moveFromToIndex(var1)] = var2;
   }

   public void setup(ReadableArray var1, ReadableArray var2) {
      this.mMoveTo = var2;
      if(var1 == null) {
         this.setSize(0);
      } else {
         int var5 = var1.size();
         int var3 = var5 + var5;
         if(this.mMapping.length < var3) {
            this.mMapping = new int[var3];
            this.mChildren = new FlatShadowNode[var5];
         }

         this.setSize(var5);
         this.setKeyValue(0, 0, var1.getInt(0));

         for(var3 = 1; var3 < var5; ++var3) {
            int var6 = var1.getInt(var3);

            int var4;
            for(var4 = var3 - 1; var4 >= 0 && this.moveFromToValue(var4) >= var6; --var4) {
               this.setKeyValue(var4 + 1, this.moveFromToIndex(var4), this.moveFromToValue(var4));
            }

            this.setKeyValue(var4 + 1, var3, var6);
         }

      }
   }

   public int size() {
      return this.mSize;
   }
}
