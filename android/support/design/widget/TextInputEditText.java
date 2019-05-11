package android.support.design.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.R;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public class TextInputEditText extends AppCompatEditText {

   public TextInputEditText(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public TextInputEditText(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.editTextStyle);
   }

   public TextInputEditText(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   @Nullable
   private CharSequence getHintFromLayout() {
      TextInputLayout var1 = this.getTextInputLayout();
      return var1 != null?var1.getHint():null;
   }

   @Nullable
   private TextInputLayout getTextInputLayout() {
      for(ViewParent var1 = this.getParent(); var1 instanceof View; var1 = var1.getParent()) {
         if(var1 instanceof TextInputLayout) {
            return (TextInputLayout)var1;
         }
      }

      return null;
   }

   public CharSequence getHint() {
      TextInputLayout var1 = this.getTextInputLayout();
      return var1 != null && var1.isProvidingHint()?var1.getHint():super.getHint();
   }

   public InputConnection onCreateInputConnection(EditorInfo var1) {
      InputConnection var2 = super.onCreateInputConnection(var1);
      if(var2 != null && var1.hintText == null) {
         var1.hintText = this.getHintFromLayout();
      }

      return var2;
   }
}
