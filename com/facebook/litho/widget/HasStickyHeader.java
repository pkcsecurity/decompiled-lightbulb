package com.facebook.litho.widget;

import com.facebook.litho.ComponentTree;
import com.facebook.litho.widget.ViewportInfo;

interface HasStickyHeader extends ViewportInfo {

   ComponentTree getComponentForStickyHeaderAt(int var1);

   boolean isSticky(int var1);

   boolean isValidPosition(int var1);
}
