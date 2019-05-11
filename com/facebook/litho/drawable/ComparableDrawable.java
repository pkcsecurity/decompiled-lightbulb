package com.facebook.litho.drawable;

import android.graphics.drawable.Drawable;

public abstract class ComparableDrawable extends Drawable {

   public abstract boolean isEquivalentTo(ComparableDrawable var1);
}
