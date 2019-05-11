package com.facebook.react.uimanager;

import java.util.Comparator;

class ViewAtIndex {

   public static Comparator<ViewAtIndex> COMPARATOR = new Comparator() {
      public int compare(ViewAtIndex var1, ViewAtIndex var2) {
         return var1.mIndex - var2.mIndex;
      }
   };
   public final int mIndex;
   public final int mTag;


   public ViewAtIndex(int var1, int var2) {
      this.mTag = var1;
      this.mIndex = var2;
   }
}
