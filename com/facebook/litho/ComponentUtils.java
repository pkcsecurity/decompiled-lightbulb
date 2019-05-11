package com.facebook.litho;

import com.facebook.litho.Component;
import com.facebook.litho.Equivalence;
import com.facebook.litho.EventHandler;
import com.facebook.litho.InternalNode;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.reference.Reference;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.annotation.Nullable;

public class ComponentUtils {

   private static boolean areArraysEquals(Class<?> var0, Object var1, Object var2) {
      var0 = var0.getComponentType();
      if(Byte.TYPE.isAssignableFrom(var0)) {
         if(!Arrays.equals((byte[])var1, (byte[])var2)) {
            return false;
         }
      } else if(Short.TYPE.isAssignableFrom(var0)) {
         if(!Arrays.equals((short[])var1, (short[])var2)) {
            return false;
         }
      } else if(Character.TYPE.isAssignableFrom(var0)) {
         if(!Arrays.equals((char[])var1, (char[])var2)) {
            return false;
         }
      } else if(Integer.TYPE.isAssignableFrom(var0)) {
         if(!Arrays.equals((int[])var1, (int[])var2)) {
            return false;
         }
      } else if(Long.TYPE.isAssignableFrom(var0)) {
         if(!Arrays.equals((long[])var1, (long[])var2)) {
            return false;
         }
      } else if(Float.TYPE.isAssignableFrom(var0)) {
         if(!Arrays.equals((float[])var1, (float[])var2)) {
            return false;
         }
      } else if(Double.TYPE.isAssignableFrom(var0)) {
         if(!Arrays.equals((double[])var1, (double[])var2)) {
            return false;
         }
      } else if(Boolean.TYPE.isAssignableFrom(var0)) {
         if(!Arrays.equals((boolean[])var1, (boolean[])var2)) {
            return false;
         }
      } else if(!Arrays.equals((Object[])var1, (Object[])var2)) {
         return false;
      }

      return true;
   }

