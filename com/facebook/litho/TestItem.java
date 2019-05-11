package com.facebook.litho;

import android.graphics.Rect;
import android.support.annotation.VisibleForTesting;
import com.facebook.litho.ComponentHost;
import com.facebook.litho.ComponentHostUtils;
import com.facebook.proguard.annotations.DoNotStrip;
import java.util.Collections;
import java.util.List;

@DoNotStrip
public class TestItem {

   private TestItem.AcquireKey mAcquireKey;
   private final Rect mBounds = new Rect();
   private Object mContent;
   private ComponentHost mHost;
   private String mTestKey;


   @DoNotStrip
   public TestItem.AcquireKey getAcquireKey() {
      return this.mAcquireKey;
   }

   @VisibleForTesting
   @DoNotStrip
   public Rect getBounds() {
      return this.mBounds;
   }

   Object getContent() {
      return this.mContent;
   }

   @VisibleForTesting
   @DoNotStrip
   public ComponentHost getHost() {
      return this.mHost;
   }

   @VisibleForTesting
   @DoNotStrip
   public String getTestKey() {
      return this.mTestKey;
   }

   @VisibleForTesting
   @DoNotStrip
   public String getTextContent() {
      List var3 = this.getTextItems();
      StringBuilder var4 = new StringBuilder();
      int var2 = var3.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         var4.append((CharSequence)var3.get(var1));
      }

      return var4.toString();
   }

   public List<CharSequence> getTextItems() {
      return ComponentHostUtils.extractTextContent(Collections.singletonList(this.mContent)).getTextItems();
   }

   void release() {
      this.mTestKey = null;
      this.mBounds.setEmpty();
      this.mHost = null;
      this.mAcquireKey = null;
   }

   @DoNotStrip
   void setAcquired() {
      this.mAcquireKey = new TestItem.AcquireKey();
   }

   void setBounds(int var1, int var2, int var3, int var4) {
      this.mBounds.set(var1, var2, var3, var4);
   }

   void setBounds(Rect var1) {
      this.mBounds.set(var1);
   }

   void setContent(Object var1) {
      this.mContent = var1;
   }

   void setHost(ComponentHost var1) {
      this.mHost = var1;
   }

   void setTestKey(String var1) {
      this.mTestKey = var1;
   }

   @DoNotStrip
   public static final class AcquireKey {

   }
}
