package com.github.mikephil.charting.utils;

import java.util.List;

public class ObjectPool<T extends ObjectPool.Poolable> {

   private static int ids;
   private int desiredCapacity;
   private T modelObject;
   private Object[] objects;
   private int objectsPointer;
   private int poolId;
   private float replenishPercentage;


   private ObjectPool(int var1, T var2) {
      if(var1 <= 0) {
         throw new IllegalArgumentException("Object Pool must be instantiated with a capacity greater than 0!");
      } else {
         this.desiredCapacity = var1;
         this.objects = new Object[this.desiredCapacity];
         this.objectsPointer = 0;
         this.modelObject = var2;
         this.replenishPercentage = 1.0F;
         this.refillPool();
      }
   }

   public static ObjectPool create(int var0, ObjectPool.Poolable var1) {
      synchronized(ObjectPool.class){}

      ObjectPool var4;
      try {
         var4 = new ObjectPool(var0, var1);
         var4.poolId = ids++;
      } finally {
         ;
      }

      return var4;
   }

   private void refillPool() {
      this.refillPool(this.replenishPercentage);
   }

   private void refillPool(float var1) {
      int var3 = (int)((float)this.desiredCapacity * var1);
      int var2;
      if(var3 < 1) {
         var2 = 1;
      } else {
         var2 = var3;
         if(var3 > this.desiredCapacity) {
            var2 = this.desiredCapacity;
         }
      }

      for(var3 = 0; var3 < var2; ++var3) {
         this.objects[var3] = this.modelObject.instantiate();
      }

      this.objectsPointer = var2 - 1;
   }

   private void resizePool() {
      int var2 = this.desiredCapacity;
      this.desiredCapacity *= 2;
      Object[] var3 = new Object[this.desiredCapacity];

      for(int var1 = 0; var1 < var2; ++var1) {
         var3[var1] = this.objects[var1];
      }

      this.objects = var3;
   }

   public T get() {
      synchronized(this){}

      ObjectPool.Poolable var1;
      try {
         if(this.objectsPointer == -1 && this.replenishPercentage > 0.0F) {
            this.refillPool();
         }

         var1 = (ObjectPool.Poolable)this.objects[this.objectsPointer];
         var1.currentOwnerId = ObjectPool.Poolable.NO_OWNER;
         --this.objectsPointer;
      } finally {
         ;
      }

      return var1;
   }

   public int getPoolCapacity() {
      return this.objects.length;
   }

   public int getPoolCount() {
      return this.objectsPointer + 1;
   }

   public int getPoolId() {
      return this.poolId;
   }

   public float getReplenishPercentage() {
      return this.replenishPercentage;
   }

   public void recycle(T var1) {
      synchronized(this){}

      try {
         if(var1.currentOwnerId != ObjectPool.Poolable.NO_OWNER) {
            if(var1.currentOwnerId == this.poolId) {
               throw new IllegalArgumentException("The object passed is already stored in this pool!");
            }

            StringBuilder var2 = new StringBuilder();
            var2.append("The object to recycle already belongs to poolId ");
            var2.append(var1.currentOwnerId);
            var2.append(".  Object cannot belong to two different pool instances simultaneously!");
            throw new IllegalArgumentException(var2.toString());
         }

         ++this.objectsPointer;
         if(this.objectsPointer >= this.objects.length) {
            this.resizePool();
         }

         var1.currentOwnerId = this.poolId;
         this.objects[this.objectsPointer] = var1;
      } finally {
         ;
      }

   }

   public void recycle(List<T> param1) {
      // $FF: Couldn't be decompiled
   }

   public void setReplenishPercentage(float var1) {
      float var2;
      if(var1 > 1.0F) {
         var2 = 1.0F;
      } else {
         var2 = var1;
         if(var1 < 0.0F) {
            var2 = 0.0F;
         }
      }

      this.replenishPercentage = var2;
   }

   public abstract static class Poolable {

      public static int NO_OWNER;
      int currentOwnerId;


      public Poolable() {
         this.currentOwnerId = NO_OWNER;
      }

      public abstract ObjectPool.Poolable instantiate();
   }
}
