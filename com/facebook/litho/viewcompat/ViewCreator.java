package com.facebook.litho.viewcompat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface ViewCreator<V extends View> {

   V createView(Context var1, ViewGroup var2);
}
