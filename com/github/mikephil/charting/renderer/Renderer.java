package com.github.mikephil.charting.renderer;

import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class Renderer {

   protected ViewPortHandler mViewPortHandler;


   public Renderer(ViewPortHandler var1) {
      this.mViewPortHandler = var1;
   }
}
