package com.facebook.litho.sections;

import android.util.SparseArray;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.sections.SectionTree;
import com.facebook.litho.sections.logger.SectionsDebugLogger;
import com.facebook.litho.widget.ChangeSetCompleteCallback;
import com.facebook.litho.widget.RenderInfo;
import com.facebook.litho.widget.SmoothScrollAlignmentType;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class BatchedTarget implements SectionTree.Target {

   private static final boolean ENABLE_LOGGER = ComponentsConfiguration.isDebugModeEnabled;
   private static final int TYPE_NONE = Integer.MAX_VALUE;
   private final SparseArray<RenderInfo> mComponentInfoSparseArray = new SparseArray();
   private int mLastEventCount = -1;
   private int mLastEventPosition = -1;
   private int mLastEventType = Integer.MAX_VALUE;
   private final String mSectionTreeTag;
   private final SectionsDebugLogger mSectionsDebugLogger;
   private final SectionTree.Target mTarget;


   BatchedTarget(SectionTree.Target var1, SectionsDebugLogger var2, String var3) {
      this.mTarget = var1;
      this.mSectionsDebugLogger = var2;
      this.mSectionTreeTag = var3;
   }

   private static List<RenderInfo> collectComponentInfos(int var0, int var1, SparseArray<RenderInfo> var2) {
      ArrayList var4 = new ArrayList(var1);

      for(int var3 = var0; var3 < var0 + var1; ++var3) {
         RenderInfo var5 = (RenderInfo)var2.get(var3);
         if(var5 == null) {
            throw new IllegalStateException(String.format(Locale.US, "Index %d does not have a corresponding renderInfo", new Object[]{Integer.valueOf(var3)}));
         }

         var4.add(var5);
      }

      return var4;
   }

   private void logDeleteIterative(int var1, int var2) {
      for(int var3 = 0; var3 < var2; ++var3) {
         this.mSectionsDebugLogger.logDelete(this.mSectionTreeTag, var1 + var3, Thread.currentThread().getName());
      }

   }

   private void logInsertIterative(int var1, List<RenderInfo> var2) {
      for(int var3 = 0; var3 < var2.size(); ++var3) {
         this.mSectionsDebugLogger.logInsert(this.mSectionTreeTag, var1 + var3, (RenderInfo)var2.get(var3), Thread.currentThread().getName());
      }

   }

   private void logUpdateIterative(int var1, List<RenderInfo> var2) {
      for(int var3 = 0; var3 < var2.size(); ++var3) {
         this.mSectionsDebugLogger.logUpdate(this.mSectionTreeTag, var1 + var3, (RenderInfo)var2.get(var3), Thread.currentThread().getName());
      }

   }

   private void maybeLogRequestFocus(int var1) {
      if(ENABLE_LOGGER && this.mComponentInfoSparseArray.size() != 0) {
         this.mSectionsDebugLogger.logRequestFocus(this.mSectionTreeTag, var1, (RenderInfo)this.mComponentInfoSparseArray.get(var1), Thread.currentThread().getName());
      }

   }

   private void maybeLogRequestFocusWithOffset(int var1, int var2) {
      if(ENABLE_LOGGER && this.mComponentInfoSparseArray.size() != 0) {
         this.mSectionsDebugLogger.logRequestFocusWithOffset(this.mSectionTreeTag, var1, var2, (RenderInfo)this.mComponentInfoSparseArray.get(var1), Thread.currentThread().getName());
      }

   }

   public void delete(int var1) {
      if(this.mLastEventType == 3 && this.mLastEventPosition >= var1 && this.mLastEventPosition <= var1 + 1) {
         ++this.mLastEventCount;
         this.mLastEventPosition = var1;
      } else {
         this.dispatchLastEvent();
         this.mLastEventPosition = var1;
         this.mLastEventCount = 1;
         this.mLastEventType = 3;
      }
   }

   public void deleteRange(int var1, int var2) {
      this.dispatchLastEvent();
      this.mTarget.deleteRange(var1, var2);
   }

   void dispatchLastEvent() {
      if(this.mLastEventType != Integer.MAX_VALUE) {
         List var1;
         switch(this.mLastEventType) {
         case 1:
            var1 = collectComponentInfos(this.mLastEventPosition, this.mLastEventCount, this.mComponentInfoSparseArray);
            if(this.mLastEventCount > 1) {
               this.mTarget.insertRange(this.mLastEventPosition, this.mLastEventCount, var1);
               if(ENABLE_LOGGER) {
                  this.logInsertIterative(this.mLastEventPosition, var1);
               }
            } else {
               this.mTarget.insert(this.mLastEventPosition, (RenderInfo)this.mComponentInfoSparseArray.get(this.mLastEventPosition));
               if(ENABLE_LOGGER) {
                  this.mSectionsDebugLogger.logInsert(this.mSectionTreeTag, this.mLastEventPosition, (RenderInfo)this.mComponentInfoSparseArray.get(this.mLastEventPosition), Thread.currentThread().getName());
               }
            }
            break;
         case 2:
            var1 = collectComponentInfos(this.mLastEventPosition, this.mLastEventCount, this.mComponentInfoSparseArray);
            if(this.mLastEventCount > 1) {
               this.mTarget.updateRange(this.mLastEventPosition, this.mLastEventCount, var1);
               if(ENABLE_LOGGER) {
                  this.logUpdateIterative(this.mLastEventPosition, var1);
               }
            } else {
               this.mTarget.update(this.mLastEventPosition, (RenderInfo)this.mComponentInfoSparseArray.get(this.mLastEventPosition));
               if(ENABLE_LOGGER) {
                  this.mSectionsDebugLogger.logUpdate(this.mSectionTreeTag, this.mLastEventPosition, (RenderInfo)this.mComponentInfoSparseArray.get(this.mLastEventPosition), Thread.currentThread().getName());
               }
            }
            break;
         case 3:
            if(this.mLastEventCount > 1) {
               this.mTarget.deleteRange(this.mLastEventPosition, this.mLastEventCount);
               if(ENABLE_LOGGER) {
                  this.logDeleteIterative(this.mLastEventPosition, this.mLastEventCount);
               }
            } else {
               this.mTarget.delete(this.mLastEventPosition);
               if(ENABLE_LOGGER) {
                  this.mSectionsDebugLogger.logDelete(this.mSectionTreeTag, this.mLastEventPosition, Thread.currentThread().getName());
               }
            }
         }

         this.mLastEventType = Integer.MAX_VALUE;
         this.mComponentInfoSparseArray.clear();
      }
   }

   public void insert(int var1, RenderInfo var2) {
      if(this.mLastEventType == 1 && var1 >= this.mLastEventPosition && var1 <= this.mLastEventPosition + this.mLastEventCount && var1 >= this.mLastEventPosition + this.mLastEventCount) {
         ++this.mLastEventCount;
         this.mLastEventPosition = Math.min(var1, this.mLastEventPosition);
         this.mComponentInfoSparseArray.put(var1, var2);
      } else {
         this.dispatchLastEvent();
         this.mLastEventPosition = var1;
         this.mLastEventCount = 1;
         this.mLastEventType = 1;
         this.mComponentInfoSparseArray.put(var1, var2);
      }
   }

   public void insertRange(int var1, int var2, List<RenderInfo> var3) {
      this.dispatchLastEvent();
      this.mTarget.insertRange(var1, var2, var3);
      if(ENABLE_LOGGER) {
         this.logInsertIterative(var1, var3);
      }

   }

   public void move(int var1, int var2) {
      this.dispatchLastEvent();
      this.mTarget.move(var1, var2);
      if(ENABLE_LOGGER) {
         this.mSectionsDebugLogger.logMove(this.mSectionTreeTag, var1, var2, Thread.currentThread().getName());
      }

   }

   public void notifyChangeSetComplete(boolean var1, ChangeSetCompleteCallback var2) {
      this.mTarget.notifyChangeSetComplete(var1, var2);
   }

   public void requestFocus(int var1) {
      this.mTarget.requestFocus(var1);
      this.maybeLogRequestFocus(var1);
   }

   public void requestFocusWithOffset(int var1, int var2) {
      this.mTarget.requestFocusWithOffset(var1, var2);
      this.maybeLogRequestFocusWithOffset(var1, var2);
   }

   public void requestSmoothFocus(int var1, int var2, SmoothScrollAlignmentType var3) {
      this.mTarget.requestSmoothFocus(var1, var2, var3);
      this.maybeLogRequestFocus(var1);
   }

   public boolean supportsBackgroundChangeSets() {
      return this.mTarget.supportsBackgroundChangeSets();
   }

   public void update(int var1, RenderInfo var2) {
      if(this.mLastEventType == 2 && var1 <= this.mLastEventPosition + this.mLastEventCount) {
         int var3 = var1 + 1;
         if(var3 >= this.mLastEventPosition) {
            int var4 = this.mLastEventPosition;
            int var5 = this.mLastEventCount;
            this.mLastEventPosition = Math.min(var1, this.mLastEventPosition);
            this.mLastEventCount = Math.max(var4 + var5, var3) - this.mLastEventPosition;
            this.mComponentInfoSparseArray.put(var1, var2);
            return;
         }
      }

      this.dispatchLastEvent();
      this.mLastEventPosition = var1;
      this.mLastEventCount = 1;
      this.mLastEventType = 2;
      this.mComponentInfoSparseArray.put(var1, var2);
   }

   public void updateRange(int var1, int var2, List<RenderInfo> var3) {
      this.dispatchLastEvent();
      this.mTarget.updateRange(var1, var2, var3);
      if(ENABLE_LOGGER) {
         this.logUpdateIterative(var1, var3);
      }

   }
}
