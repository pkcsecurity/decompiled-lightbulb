package com.facebook.litho.viewcompat;

import android.view.View;

public interface ViewBinder<V extends View> {

   void bind(V var1);

   void prepare();

   void unbind(V var1);
}
