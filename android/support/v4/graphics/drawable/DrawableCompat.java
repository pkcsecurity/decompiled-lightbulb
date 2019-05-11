package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.TintAwareDrawable;
import android.support.v4.graphics.drawable.WrappedDrawable;
import android.support.v4.graphics.drawable.WrappedDrawableApi14;
import android.support.v4.graphics.drawable.WrappedDrawableApi21;
import android.util.AttributeSet;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class DrawableCompat {

   private static final String TAG = "DrawableCompat";
   private static Method sGetLayoutDirectionMethod;
   private static boolean sGetLayoutDirectionMethodFetched;
   private static Method sSetLayoutDirectionMethod;
   private static boolean sSetLayoutDirectionMethodFetched;


   public static void applyTheme(@NonNull Drawable var0, @NonNull Theme var1) {
      if(VERSION.SDK_INT >= 21) {
         var0.applyTheme(var1);
      }

   }

   public static boolean canApplyTheme(@NonNull Drawable var0) {
      return VERSION.SDK_INT >= 21?var0.canApplyTheme():false;
   }

   public static void clearColorFilter(@NonNull Drawable var0) {
      if(VERSION.SDK_INT >= 23) {
         var0.clearColorFilter();
      } else {
         if(VERSION.SDK_INT >= 21) {
            var0.clearColorFilter();
            if(var0 instanceof InsetDrawable) {
               clearColorFilter(((InsetDrawable)var0).getDrawable());
               return;
            }

            if(var0 instanceof WrappedDrawable) {
               clearColorFilter(((WrappedDrawable)var0).getWrappedDrawable());
               return;
            }

            if(var0 instanceof DrawableContainer) {
               DrawableContainerState var4 = (DrawableContainerState)((DrawableContainer)var0).getConstantState();
               if(var4 != null) {
                  int var1 = 0;

                  for(int var2 = var4.getChildCount(); var1 < var2; ++var1) {
                     Drawable var3 = var4.getChild(var1);
                     if(var3 != null) {
                        clearColorFilter(var3);
                     }
                  }
               }
            }
         } else {
            var0.clearColorFilter();
         }

      }
   }

   public static int getAlpha(@NonNull Drawable var0) {
      return VERSION.SDK_INT >= 19?var0.getAlpha():0;
   }

   public static ColorFilter getColorFilter(@NonNull Drawable var0) {
      return VERSION.SDK_INT >= 21?var0.getColorFilter():null;
   }

   public static int getLayoutDirection(@NonNull Drawable var0) {
      if(VERSION.SDK_INT >= 23) {
         return var0.getLayoutDirection();
      } else if(VERSION.SDK_INT >= 17) {
         if(!sGetLayoutDirectionMethodFetched) {
            try {
               sGetLayoutDirectionMethod = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0]);
               sGetLayoutDirectionMethod.setAccessible(true);
            } catch (NoSuchMethodException var3) {
               Log.i("DrawableCompat", "Failed to retrieve getLayoutDirection() method", var3);
            }

            sGetLayoutDirectionMethodFetched = true;
         }

         if(sGetLayoutDirectionMethod != null) {
            try {
               int var1 = ((Integer)sGetLayoutDirectionMethod.invoke(var0, new Object[0])).intValue();
               return var1;
            } catch (Exception var4) {
               Log.i("DrawableCompat", "Failed to invoke getLayoutDirection() via reflection", var4);
               sGetLayoutDirectionMethod = null;
            }
         }

         return 0;
      } else {
         return 0;
      }
   }

   public static void inflate(@NonNull Drawable var0, @NonNull Resources var1, @NonNull XmlPullParser var2, @NonNull AttributeSet var3, @Nullable Theme var4) throws XmlPullParserException, IOException {
      if(VERSION.SDK_INT >= 21) {
         var0.inflate(var1, var2, var3, var4);
      } else {
         var0.inflate(var1, var2, var3);
      }
   }

   public static boolean isAutoMirrored(@NonNull Drawable var0) {
      return VERSION.SDK_INT >= 19?var0.isAutoMirrored():false;
   }

   @Deprecated
   public static void jumpToCurrentState(@NonNull Drawable var0) {
      var0.jumpToCurrentState();
   }

   public static void setAutoMirrored(@NonNull Drawable var0, boolean var1) {
      if(VERSION.SDK_INT >= 19) {
         var0.setAutoMirrored(var1);
      }

   }

   public static void setHotspot(@NonNull Drawable var0, float var1, float var2) {
      if(VERSION.SDK_INT >= 21) {
         var0.setHotspot(var1, var2);
      }

   }

   public static void setHotspotBounds(@NonNull Drawable var0, int var1, int var2, int var3, int var4) {
      if(VERSION.SDK_INT >= 21) {
         var0.setHotspotBounds(var1, var2, var3, var4);
      }

   }

   public static boolean setLayoutDirection(@NonNull Drawable var0, int var1) {
      if(VERSION.SDK_INT >= 23) {
         return var0.setLayoutDirection(var1);
      } else if(VERSION.SDK_INT >= 17) {
         if(!sSetLayoutDirectionMethodFetched) {
            try {
               sSetLayoutDirectionMethod = Drawable.class.getDeclaredMethod("setLayoutDirection", new Class[]{Integer.TYPE});
               sSetLayoutDirectionMethod.setAccessible(true);
            } catch (NoSuchMethodException var3) {
               Log.i("DrawableCompat", "Failed to retrieve setLayoutDirection(int) method", var3);
            }

            sSetLayoutDirectionMethodFetched = true;
         }

         if(sSetLayoutDirectionMethod != null) {
            try {
               sSetLayoutDirectionMethod.invoke(var0, new Object[]{Integer.valueOf(var1)});
               return true;
            } catch (Exception var4) {
               Log.i("DrawableCompat", "Failed to invoke setLayoutDirection(int) via reflection", var4);
               sSetLayoutDirectionMethod = null;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static void setTint(@NonNull Drawable var0, @ColorInt int var1) {
      if(VERSION.SDK_INT >= 21) {
         var0.setTint(var1);
      } else {
         if(var0 instanceof TintAwareDrawable) {
            ((TintAwareDrawable)var0).setTint(var1);
         }

      }
   }

   public static void setTintList(@NonNull Drawable var0, @Nullable ColorStateList var1) {
      if(VERSION.SDK_INT >= 21) {
         var0.setTintList(var1);
      } else {
         if(var0 instanceof TintAwareDrawable) {
            ((TintAwareDrawable)var0).setTintList(var1);
         }

      }
   }

   public static void setTintMode(@NonNull Drawable var0, @NonNull Mode var1) {
      if(VERSION.SDK_INT >= 21) {
         var0.setTintMode(var1);
      } else {
         if(var0 instanceof TintAwareDrawable) {
            ((TintAwareDrawable)var0).setTintMode(var1);
         }

      }
   }

   public static <T extends Drawable> T unwrap(@NonNull Drawable var0) {
      return var0 instanceof WrappedDrawable?((WrappedDrawable)var0).getWrappedDrawable():var0;
   }

   public static Drawable wrap(@NonNull Drawable var0) {
      return (Drawable)(VERSION.SDK_INT >= 23?var0:(VERSION.SDK_INT >= 21?(!(var0 instanceof TintAwareDrawable)?new WrappedDrawableApi21(var0):var0):(!(var0 instanceof TintAwareDrawable)?new WrappedDrawableApi14(var0):var0)));
   }
}
