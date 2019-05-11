package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.Nonnegative;
import javax.annotation.meta.TypeQualifierNickname;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Nonnegative
@TypeQualifierNickname
public @interface Signed {
}
