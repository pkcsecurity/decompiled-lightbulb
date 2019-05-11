package android.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
public @interface RestrictTo {

   RestrictTo.Scope[] value();

   public static enum Scope {

      // $FF: synthetic field
      private static final RestrictTo.Scope[] $VALUES = new RestrictTo.Scope[]{LIBRARY, LIBRARY_GROUP, GROUP_ID, TESTS, SUBCLASSES};
      @Deprecated
      GROUP_ID("GROUP_ID", 2),
      LIBRARY("LIBRARY", 0),
      LIBRARY_GROUP("LIBRARY_GROUP", 1),
      SUBCLASSES("SUBCLASSES", 4),
      TESTS("TESTS", 3);


      private Scope(String var1, int var2) {}
   }
}
