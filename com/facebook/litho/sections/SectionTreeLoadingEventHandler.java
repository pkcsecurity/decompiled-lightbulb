package com.facebook.litho.sections;

import com.facebook.litho.EventHandler;
import com.facebook.litho.HasEventDispatcher;
import com.facebook.litho.sections.LoadingEvent;
import com.facebook.litho.sections.SectionTree;
import java.lang.ref.WeakReference;

public class SectionTreeLoadingEventHandler extends EventHandler<LoadingEvent> {

   private static final int INVALID_ID = -1;
   private final WeakReference<SectionTree> mSectionTree;


   SectionTreeLoadingEventHandler(SectionTree var1) {
      super((HasEventDispatcher)null, -1);
      this.mSectionTree = new WeakReference(var1);
   }

   SectionTreeLoadingEventHandler(SectionTree var1, int var2, Object[] var3) {
      super((HasEventDispatcher)null, var2, var3);
      this.mSectionTree = new WeakReference(var1);
   }

   public void dispatchEvent(LoadingEvent var1) {
      SectionTree var2 = (SectionTree)this.mSectionTree.get();
      if(var2 != null) {
         var2.dispatchLoadingEvent(var1);
      }
   }
}
