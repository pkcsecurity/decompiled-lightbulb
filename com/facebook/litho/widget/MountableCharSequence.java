package com.facebook.litho.widget;

import android.graphics.drawable.Drawable;

public interface MountableCharSequence extends CharSequence {

   void onMount(Drawable var1);

   void onUnmount(Drawable var1);
}
