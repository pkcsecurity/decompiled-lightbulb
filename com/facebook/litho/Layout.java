package com.facebook.litho;

import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.InternalNode;

final class Layout {

   static InternalNode create(ComponentContext var0, Component var1) {
      return create(var0, var1, 0, 0);
   }

   static InternalNode create(ComponentContext var0, Component var1, @AttrRes int var2, @StyleRes int var3) {
      return var1 == null?ComponentContext.NULL_LAYOUT:var0.newLayoutBuilder(var1, var2, var3);
   }
}
