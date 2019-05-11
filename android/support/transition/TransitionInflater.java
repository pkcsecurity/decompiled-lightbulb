package android.support.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.content.res.Resources.NotFoundException;
import android.support.annotation.NonNull;
import android.support.transition.ArcMotion;
import android.support.transition.AutoTransition;
import android.support.transition.ChangeBounds;
import android.support.transition.ChangeClipBounds;
import android.support.transition.ChangeImageTransform;
import android.support.transition.ChangeScroll;
import android.support.transition.ChangeTransform;
import android.support.transition.Explode;
import android.support.transition.Fade;
import android.support.transition.PathMotion;
import android.support.transition.PatternPathMotion;
import android.support.transition.Scene;
import android.support.transition.Slide;
import android.support.transition.Styleable;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.ViewGroup;
import java.io.IOException;
import java.lang.reflect.Constructor;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class TransitionInflater {

   private static final ArrayMap<String, Constructor> CONSTRUCTORS = new ArrayMap();
   private static final Class<?>[] CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class};
   private final Context mContext;


   private TransitionInflater(@NonNull Context var1) {
      this.mContext = var1;
   }

   private Object createCustom(AttributeSet param1, Class param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   private Transition createTransitionFromXml(XmlPullParser var1, AttributeSet var2, Transition var3) throws XmlPullParserException, IOException {
      int var4 = var1.getDepth();
      TransitionSet var7;
      if(var3 instanceof TransitionSet) {
         var7 = (TransitionSet)var3;
      } else {
         var7 = null;
      }

      while(true) {
         Object var8 = null;

         while(true) {
            int var5 = var1.next();
            if(var5 == 3 && var1.getDepth() <= var4 || var5 == 1) {
               return (Transition)var8;
            }

            if(var5 == 2) {
               String var6 = var1.getName();
               Object var10;
               if("fade".equals(var6)) {
                  var10 = new Fade(this.mContext, var2);
               } else if("changeBounds".equals(var6)) {
                  var10 = new ChangeBounds(this.mContext, var2);
               } else if("slide".equals(var6)) {
                  var10 = new Slide(this.mContext, var2);
               } else if("explode".equals(var6)) {
                  var10 = new Explode(this.mContext, var2);
               } else if("changeImageTransform".equals(var6)) {
                  var10 = new ChangeImageTransform(this.mContext, var2);
               } else if("changeTransform".equals(var6)) {
                  var10 = new ChangeTransform(this.mContext, var2);
               } else if("changeClipBounds".equals(var6)) {
                  var10 = new ChangeClipBounds(this.mContext, var2);
               } else if("autoTransition".equals(var6)) {
                  var10 = new AutoTransition(this.mContext, var2);
               } else if("changeScroll".equals(var6)) {
                  var10 = new ChangeScroll(this.mContext, var2);
               } else if("transitionSet".equals(var6)) {
                  var10 = new TransitionSet(this.mContext, var2);
               } else if("transition".equals(var6)) {
                  var10 = (Transition)this.createCustom(var2, Transition.class, "transition");
               } else if("targets".equals(var6)) {
                  this.getTargetIds(var1, var2, var3);
                  var10 = var8;
               } else if("arcMotion".equals(var6)) {
                  if(var3 == null) {
                     throw new RuntimeException("Invalid use of arcMotion element");
                  }

                  var3.setPathMotion(new ArcMotion(this.mContext, var2));
                  var10 = var8;
               } else if("pathMotion".equals(var6)) {
                  if(var3 == null) {
                     throw new RuntimeException("Invalid use of pathMotion element");
                  }

                  var3.setPathMotion((PathMotion)this.createCustom(var2, PathMotion.class, "pathMotion"));
                  var10 = var8;
               } else {
                  if(!"patternPathMotion".equals(var6)) {
                     StringBuilder var9 = new StringBuilder();
                     var9.append("Unknown scene name: ");
                     var9.append(var1.getName());
                     throw new RuntimeException(var9.toString());
                  }

                  if(var3 == null) {
                     throw new RuntimeException("Invalid use of patternPathMotion element");
                  }

                  var3.setPathMotion(new PatternPathMotion(this.mContext, var2));
                  var10 = var8;
               }

               var8 = var10;
               if(var10 != null) {
                  if(!var1.isEmptyElementTag()) {
                     this.createTransitionFromXml(var1, var2, (Transition)var10);
                  }

                  if(var7 != null) {
                     var7.addTransition((Transition)var10);
                     break;
                  }

                  var8 = var10;
                  if(var3 != null) {
                     throw new InflateException("Could not add transition to another transition.");
                  }
               }
            }
         }
      }
   }

   private TransitionManager createTransitionManagerFromXml(XmlPullParser var1, AttributeSet var2, ViewGroup var3) throws XmlPullParserException, IOException {
      int var4 = var1.getDepth();
      TransitionManager var6 = null;

      while(true) {
         int var5 = var1.next();
         if(var5 == 3 && var1.getDepth() <= var4 || var5 == 1) {
            return var6;
         }

         if(var5 == 2) {
            String var7 = var1.getName();
            if(var7.equals("transitionManager")) {
               var6 = new TransitionManager();
            } else {
               if(!var7.equals("transition") || var6 == null) {
                  StringBuilder var8 = new StringBuilder();
                  var8.append("Unknown scene name: ");
                  var8.append(var1.getName());
                  throw new RuntimeException(var8.toString());
               }

               this.loadTransition(var2, var1, var3, var6);
            }
         }
      }
   }

   public static TransitionInflater from(Context var0) {
      return new TransitionInflater(var0);
   }

   private void getTargetIds(XmlPullParser param1, AttributeSet param2, Transition param3) throws XmlPullParserException, IOException {
      // $FF: Couldn't be decompiled
   }

   private void loadTransition(AttributeSet var1, XmlPullParser var2, ViewGroup var3, TransitionManager var4) throws NotFoundException {
      TypedArray var8 = this.mContext.obtainStyledAttributes(var1, Styleable.TRANSITION_MANAGER);
      int var5 = TypedArrayUtils.getNamedResourceId(var8, var2, "transition", 2, -1);
      int var6 = TypedArrayUtils.getNamedResourceId(var8, var2, "fromScene", 0, -1);
      Object var7 = null;
      Scene var9;
      if(var6 < 0) {
         var9 = null;
      } else {
         var9 = Scene.getSceneForLayout(var3, var6, this.mContext);
      }

      var6 = TypedArrayUtils.getNamedResourceId(var8, var2, "toScene", 1, -1);
      Scene var10;
      if(var6 < 0) {
         var10 = (Scene)var7;
      } else {
         var10 = Scene.getSceneForLayout(var3, var6, this.mContext);
      }

      if(var5 >= 0) {
         Transition var12 = this.inflateTransition(var5);
         if(var12 != null) {
            if(var10 == null) {
               StringBuilder var11 = new StringBuilder();
               var11.append("No toScene for transition ID ");
               var11.append(var5);
               throw new RuntimeException(var11.toString());
            }

            if(var9 == null) {
               var4.setTransition(var10, var12);
            } else {
               var4.setTransition(var9, var10, var12);
            }
         }
      }

      var8.recycle();
   }

   public Transition inflateTransition(int var1) {
      XmlResourceParser var2 = this.mContext.getResources().getXml(var1);

      Transition var3;
      try {
         var3 = this.createTransitionFromXml(var2, Xml.asAttributeSet(var2), (Transition)null);
      } catch (XmlPullParserException var8) {
         throw new InflateException(var8.getMessage(), var8);
      } catch (IOException var9) {
         StringBuilder var4 = new StringBuilder();
         var4.append(var2.getPositionDescription());
         var4.append(": ");
         var4.append(var9.getMessage());
         throw new InflateException(var4.toString(), var9);
      } finally {
         var2.close();
      }

      return var3;
   }

   public TransitionManager inflateTransitionManager(int var1, ViewGroup var2) {
      XmlResourceParser var3 = this.mContext.getResources().getXml(var1);

      TransitionManager var11;
      try {
         InflateException var12;
         try {
            var11 = this.createTransitionManagerFromXml(var3, Xml.asAttributeSet(var3), var2);
         } catch (XmlPullParserException var8) {
            var12 = new InflateException(var8.getMessage());
            var12.initCause(var8);
            throw var12;
         } catch (IOException var9) {
            StringBuilder var4 = new StringBuilder();
            var4.append(var3.getPositionDescription());
            var4.append(": ");
            var4.append(var9.getMessage());
            var12 = new InflateException(var4.toString());
            var12.initCause(var9);
            throw var12;
         }
      } finally {
         var3.close();
      }

      return var11;
   }
}
