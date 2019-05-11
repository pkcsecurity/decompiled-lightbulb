package com.facebook.imagepipeline.image;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imageutils.BitmapUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class CloseableAnimatedBitmap extends CloseableBitmap {

   @GuardedBy
   private List<CloseableReference<Bitmap>> mBitmapReferences;
   private volatile List<Bitmap> mBitmaps;
   private volatile List<Integer> mDurations;


   public CloseableAnimatedBitmap(List<CloseableReference<Bitmap>> var1, List<Integer> var2) {
      Preconditions.checkNotNull(var1);
      int var3 = var1.size();
      boolean var5 = false;
      boolean var4;
      if(var3 >= 1) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4, "Need at least 1 frame!");
      this.mBitmapReferences = new ArrayList(var1.size());
      this.mBitmaps = new ArrayList(var1.size());
      Iterator var7 = var1.iterator();

      while(var7.hasNext()) {
         CloseableReference var6 = (CloseableReference)var7.next();
         this.mBitmapReferences.add(var6.clone());
         this.mBitmaps.add(var6.get());
      }

      this.mDurations = (List)Preconditions.checkNotNull(var2);
      var4 = var5;
      if(this.mDurations.size() == this.mBitmaps.size()) {
         var4 = true;
      }

      Preconditions.checkState(var4, "Arrays length mismatch!");
   }

   public CloseableAnimatedBitmap(List<Bitmap> var1, List<Integer> var2, ResourceReleaser<Bitmap> var3) {
      Preconditions.checkNotNull(var1);
      int var4 = var1.size();
      boolean var6 = false;
      boolean var5;
      if(var4 >= 1) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkState(var5, "Need at least 1 frame!");
      this.mBitmaps = new ArrayList(var1.size());
      this.mBitmapReferences = new ArrayList(var1.size());
      Iterator var8 = var1.iterator();

      while(var8.hasNext()) {
         Bitmap var7 = (Bitmap)var8.next();
         this.mBitmapReferences.add(CloseableReference.of(var7, var3));
         this.mBitmaps.add(var7);
      }

      this.mDurations = (List)Preconditions.checkNotNull(var2);
      var5 = var6;
      if(this.mDurations.size() == this.mBitmaps.size()) {
         var5 = true;
      }

      Preconditions.checkState(var5, "Arrays length mismatch!");
   }

   public void close() {
      // $FF: Couldn't be decompiled
   }

   public List<Bitmap> getBitmaps() {
      return this.mBitmaps;
   }

   public List<Integer> getDurations() {
      return this.mDurations;
   }

   public int getHeight() {
      List var1 = this.mBitmaps;
      return var1 == null?0:((Bitmap)var1.get(0)).getHeight();
   }

   public int getSizeInBytes() {
      List var1 = this.mBitmaps;
      return var1 != null?(var1.size() == 0?0:BitmapUtil.getSizeInBytes((Bitmap)var1.get(0)) * var1.size()):0;
   }

   public Bitmap getUnderlyingBitmap() {
      List var1 = this.mBitmaps;
      return var1 != null?(Bitmap)var1.get(0):null;
   }

   public int getWidth() {
      List var1 = this.mBitmaps;
      return var1 == null?0:((Bitmap)var1.get(0)).getWidth();
   }

   public boolean isClosed() {
      synchronized(this){}
      boolean var4 = false;

      List var2;
      try {
         var4 = true;
         var2 = this.mBitmaps;
         var4 = false;
      } finally {
         if(var4) {
            ;
         }
      }

      boolean var1;
      if(var2 == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
