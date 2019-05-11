package com.facebook.react.views.text;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.views.text.TextInlineImageSpan;

public abstract class ReactTextInlineImageShadowNode extends LayoutShadowNode {

   public abstract TextInlineImageSpan buildInlineImageSpan();
}
