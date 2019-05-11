package com.facebook.react.flat;

import com.facebook.react.flat.FlatShadowNode;

final class FlatRootShadowNode extends FlatShadowNode {

   FlatRootShadowNode() {
      this.forceMountToView();
      this.signalBackingViewIsCreated();
   }
}
