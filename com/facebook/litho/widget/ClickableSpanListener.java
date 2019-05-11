package com.facebook.litho.widget;

import android.text.style.ClickableSpan;
import android.view.View;
import com.facebook.litho.widget.LongClickableSpan;

public interface ClickableSpanListener {

   boolean onClick(ClickableSpan var1, View var2);

   boolean onLongClick(LongClickableSpan var1, View var2);
}
