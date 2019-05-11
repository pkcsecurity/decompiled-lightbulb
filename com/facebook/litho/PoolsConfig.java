package com.facebook.litho;

import com.facebook.litho.InternalNode;
import com.facebook.yoga.YogaConfig;
import com.facebook.yoga.YogaNode;
import javax.annotation.Nullable;

public class PoolsConfig {

   public static int sDiffNodeSize;
   public static int sDisplayListContainerSize;
   @Nullable
   public static volatile PoolsConfig.InternalNodeFactory sInternalNodeFactory;
   public static int sInternalNodeSize;
   public static int sLayoutOutputSize;
   public static int sLayoutStateSize;
   public static int sNodeInfoSize;
   @Nullable
   public static volatile PoolsConfig.YogaNodeFactory sYogaNodeFactory;
   public static int sYogaNodeSize;



   public interface InternalNodeFactory {

      InternalNode create();
   }

   public interface YogaNodeFactory {

      YogaNode create();

      YogaNode create(YogaConfig var1);
   }
}
