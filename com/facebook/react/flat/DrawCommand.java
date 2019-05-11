package com.facebook.react.flat;

import android.graphics.Canvas;
import com.facebook.react.flat.FlatViewGroup;

public abstract class DrawCommand {

   static final DrawCommand[] EMPTY_ARRAY = new DrawCommand[0];


   abstract void debugDraw(FlatViewGroup var1, Canvas var2);

   abstract void draw(FlatViewGroup var1, Canvas var2);

   abstract float getBottom();

   abstract float getLeft();

   abstract float getRight();

   abstract float getTop();
}
