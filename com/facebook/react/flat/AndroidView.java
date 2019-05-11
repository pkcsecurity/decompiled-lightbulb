package com.facebook.react.flat;


interface AndroidView {

   float getPadding(int var1);

   boolean isPaddingChanged();

   boolean needsCustomLayoutForChildren();

   void resetPaddingChanged();
}
