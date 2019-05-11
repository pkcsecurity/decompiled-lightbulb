package com.facebook.litho.displaylist;

import android.graphics.Canvas;
import com.facebook.litho.displaylist.DisplayListException;

interface PlatformDisplayList {

   void clear() throws DisplayListException;

   void draw(Canvas var1) throws DisplayListException;

   void end(Canvas var1) throws DisplayListException;

   boolean isValid();

   void print(Canvas var1) throws DisplayListException;

   void setBounds(int var1, int var2, int var3, int var4) throws DisplayListException;

   void setTranslationX(float var1) throws DisplayListException;

   void setTranslationY(float var1) throws DisplayListException;

   Canvas start(int var1, int var2) throws DisplayListException;
}
