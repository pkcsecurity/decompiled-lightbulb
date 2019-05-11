package android.support.v7.util;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.util.BatchingListUpdateCallback;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DiffUtil {

   private static final Comparator<DiffUtil.Snake> SNAKE_COMPARATOR = new Comparator() {
      public int compare(DiffUtil.Snake var1, DiffUtil.Snake var2) {
         int var4 = var1.x - var2.x;
         int var3 = var4;
         if(var4 == 0) {
            var3 = var1.y - var2.y;
         }

         return var3;
      }
   };


   @NonNull
   public static DiffUtil.DiffResult calculateDiff(@NonNull DiffUtil.Callback var0) {
      return calculateDiff(var0, true);
   }

   @NonNull
   public static DiffUtil.DiffResult calculateDiff(@NonNull DiffUtil.Callback var0, boolean var1) {
      int var2 = var0.getOldListSize();
      int var3 = var0.getNewListSize();
      ArrayList var5 = new ArrayList();
      ArrayList var6 = new ArrayList();
      var6.add(new DiffUtil.Range(0, var2, 0, var3));
      var2 = Math.abs(var2 - var3) + var2 + var3;
      var3 = var2 * 2;
      int[] var7 = new int[var3];
      int[] var8 = new int[var3];
      ArrayList var9 = new ArrayList();

      while(!var6.isEmpty()) {
         DiffUtil.Range var10 = (DiffUtil.Range)var6.remove(var6.size() - 1);
         DiffUtil.Snake var11 = diffPartial(var0, var10.oldListStart, var10.oldListEnd, var10.newListStart, var10.newListEnd, var7, var8, var2);
         if(var11 != null) {
            if(var11.size > 0) {
               var5.add(var11);
            }

            var11.x += var10.oldListStart;
            var11.y += var10.newListStart;
            DiffUtil.Range var4;
            if(var9.isEmpty()) {
               var4 = new DiffUtil.Range();
            } else {
               var4 = (DiffUtil.Range)var9.remove(var9.size() - 1);
            }

            var4.oldListStart = var10.oldListStart;
            var4.newListStart = var10.newListStart;
            if(var11.reverse) {
               var4.oldListEnd = var11.x;
               var4.newListEnd = var11.y;
            } else if(var11.removal) {
               var4.oldListEnd = var11.x - 1;
               var4.newListEnd = var11.y;
            } else {
               var4.oldListEnd = var11.x;
               var4.newListEnd = var11.y - 1;
            }

            var6.add(var4);
            if(var11.reverse) {
               if(var11.removal) {
                  var10.oldListStart = var11.x + var11.size + 1;
                  var10.newListStart = var11.y + var11.size;
               } else {
                  var10.oldListStart = var11.x + var11.size;
                  var10.newListStart = var11.y + var11.size + 1;
               }
            } else {
               var10.oldListStart = var11.x + var11.size;
               var10.newListStart = var11.y + var11.size;
            }

            var6.add(var10);
         } else {
            var9.add(var10);
         }
      }

      Collections.sort(var5, SNAKE_COMPARATOR);
      return new DiffUtil.DiffResult(var0, var5, var7, var8, var1);
   }

   private static DiffUtil.Snake diffPartial(DiffUtil.Callback var0, int var1, int var2, int var3, int var4, int[] var5, int[] var6, int var7) {
      var2 -= var1;
      var4 -= var3;
      if(var2 >= 1 && var4 >= 1) {
         int var15 = var2 - var4;
         int var8 = (var2 + var4 + 1) / 2;
         int var9 = var7 - var8 - 1;
         int var10 = var7 + var8 + 1;
         Arrays.fill(var5, var9, var10, 0);
         Arrays.fill(var6, var9 + var15, var10 + var15, var2);
         boolean var19;
         if(var15 % 2 != 0) {
            var19 = true;
         } else {
            var19 = false;
         }

         for(int var11 = 0; var11 <= var8; ++var11) {
            int var12 = -var11;

            int var13;
            int var14;
            boolean var17;
            DiffUtil.Snake var18;
            for(var13 = var12; var13 <= var11; var13 += 2) {
               label84: {
                  if(var13 != var12) {
                     label82: {
                        if(var13 != var11) {
                           var9 = var7 + var13;
                           if(var5[var9 - 1] < var5[var9 + 1]) {
                              break label82;
                           }
                        }

                        var9 = var5[var7 + var13 - 1] + 1;
                        var17 = true;
                        break label84;
                     }
                  }

                  var9 = var5[var7 + var13 + 1];
                  var17 = false;
               }

               for(var14 = var9 - var13; var9 < var2 && var14 < var4 && var0.areItemsTheSame(var1 + var9, var3 + var14); ++var14) {
                  ++var9;
               }

               var14 = var7 + var13;
               var5[var14] = var9;
               if(var19 && var13 >= var15 - var11 + 1 && var13 <= var15 + var11 - 1 && var5[var14] >= var6[var14]) {
                  var18 = new DiffUtil.Snake();
                  var18.x = var6[var14];
                  var18.y = var18.x - var13;
                  var18.size = var5[var14] - var6[var14];
                  var18.removal = var17;
                  var18.reverse = false;
                  return var18;
               }
            }

            for(var13 = var12; var13 <= var11; var13 += 2) {
               int var16;
               label107: {
                  var16 = var13 + var15;
                  if(var16 != var11 + var15) {
                     label105: {
                        if(var16 != var12 + var15) {
                           var9 = var7 + var16;
                           if(var6[var9 - 1] < var6[var9 + 1]) {
                              break label105;
                           }
                        }

                        var9 = var6[var7 + var16 + 1] - 1;
                        var17 = true;
                        break label107;
                     }
                  }

                  var9 = var6[var7 + var16 - 1];
                  var17 = false;
               }

               for(var14 = var9 - var16; var9 > 0 && var14 > 0 && var0.areItemsTheSame(var1 + var9 - 1, var3 + var14 - 1); --var14) {
                  --var9;
               }

               var14 = var7 + var16;
               var6[var14] = var9;
               if(!var19 && var16 >= var12 && var16 <= var11 && var5[var14] >= var6[var14]) {
                  var18 = new DiffUtil.Snake();
                  var18.x = var6[var14];
                  var18.y = var18.x - var16;
                  var18.size = var5[var14] - var6[var14];
                  var18.removal = var17;
                  var18.reverse = true;
                  return var18;
               }
            }
         }

         throw new IllegalStateException("DiffUtil hit an unexpected case while trying to calculate the optimal path. Please make sure your data is not changing during the diff calculation.");
      } else {
         return null;
      }
   }

   public static class DiffResult {

      private static final int FLAG_CHANGED = 2;
      private static final int FLAG_IGNORE = 16;
      private static final int FLAG_MASK = 31;
      private static final int FLAG_MOVED_CHANGED = 4;
      private static final int FLAG_MOVED_NOT_CHANGED = 8;
      private static final int FLAG_NOT_CHANGED = 1;
      private static final int FLAG_OFFSET = 5;
      public static final int NO_POSITION = -1;
      private final DiffUtil.Callback mCallback;
      private final boolean mDetectMoves;
      private final int[] mNewItemStatuses;
      private final int mNewListSize;
      private final int[] mOldItemStatuses;
      private final int mOldListSize;
      private final List<DiffUtil.Snake> mSnakes;


      DiffResult(DiffUtil.Callback var1, List<DiffUtil.Snake> var2, int[] var3, int[] var4, boolean var5) {
         this.mSnakes = var2;
         this.mOldItemStatuses = var3;
         this.mNewItemStatuses = var4;
         Arrays.fill(this.mOldItemStatuses, 0);
         Arrays.fill(this.mNewItemStatuses, 0);
         this.mCallback = var1;
         this.mOldListSize = var1.getOldListSize();
         this.mNewListSize = var1.getNewListSize();
         this.mDetectMoves = var5;
         this.addRootSnake();
         this.findMatchingItems();
      }

      private void addRootSnake() {
         DiffUtil.Snake var1;
         if(this.mSnakes.isEmpty()) {
            var1 = null;
         } else {
            var1 = (DiffUtil.Snake)this.mSnakes.get(0);
         }

         if(var1 == null || var1.x != 0 || var1.y != 0) {
            var1 = new DiffUtil.Snake();
            var1.x = 0;
            var1.y = 0;
            var1.removal = false;
            var1.size = 0;
            var1.reverse = false;
            this.mSnakes.add(0, var1);
         }

      }

      private void dispatchAdditions(List<DiffUtil.PostponedUpdate> var1, ListUpdateCallback var2, int var3, int var4, int var5) {
         if(!this.mDetectMoves) {
            var2.onInserted(var3, var4);
         } else {
            --var4;

            for(; var4 >= 0; --var4) {
               int[] var9 = this.mNewItemStatuses;
               int var6 = var5 + var4;
               int var7 = var9[var6] & 31;
               if(var7 != 0) {
                  if(var7 != 4 && var7 != 8) {
                     if(var7 != 16) {
                        StringBuilder var11 = new StringBuilder();
                        var11.append("unknown flag for pos ");
                        var11.append(var6);
                        var11.append(" ");
                        var11.append(Long.toBinaryString((long)var7));
                        throw new IllegalStateException(var11.toString());
                     }

                     var1.add(new DiffUtil.PostponedUpdate(var6, var3, false));
                  } else {
                     int var8 = this.mNewItemStatuses[var6] >> 5;
                     var2.onMoved(removePostponedUpdate(var1, var8, true).currentPos, var3);
                     if(var7 == 4) {
                        var2.onChanged(var3, 1, this.mCallback.getChangePayload(var8, var6));
                     }
                  }
               } else {
                  var2.onInserted(var3, 1);

                  DiffUtil.PostponedUpdate var10;
                  for(Iterator var12 = var1.iterator(); var12.hasNext(); ++var10.currentPos) {
                     var10 = (DiffUtil.PostponedUpdate)var12.next();
                  }
               }
            }

         }
      }

      private void dispatchRemovals(List<DiffUtil.PostponedUpdate> var1, ListUpdateCallback var2, int var3, int var4, int var5) {
         if(!this.mDetectMoves) {
            var2.onRemoved(var3, var4);
         } else {
            --var4;

            for(; var4 >= 0; --var4) {
               int[] var9 = this.mOldItemStatuses;
               int var6 = var5 + var4;
               int var7 = var9[var6] & 31;
               if(var7 != 0) {
                  if(var7 != 4 && var7 != 8) {
                     if(var7 != 16) {
                        StringBuilder var11 = new StringBuilder();
                        var11.append("unknown flag for pos ");
                        var11.append(var6);
                        var11.append(" ");
                        var11.append(Long.toBinaryString((long)var7));
                        throw new IllegalStateException(var11.toString());
                     }

                     var1.add(new DiffUtil.PostponedUpdate(var6, var3 + var4, true));
                  } else {
                     int var8 = this.mOldItemStatuses[var6] >> 5;
                     DiffUtil.PostponedUpdate var12 = removePostponedUpdate(var1, var8, false);
                     var2.onMoved(var3 + var4, var12.currentPos - 1);
                     if(var7 == 4) {
                        var2.onChanged(var12.currentPos - 1, 1, this.mCallback.getChangePayload(var6, var8));
                     }
                  }
               } else {
                  var2.onRemoved(var3 + var4, 1);

                  DiffUtil.PostponedUpdate var10;
                  for(Iterator var13 = var1.iterator(); var13.hasNext(); --var10.currentPos) {
                     var10 = (DiffUtil.PostponedUpdate)var13.next();
                  }
               }
            }

         }
      }

      private void findAddition(int var1, int var2, int var3) {
         if(this.mOldItemStatuses[var1 - 1] == 0) {
            this.findMatchingItem(var1, var2, var3, false);
         }
      }

      private boolean findMatchingItem(int var1, int var2, int var3, boolean var4) {
         int var5;
         int var6;
         if(var4) {
            var5 = var2 - 1;
            var2 = var1;
            var6 = var5;
         } else {
            int var7 = var1 - 1;
            var6 = var7;
            var5 = var2;
            var2 = var7;
         }

         while(var3 >= 0) {
            DiffUtil.Snake var12 = (DiffUtil.Snake)this.mSnakes.get(var3);
            int var8 = var12.x;
            int var9 = var12.size;
            int var10 = var12.y;
            int var11 = var12.size;
            byte var13 = 4;
            if(var4) {
               --var2;

               while(var2 >= var8 + var9) {
                  if(this.mCallback.areItemsTheSame(var2, var6)) {
                     if(this.mCallback.areContentsTheSame(var2, var6)) {
                        var13 = 8;
                     }

                     this.mNewItemStatuses[var6] = var2 << 5 | 16;
                     this.mOldItemStatuses[var2] = var6 << 5 | var13;
                     return true;
                  }

                  --var2;
               }
            } else {
               for(var2 = var5 - 1; var2 >= var10 + var11; --var2) {
                  if(this.mCallback.areItemsTheSame(var6, var2)) {
                     if(this.mCallback.areContentsTheSame(var6, var2)) {
                        var13 = 8;
                     }

                     int[] var14 = this.mOldItemStatuses;
                     --var1;
                     var14[var1] = var2 << 5 | 16;
                     this.mNewItemStatuses[var2] = var1 << 5 | var13;
                     return true;
                  }
               }
            }

            var2 = var12.x;
            var5 = var12.y;
            --var3;
         }

         return false;
      }

      private void findMatchingItems() {
         int var2 = this.mOldListSize;
         int var1 = this.mNewListSize;

         for(int var3 = this.mSnakes.size() - 1; var3 >= 0; --var3) {
            DiffUtil.Snake var9 = (DiffUtil.Snake)this.mSnakes.get(var3);
            int var7 = var9.x;
            int var8 = var9.size;
            int var5 = var9.y;
            int var6 = var9.size;
            int var4;
            if(this.mDetectMoves) {
               while(true) {
                  var4 = var1;
                  if(var2 <= var7 + var8) {
                     while(var4 > var5 + var6) {
                        this.findRemoval(var2, var4, var3);
                        --var4;
                     }
                     break;
                  }

                  this.findAddition(var2, var1, var3);
                  --var2;
               }
            }

            for(var1 = 0; var1 < var9.size; ++var1) {
               var4 = var9.x + var1;
               var5 = var9.y + var1;
               byte var10;
               if(this.mCallback.areContentsTheSame(var4, var5)) {
                  var10 = 1;
               } else {
                  var10 = 2;
               }

               this.mOldItemStatuses[var4] = var5 << 5 | var10;
               this.mNewItemStatuses[var5] = var4 << 5 | var10;
            }

            var2 = var9.x;
            var1 = var9.y;
         }

      }

      private void findRemoval(int var1, int var2, int var3) {
         if(this.mNewItemStatuses[var2 - 1] == 0) {
            this.findMatchingItem(var1, var2, var3, true);
         }
      }

      private static DiffUtil.PostponedUpdate removePostponedUpdate(List<DiffUtil.PostponedUpdate> var0, int var1, boolean var2) {
         for(int var3 = var0.size() - 1; var3 >= 0; --var3) {
            DiffUtil.PostponedUpdate var5 = (DiffUtil.PostponedUpdate)var0.get(var3);
            if(var5.posInOwnerList == var1 && var5.removal == var2) {
               var0.remove(var3);

               while(var3 < var0.size()) {
                  DiffUtil.PostponedUpdate var6 = (DiffUtil.PostponedUpdate)var0.get(var3);
                  int var4 = var6.currentPos;
                  byte var7;
                  if(var2) {
                     var7 = 1;
                  } else {
                     var7 = -1;
                  }

                  var6.currentPos = var4 + var7;
                  ++var3;
               }

               return var5;
            }
         }

         return null;
      }

      public int convertNewPositionToOld(
         @IntRange(
            from = 0L
         ) int var1) {
         if(var1 >= 0 && var1 < this.mNewItemStatuses.length) {
            var1 = this.mNewItemStatuses[var1];
            return (var1 & 31) == 0?-1:var1 >> 5;
         } else {
            StringBuilder var2 = new StringBuilder();
            var2.append("Index out of bounds - passed position = ");
            var2.append(var1);
            var2.append(", new list size = ");
            var2.append(this.mNewItemStatuses.length);
            throw new IndexOutOfBoundsException(var2.toString());
         }
      }

      public int convertOldPositionToNew(
         @IntRange(
            from = 0L
         ) int var1) {
         if(var1 >= 0 && var1 < this.mOldItemStatuses.length) {
            var1 = this.mOldItemStatuses[var1];
            return (var1 & 31) == 0?-1:var1 >> 5;
         } else {
            StringBuilder var2 = new StringBuilder();
            var2.append("Index out of bounds - passed position = ");
            var2.append(var1);
            var2.append(", old list size = ");
            var2.append(this.mOldItemStatuses.length);
            throw new IndexOutOfBoundsException(var2.toString());
         }
      }

      public void dispatchUpdatesTo(@NonNull ListUpdateCallback var1) {
         BatchingListUpdateCallback var10;
         if(var1 instanceof BatchingListUpdateCallback) {
            var10 = (BatchingListUpdateCallback)var1;
         } else {
            var10 = new BatchingListUpdateCallback(var1);
         }

         ArrayList var8 = new ArrayList();
         int var3 = this.mOldListSize;
         int var4 = this.mNewListSize;
         int var2 = this.mSnakes.size();
         --var2;

         while(var2 >= 0) {
            DiffUtil.Snake var9 = (DiffUtil.Snake)this.mSnakes.get(var2);
            int var5 = var9.size;
            int var6 = var9.x + var5;
            int var7 = var9.y + var5;
            if(var6 < var3) {
               this.dispatchRemovals(var8, var10, var6, var3 - var6, var6);
            }

            if(var7 < var4) {
               this.dispatchAdditions(var8, var10, var6, var4 - var7, var7);
            }

            for(var3 = var5 - 1; var3 >= 0; --var3) {
               if((this.mOldItemStatuses[var9.x + var3] & 31) == 2) {
                  var10.onChanged(var9.x + var3, 1, this.mCallback.getChangePayload(var9.x + var3, var9.y + var3));
               }
            }

            var3 = var9.x;
            var4 = var9.y;
            --var2;
         }

         var10.dispatchLastEvent();
      }

      public void dispatchUpdatesTo(@NonNull RecyclerView.Adapter var1) {
         this.dispatchUpdatesTo((ListUpdateCallback)(new AdapterListUpdateCallback(var1)));
      }

      @VisibleForTesting
      List<DiffUtil.Snake> getSnakes() {
         return this.mSnakes;
      }
   }

   static class Snake {

      boolean removal;
      boolean reverse;
      int size;
      int x;
      int y;


   }

   public abstract static class ItemCallback<T extends Object> {

      public abstract boolean areContentsTheSame(@NonNull T var1, @NonNull T var2);

      public abstract boolean areItemsTheSame(@NonNull T var1, @NonNull T var2);

      @Nullable
      public Object getChangePayload(@NonNull T var1, @NonNull T var2) {
         return null;
      }
   }

   public abstract static class Callback {

      public abstract boolean areContentsTheSame(int var1, int var2);

      public abstract boolean areItemsTheSame(int var1, int var2);

      @Nullable
      public Object getChangePayload(int var1, int var2) {
         return null;
      }

      public abstract int getNewListSize();

      public abstract int getOldListSize();
   }

   static class Range {

      int newListEnd;
      int newListStart;
      int oldListEnd;
      int oldListStart;


      public Range() {}

      public Range(int var1, int var2, int var3, int var4) {
         this.oldListStart = var1;
         this.oldListEnd = var2;
         this.newListStart = var3;
         this.newListEnd = var4;
      }
   }

   static class PostponedUpdate {

      int currentPos;
      int posInOwnerList;
      boolean removal;


      public PostponedUpdate(int var1, int var2, boolean var3) {
         this.posInOwnerList = var1;
         this.currentPos = var2;
         this.removal = var3;
      }
   }
}
