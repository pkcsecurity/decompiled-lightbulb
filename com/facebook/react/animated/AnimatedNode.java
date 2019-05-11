package com.facebook.react.animated;

import com.facebook.infer.annotation.Assertions;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

abstract class AnimatedNode {

   private static final int DEFAULT_ANIMATED_NODE_CHILD_COUNT = 1;
   public static final int INITIAL_BFS_COLOR = 0;
   int mActiveIncomingNodes = 0;
   int mBFSColor = 0;
   @Nullable
   List<AnimatedNode> mChildren;
   int mTag = -1;


   public final void addChild(AnimatedNode var1) {
      if(this.mChildren == null) {
         this.mChildren = new ArrayList(1);
      }

      ((List)Assertions.assertNotNull(this.mChildren)).add(var1);
      var1.onAttachedToNode(this);
   }

   public void onAttachedToNode(AnimatedNode var1) {}

   public void onDetachedFromNode(AnimatedNode var1) {}

   public final void removeChild(AnimatedNode var1) {
      if(this.mChildren != null) {
         var1.onDetachedFromNode(this);
         this.mChildren.remove(var1);
      }
   }

   public void update() {}
}
