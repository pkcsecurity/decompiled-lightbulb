package android.support.design.expandable;

import android.support.annotation.IdRes;
import android.support.design.expandable.ExpandableWidget;

public interface ExpandableTransformationWidget extends ExpandableWidget {

   @IdRes
   int getExpandedComponentIdHint();

   void setExpandedComponentIdHint(@IdRes int var1);
}