   private static boolean areComponentCollectionsEquals(int var0, Collection var1, Collection var2) {
      if(var0 < 1) {
         throw new IllegalArgumentException("Level cannot be < 1");
      } else if(var1 == null && var2 == null) {
         return true;
      } else {
         label43: {
            if(var1 != null) {
               if(var2 != null) {
                  if(var1.size() != var2.size()) {
                     return false;
                  }
                  break label43;
               }
            } else if(var2 == null) {
               break label43;
            }

            return false;
         }

         Iterator var3 = var1.iterator();
         Iterator var4 = var2.iterator();

         while(var3.hasNext() && var4.hasNext()) {
            if(var0 == 1) {
               if(!((Component)var3.next()).isEquivalentTo((Component)var4.next())) {
                  return false;
               }
            } else if(!areComponentCollectionsEquals(var0 - 1, (Collection)var3.next(), (Collection)var4.next())) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean hasEquivalentFields(Object var0, Object var1) {
      if(var0 != null && var1 != null && var0.getClass() == var1.getClass()) {
         Field[] var5 = var0.getClass().getDeclaredFields();
         int var3 = var5.length;

         for(int var2 = 0; var2 < var3; ++var2) {
            Field var7 = var5[var2];
            if(var7.isAnnotationPresent(Comparable.class)) {
               Class var8 = var7.getType();

               Object var6;
               Object var9;
               try {
                  var7.setAccessible(true);
                  var9 = var7.get(var0);
                  var6 = var7.get(var1);
                  var7.setAccessible(false);
               } catch (IllegalAccessException var10) {
                  throw new IllegalStateException("Unable to get fields by reflection.", var10);
               }

               int var4 = ((Comparable)var7.getAnnotation(Comparable.class)).type();
               switch(var4) {
               case 0:
                  if(Float.compare(((Float)var9).floatValue(), ((Float)var6).floatValue()) != 0) {
                     return false;
                  }
                  break;
               case 1:
                  if(Double.compare(((Double)var9).doubleValue(), ((Double)var6).doubleValue()) != 0) {
                     return false;
                  }
                  break;
               case 2:
                  if(!areArraysEquals(var8, var9, var6)) {
                     return false;
                  }
                  break;
               case 3:
                  if(!var9.equals(var6)) {
                     return false;
                  }
                  break;
               case 4:
                  if(Reference.shouldUpdate((Reference)var9, (Reference)var6)) {
                     return false;
                  }
                  break;
               case 5:
                  Collection var12 = (Collection)var9;
                  Collection var11 = (Collection)var6;
                  if(var12 != null) {
                     if(!var12.equals(var11)) {
                        return false;
                     }
                  } else if(var11 != null) {
                     return false;
                  }
                  break;
               case 6:
               case 7:
               case 8:
               case 9:
                  if(!areComponentCollectionsEquals(var4 - 5, (Collection)var9, (Collection)var6)) {
                     return false;
                  }
                  break;
               case 10:
               case 15:
                  if(var9 != null) {
                     if(!((Equivalence)var9).isEquivalentTo(var6)) {
                        return false;
                     }
                  } else if(var6 != null) {
                     return false;
                  }
                  break;
               case 11:
               case 12:
                  if(var9 != null) {
                     if(!((EventHandler)var9).isEquivalentTo((EventHandler)var6)) {
                        return false;
                     }
                  } else if(var6 != null) {
                     return false;
                  }
                  break;
               case 13:
                  if(var9 != null) {
                     if(!var9.equals(var6)) {
                        return false;
                     }
                  } else if(var6 != null) {
                     return false;
                  }
                  break;
               case 14:
                  if(!hasEquivalentFields(var9, var6)) {
                     return false;
                  }
               }
            }
         }

         return true;
      } else {
         throw new IllegalArgumentException("The input is invalid.");
      }
   }

   static String treeToString(@Nullable InternalNode var0) {
      if(var0 == null) {
         return "null";
      } else {
         StringBuilder var5 = new StringBuilder();
         LinkedList var6 = new LinkedList();
         var6.addLast((Object)null);
         var6.addLast(var0);
         int var1 = 0;

         while(!var6.isEmpty()) {
            InternalNode var7 = (InternalNode)var6.removeLast();
            if(var7 == null) {
               --var1;
            } else {
               Component var8 = var7.getRootComponent();
               if(var8 != null) {
                  int var2;
                  if(var7 != var0) {
                     var5.append('\n');
                     Iterator var9 = var6.iterator();
                     var9.next();
                     var9.next();

                     for(var2 = 0; var2 < var1 - 1; ++var2) {
                        boolean var3;
                        if(var9.next() == null) {
                           var3 = true;
                        } else {
                           var3 = false;
                        }

                        if(!var3) {
                           while(true) {
                              if(var9.next() != null) {
                                 continue;
                              }
                           }
                        }

                        Object var4;
                        if(var3) {
                           var4 = Character.valueOf(' ');
                        } else {
                           var4 = "│";
                        }

                        var5.append(var4);
                        var5.append(' ');
                     }

                     String var10;
                     if(var6.getLast() == null) {
                        var10 = "└╴";
                     } else {
                        var10 = "├╴";
                     }

                     var5.append(var10);
                  }

                  var5.append(var8.getSimpleName());
                  if(var8.hasManualKey() || var7.hasTransitionKey() || var7.getTestKey() != null) {
                     var5.append('[');
                     if(var8.hasManualKey()) {
                        var5.append("manual.key=\"");
                        var5.append(var8.getKey());
                        var5.append("\";");
                     }

                     if(var7.hasTransitionKey()) {
                        var5.append("trans.key=\"");
                        var5.append(var7.getTransitionKey());
                        var5.append("\";");
                     }

                     if(var7.getTestKey() != null) {
                        var5.append("test.key=\"");
                        var5.append(var7.getTestKey());
                        var5.append("\";");
                     }

                     var5.append(']');
                  }

                  if(var7.getChildCount() != 0) {
                     var6.addLast((Object)null);

                     for(var2 = var7.getChildCount() - 1; var2 >= 0; --var2) {
                        var6.addLast(var7.getChildAt(var2));
                     }

                     ++var1;
                  }
               }
            }
         }

         return var5.toString();
      }
   }
}
