package com.facebook.litho.fresco;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.litho.ImageContent;
import com.facebook.litho.Touchable;
import com.facebook.litho.fresco.NoOpDrawable;
import java.util.Collections;
import java.util.List;

public class DraweeDrawable<DH extends Object & DraweeHierarchy> extends ForwardingDrawable implements ImageContent, Touchable {

   private final DraweeHolder<DH> mDraweeHolder;
   private final Drawable mNoOpDrawable = new NoOpDrawable();


   public DraweeDrawable(Context var1, DH var2) {
      super((Drawable)null);
      this.setCurrent(this.mNoOpDrawable);
      this.mDraweeHolder = DraweeHolder.create(var2, var1);
   }

   public void draw(Canvas var1) {
      this.mDraweeHolder.onDraw();
      super.draw(var1);
   }

   public DraweeController getController() {
      return this.mDraweeHolder.getController();
   }

   public DH getDraweeHierarchy() {
      return this.mDraweeHolder.getHierarchy();
   }

   public List<Drawable> getImageItems() {
      return Collections.singletonList(this);
   }

   public void mount() {
      this.setDrawable(this.mDraweeHolder.getTopLevelDrawable());
      this.mDraweeHolder.onAttach();
   }

   public boolean onTouchEvent(MotionEvent var1, View var2) {
      return this.mDraweeHolder.onTouchEvent(var1);
   }

   public void setController(DraweeController var1) {
      if(this.mDraweeHolder.getController() != var1) {
         this.mDraweeHolder.setController(var1);
      }
   }

   public boolean shouldHandleTouchEvent(MotionEvent var1) {
      return true;
   }

   public void unmount() {
      this.mDraweeHolder.onDetach();
      this.setDrawable(this.mNoOpDrawable);
   }
}
