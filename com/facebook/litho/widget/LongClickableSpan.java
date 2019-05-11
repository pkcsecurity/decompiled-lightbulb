package com.facebook.litho.widget;

import android.text.style.ClickableSpan;
import android.view.View;

public abstract class LongClickableSpan extends ClickableSpan {

   public abstract boolean onLongClick(View var1);
}
