package android.support.design.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.R;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;

public class TabItem extends View {

   public final int customLayout;
   public final Drawable icon;
   public final CharSequence text;


   public TabItem(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public TabItem(Context var1, AttributeSet var2) {
      super(var1, var2);
      TintTypedArray var3 = TintTypedArray.obtainStyledAttributes(var1, var2, R.styleable.TabItem);
      this.text = var3.getText(R.styleable.TabItem_android_text);
      this.icon = var3.getDrawable(R.styleable.TabItem_android_icon);
      this.customLayout = var3.getResourceId(R.styleable.TabItem_android_layout, 0);
      var3.recycle();
   }
}
