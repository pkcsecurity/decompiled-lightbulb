package com.facebook.litho.widget;


public interface ViewportInfo {

   int findFirstFullyVisibleItemPosition();

   int findFirstVisibleItemPosition();

   int findLastFullyVisibleItemPosition();

   int findLastVisibleItemPosition();

   int getItemCount();

   public @interface State {

      int DATA_CHANGES = 1;
      int SCROLLING = 0;

   }

   public interface ViewportChanged {

      void viewportChanged(int var1, int var2, int var3, int var4, @ViewportInfo.State int var5);
   }
}
