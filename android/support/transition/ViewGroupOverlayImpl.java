package android.support.transition;

import android.support.annotation.NonNull;
import android.support.transition.ViewOverlayImpl;
import android.view.View;

interface ViewGroupOverlayImpl extends ViewOverlayImpl {

   void add(@NonNull View var1);

   void remove(@NonNull View var1);
}
