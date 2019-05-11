package com.facebook.react.flat;

import android.view.View;
import com.facebook.react.views.viewpager.ReactViewPager;
import com.facebook.react.views.viewpager.ReactViewPagerManager;
import java.util.List;

public class RCTViewPagerManager extends ReactViewPagerManager {

   static final String REACT_CLASS = "AndroidViewPager";


   public void addViews(ReactViewPager var1, List<View> var2) {
      var1.setViews(var2);
   }

   public void removeAllViews(ReactViewPager var1) {
      var1.removeAllViewsFromAdapter();
   }
}
