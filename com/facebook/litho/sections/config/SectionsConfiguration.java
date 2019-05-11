package com.facebook.litho.sections.config;

import com.facebook.litho.sections.logger.SectionsDebugLogger;
import java.util.List;

public class SectionsConfiguration {

   public static List<SectionsDebugLogger> LOGGERS;
   public static boolean sectionComponentsAsyncPropUpdates;
   public static boolean sectionComponentsAsyncStateUpdates;
   public static boolean trimDataDiffSectionHeadAndTail;
   public static boolean trimSameInstancesOnly;
   public static boolean useBackgroundChangeSets;


}
