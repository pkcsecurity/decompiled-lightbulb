package com.facebook.react.views.text.frescosupport;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.net.Uri.Builder;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextInlineImageShadowNode;
import com.facebook.react.views.text.TextInlineImageSpan;
import com.facebook.react.views.text.frescosupport.FrescoBasedReactTextInlineImageSpan;
import java.util.Locale;
import javax.annotation.Nullable;

public class FrescoBasedReactTextInlineImageShadowNode extends ReactTextInlineImageShadowNode {

   @Nullable
   private final Object mCallerContext;
   private final AbstractDraweeControllerBuilder mDraweeControllerBuilder;
   private ReadableMap mHeaders;
   private float mHeight = Float.NaN;
   @Nullable
   private Uri mUri;
   private float mWidth = Float.NaN;


   public FrescoBasedReactTextInlineImageShadowNode(AbstractDraweeControllerBuilder var1, @Nullable Object var2) {
      this.mDraweeControllerBuilder = var1;
      this.mCallerContext = var2;
   }

   @Nullable
   private static Uri getResourceDrawableUri(Context var0, @Nullable String var1) {
      if(var1 != null && !var1.isEmpty()) {
         var1 = var1.toLowerCase(Locale.getDefault()).replace("-", "_");
         int var2 = var0.getResources().getIdentifier(var1, "drawable", var0.getPackageName());
         return (new Builder()).scheme("res").path(String.valueOf(var2)).build();
      } else {
         return null;
      }
   }

   public TextInlineImageSpan buildInlineImageSpan() {
      Resources var2 = this.getThemedContext().getResources();
      int var1 = (int)Math.ceil((double)this.mWidth);
      return new FrescoBasedReactTextInlineImageSpan(var2, (int)Math.ceil((double)this.mHeight), var1, this.getUri(), this.getHeaders(), this.getDraweeControllerBuilder(), this.getCallerContext());
   }

   @Nullable
   public Object getCallerContext() {
      return this.mCallerContext;
   }

   public AbstractDraweeControllerBuilder getDraweeControllerBuilder() {
      return this.mDraweeControllerBuilder;
   }

   public ReadableMap getHeaders() {
      return this.mHeaders;
   }

   @Nullable
   public Uri getUri() {
      return this.mUri;
   }

   public boolean isVirtual() {
      return true;
   }

   @ReactProp(
      name = "headers"
   )
   public void setHeaders(ReadableMap var1) {
      this.mHeaders = var1;
   }

   public void setHeight(Dynamic var1) {
      if(var1.getType() == ReadableType.Number) {
         this.mHeight = (float)var1.asDouble();
      } else {
         throw new JSApplicationIllegalArgumentException("Inline images must not have percentage based height");
      }
   }

   @ReactProp(
      name = "src"
   )
   public void setSource(@Nullable ReadableArray var1) {
      String var4 = null;
      Uri var2 = null;
      String var3;
      if(var1 != null && var1.size() != 0) {
         var3 = var1.getMap(0).getString("uri");
      } else {
         var3 = null;
      }

      Uri var7 = var4;
      if(var3 != null) {
         label46: {
            try {
               var7 = Uri.parse(var3);
            } catch (Exception var6) {
               break label46;
            }

            label32: {
               try {
                  var4 = var7.getScheme();
               } catch (Exception var5) {
                  break label32;
               }

               if(var4 == null) {
                  break label46;
               }
            }

            var2 = var7;
         }

         var7 = var2;
         if(var2 == null) {
            var7 = getResourceDrawableUri(this.getThemedContext(), var3);
         }
      }

      if(var7 != this.mUri) {
         this.markUpdated();
      }

      this.mUri = var7;
   }

   public void setWidth(Dynamic var1) {
      if(var1.getType() == ReadableType.Number) {
         this.mWidth = (float)var1.asDouble();
      } else {
         throw new JSApplicationIllegalArgumentException("Inline images must not have percentage based width");
      }
   }
}
