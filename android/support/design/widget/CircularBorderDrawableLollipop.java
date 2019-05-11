package android.support.design.widget;

import android.graphics.Outline;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.design.widget.CircularBorderDrawable;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class CircularBorderDrawableLollipop extends CircularBorderDrawable {

   public void getOutline(Outline var1) {
      this.copyBounds(this.rect);
      var1.setOval(this.rect);
   }
}
